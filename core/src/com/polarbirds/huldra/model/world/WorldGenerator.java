package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.polarbirds.huldra.model.utility.ALoader;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.smokebox.lib.utils.IntVector2;
import com.smokebox.lib.utils.geom.Bounds;
import com.smokebox.lib.utils.geom.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A class to generate the boundslist used in the WorldTypes. Created by Harald Wilhelmsen on
 * 7/6/2015.
 */
public final class WorldGenerator extends ALoader {

    public Tile[][] tiles;
    public Vector2 spawn;

    private LevelFile levelFile;
    private Random random;

    private HashMap<String, ArrayList<TextureData>> textureDataMap;

    public WorldGenerator(LevelFile levelFile, Random random) {
        this.levelFile = levelFile;
        this.random = random;
    }

    @Override
    public void run() {
        max = 5;
        generate();
        loadTextureData();
        done = true;
    }

    private IntVector2 getSize() {
        int width = 1 + (int) Math.abs(random.nextGaussian() * levelFile.type.rsSize);
        int height = 1 + (int) Math.abs(random.nextGaussian() * levelFile.type.rsSize);

        if (width > Section.BOUNDS_MAX_WIDTH) {
            width = Section.BOUNDS_MAX_WIDTH;
        }

        if (height > Section.BOUNDS_MAX_HEIGHT) {
            height = Section.BOUNDS_MAX_HEIGHT;
        }
        return new IntVector2(width, height);
    }

    private IntVector2 getLocation(IntVector2 dimensions, Iterable<Bounds> boundsList,
                                   Bounds bounds) {
        // get this location's open possible surrounding locations
        List<IntVector2> locations =
            getLocationsAround(dimensions.x, dimensions.y, boundsList, bounds);
        // if no open locations, return null, This bounds-object is no good
        System.out.print("[s:" + locations.size() + "]");
        if (!locations.isEmpty()) {
            return locations.get(random.nextInt(locations.size()));
        }
        return null;
    }

    /**
     * Returns the location of where bounds2 can be placed with no intersections. Note: Can safely
     * return (0, 0) because this place will always be filled with spawn-section.
     *
     * @param width      Width of the section to place
     * @param height     Height of the section to place
     * @param boundsList List of bounds to check collisions with
     * @param bounds     SectionBounds to find location around.
     * @return The location of the bottom left corner of bounds2 if they can be combined, (0, 0) if
     * they could not be combined at all
     */
    private List<IntVector2> getLocationsAround(int width, int height,
                                                Iterable<Bounds> boundsList,
                                                Bounds bounds) {
        List<IntVector2> possibleLocations = new ArrayList<>();

        for (int x = bounds.x - width + 1;
             x < bounds.x + bounds.width;
             x++) {
            addIfNotCollides(new Bounds(x, bounds.y + bounds.height, width, height),
                             boundsList, possibleLocations);
            addIfNotCollides(new Bounds(x, bounds.y - height, width, height),
                             boundsList, possibleLocations);
        }

        for (int y = bounds.y - height + 1;
             y < bounds.y + bounds.height;
             y++) {
            addIfNotCollides(new Bounds(bounds.x + bounds.width, y, width, height),
                             boundsList, possibleLocations);
            addIfNotCollides(new Bounds(bounds.x - width, y, width, height),
                             boundsList, possibleLocations);
        }

        return possibleLocations;
    }

    private void addIfNotCollides(Bounds bounds, Iterable<Bounds> boundsList,
                                  Collection<IntVector2> locations) {
        if (!bounds.overlapsList(boundsList)) {
            locations.add(new IntVector2(bounds.x, bounds.y));
        }
    }

    private int getNewSelection(int size) {
        return (int) cap((float) (Math.abs(random.nextGaussian()) * levelFile.type.rsSpread), 0,
                         size - 1);
    }

    private float cap(float value, float min, float max) {
        return value < min ? min : value > max ? max : value;
    }

    private List<Bounds> generateBoundsList() {
        ArrayList<Bounds> boundsList = new ArrayList<>();

        boundsList.add(new Bounds(0, 0, 1, 1));

        // place down section until required amountOfSections is met
        int sectionsPlaced = 1;

        // loop until all sectionBoundsList are placed or the loop uses too many iterations
        for (int iterations = 0; iterations < 10000 && sectionsPlaced < levelFile.amountOfSections;
             iterations++) {
            System.out
                .println("Sections placed: " + sectionsPlaced + "/" + levelFile.amountOfSections);

            // find dimensions for a new sectionBounds
            IntVector2 dimensions = getSize();

            // find a section to expand from, and place a section there
            System.out.println(
                "Finding combined location for dimensions: " + dimensions.x + ", " + dimensions.y);
            for (int iterations2 = 0; iterations2 < 10000; iterations2++) {
                // Sort boundsList by their distance from spawn
                boundsList.sort(new SpreadComparator(levelFile.type.rsHor, levelFile.type.rsVer));

                // choose a random section to try to expand from
                Bounds bounds = boundsList.get(getNewSelection(boundsList.size()));
                System.out.print("Dim:[" + bounds.width + "," + bounds.height + "]");

                // Find location for new bounds
                IntVector2 location = getLocation(dimensions, boundsList, bounds);
                if (location == null) {
                    System.out.print("{f}");
                    continue;
                }
                System.out.print("{s}");

                Bounds toAdd = new Bounds(location.x, location.y, dimensions.x, dimensions.y);
                boundsList.add(toAdd);
                sectionsPlaced++;
                break;
            }
            System.out.println();
        }
        return boundsList;
    }

    private void generate() {
        Iterable<Bounds> boundsList = generateBoundsList();
        loaded++;
        List<TilesWithOpenings> twos = TilesWithOpenings.loadAndGetList();
        loaded++;
        // normalize bounds. Since the spawn was previously on 0,0 it is now located on the shift
        // that was applied
        IntVector2 intSpawn = normalizeBoundsList(boundsList);
        loaded++;
        Collection<Section> sections = new ArrayList<>();
        for (Bounds bounds : boundsList) {
            sections.add(new Section(bounds));
        }

        IntVector2 maxBounds = getMaxBounds(boundsList);

        // Array for map-tiles
        TileType[][] tileTypes = new TileType
            [maxBounds.x * Section.TILES_PER_SIDE + 2][maxBounds.y * Section.TILES_PER_SIDE + 2];

        for (TileType[] tt : tileTypes) {
            Arrays.fill(tt, TileType.SOLID);
        }

        // Array of reachable openings
        boolean[][] reachableOpenings =
            addSectionOpenings(sections, new boolean[tileTypes.length][tileTypes[0].length]);

        // Add the section's tiles to the map
        for (Section section : sections) {
            TilesWithOpenings two = getTilesForSection(section, reachableOpenings, twos, random);
            TileType[][] sectionTiles = two.tiles;
            section.tilesWithOpenings = two;
            int baseX = section.bounds.x * Section.TILES_PER_SIDE;
            int baseY = section.bounds.y * Section.TILES_PER_SIDE;
            for (int x = 0; x < sectionTiles.length; x++) {
                System.arraycopy(sectionTiles[x], 0, tileTypes[baseX + x + 1], baseY + 1,
                                 sectionTiles[0].length);
            }
        }

        // add an opening in all tiles in each bounds
        for (Bounds bounds : boundsList) {
            for (int x = 0; x < bounds.width * Section.TILES_PER_SIDE; x++) {
                for (int y = 0; y < bounds.height * Section.TILES_PER_SIDE; y++) {
                    reachableOpenings[bounds.x * Section.TILES_PER_SIDE + x]
                        [bounds.y * Section.TILES_PER_SIDE + y] = true;
                }
            }
        }

        Section spawnSection = null;
        for (Section s : sections) {
            if (intSpawn.isLike(-s.bounds.x, -s.bounds.y)) {
                spawnSection = s;
            }
        }
        List<IntVector2> spawns = spawnSection.tilesWithOpenings.locs.get("SPAWN");
        IntVector2 gottenSpawn = spawns.get(spawns.size() - 1);
        Vector2 spawn =
            new Vector2(spawnSection.bounds.x * Section.TILES_PER_SIDE + gottenSpawn.x + 1,
                        spawnSection.bounds.y * Section.TILES_PER_SIDE + gottenSpawn.y + 1);
        loaded++;
        this.tiles = convertTiles(tileTypes);
        this.spawn = spawn;
    }

    private Tile[][] convertTiles(TileType[][] tileTypes) {
        Tile[][] tiles = new Tile[tileTypes.length][tileTypes[0].length];
        for (int x = 0; x < tileTypes.length; x++) {
            for (int y = 0; y < tileTypes[0].length; y++) {
                tiles[x][y] = new Tile(tileTypes[x][y]);
            }
        }
        return tiles;
    }

    public void loadTextureData() {
        textureDataMap = new HashMap<>();
        String dirString = "graphics/world/tiles/" + levelFile.worldTypeString + "/";
        for (String path : new File(dirString).list()) {
            if (path.contains(".png")) {
                String key = path.substring(0, path.lastIndexOf("_"));
                TextureData textureData = TextureData.Factory
                    .loadFromFile(Gdx.files.internal(dirString + path), null, false);
                if (!textureDataMap.containsKey(key)) {
                    textureDataMap.put(key, new ArrayList<>());
                }
                textureDataMap.get(key).add(textureData);
            }
        }
    }

    private Map<String, ArrayList<Texture>> parseTextures() {
        Map<String, ArrayList<Texture>> textureLists = new HashMap<>();
        for (Map.Entry<String, ArrayList<TextureData>> entry : textureDataMap.entrySet()) {
            ArrayList<Texture> textureList = new ArrayList<>();
            textureLists.put(entry.getKey(), textureList);
            for (TextureData data : entry.getValue()) {
                textureList.add(new Texture(data));
            }
        }
        return textureLists;
    }


    public void placeTextures() {
        Map<String, ArrayList<Texture>> textureLists = parseTextures();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y].setTexture(getTexture(textureLists, tiles, x, y));
            }
        }
    }


    private Texture getTexture(Map<String, ArrayList<Texture>> textureMap,
                               Tile[][] tiles, int x, int y) {
        // get free sides in a boolean array
        boolean[] freeSides = new boolean[]{
            isTileOpen(tiles, x, y + 1),
            isTileOpen(tiles, x + 1, y),
            isTileOpen(tiles, x, y - 1),
            isTileOpen(tiles, x - 1, y),
        };
        //convert free sides into a "tftf"-format
        StringBuilder builder = new StringBuilder();
        builder.append("solid_");
        for (boolean b : freeSides) {
            builder.append(b ? "t" : "f");
        }
        ArrayList<Texture> textureList = textureMap.get(builder.toString());
        if (textureList == null) {
            return textureMap.get("solid").get(0);
        }
        return textureList.get((int) (Math.random() * textureList.size()));
    }

    private boolean isTileOpen(Tile[][] tiles, int x, int y) {
        return x >= 0 && y >= 0 && x < tiles.length && y < tiles[0].length
               && tiles[x][y].tileType != TileType.SOLID;
    }

    /**
     * Returns tiles for the given sectionBounds, taking into account the sectionBounds' openings
     */
    private TilesWithOpenings getTilesForSection(Section section, boolean[][] openings,
                                                 Iterable<TilesWithOpenings> twos, Random random) {
        List<TilesWithOpenings> candidates = new ArrayList<>();
        for (TilesWithOpenings two : twos) {
            if (two.matches(section.bounds, openings)) {
                candidates.add(two);
            }
            TilesWithOpenings twoFlipped = two.getFlipped();
            if (twoFlipped.matches(section.bounds, openings)) {
                candidates.add(twoFlipped);
            }
        }
        return candidates.get(random.nextInt(candidates.size()));
    }

    private boolean allTrue(boolean[] bs) {
        for (boolean b : bs) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    private TileType[][] placeholderTiles(Section section) {
        TileType[][] tiles =
            new TileType[section.bounds.width * Section.TILES_PER_SIDE][section.bounds.height
                                                                        * Section.TILES_PER_SIDE];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y] = x == 0 || y == 0 || x == tiles.length - 1 || y == tiles[0].length - 1
                              ? TileType.SOLID : TileType.EMPTY;
            }
        }
        return tiles;
    }

    private boolean[][] addSectionOpenings(Iterable<Section> sections, boolean[][] openings) {
        // create an array to keep track of where there are sections
        boolean[][] fills = new boolean[openings.length][openings[0].length];
        for (Section section : sections) {
            Bounds b = section.bounds;
            for (int x = b.x * Section.TILES_PER_SIDE; x < (b.x + b.width) * Section.TILES_PER_SIDE;
                 x++) {
                for (int y = b.y * Section.TILES_PER_SIDE;
                     y < (b.y + b.height) * Section.TILES_PER_SIDE;
                     y++) {
                    fills[x][y] = true;
                }
            }
        }

        // Make sure boundaries of sections are true in reachableOpenings-array
        for (Section section : sections) {
            Bounds b = section.bounds;
            for (int y = b.y * Section.TILES_PER_SIDE;
                 y < (b.y + b.height) * Section.TILES_PER_SIDE;
                 y++) {
                int xCheck = b.x * Section.TILES_PER_SIDE - 1;
                if (xCheck >= 0 && fills[xCheck][y]) {
                    openings[xCheck + 1][y] = true;
                }
                xCheck = (b.x + b.width) * Section.TILES_PER_SIDE;
                if (xCheck < fills.length && fills[(b.x + b.width) * Section.TILES_PER_SIDE][y]) {
                    openings[xCheck - 1][y] = true;
                }
            }
            for (int x = b.x * Section.TILES_PER_SIDE; x < (b.x + b.width) * Section.TILES_PER_SIDE;
                 x++) {
                int yCheck = b.y * Section.TILES_PER_SIDE - 1;
                if (yCheck >= 0 && fills[x][yCheck]) {
                    openings[x][yCheck + 1] = true;
                }
                yCheck = (b.y + b.height) * Section.TILES_PER_SIDE;
                if (yCheck < fills[0].length && fills[x][yCheck]) {
                    openings[x][yCheck - 1] = true;
                }
            }
        }
        return openings;
    }

    private void placeVerTrue(int startX, int startY, int length, boolean[][] openings) {
        for (int y = 0; y < length; y++) {
            openings[startX][startY + y] = true;
        }
    }

    private void placeHorTrue(int startX, int startY, int length, boolean[][] openings) {
        for (int x = 0; x < length; x++) {
            openings[startX + x][startY] = true;
        }
    }

    private int[][] getInts(TileType[][] mapTiles, TileType checkFor) {
        int[][] ints = new int[mapTiles.length][mapTiles[0].length];
        for (int x = 0; x < mapTiles.length; x++) {
            for (int y = 0; y < mapTiles[0].length; y++) {
                TileType tile = mapTiles[x][y];
                if (tile == checkFor) {
                    ints[x][y] = 1;
                }
            }
        }
        return ints;
    }

    /**
     * Normalizes the given list of bounds. What this means is that it makes sure no bounds have
     * negative coordinates, and the lowest coordinates will be (0, 0)
     *
     * @param boundsList List of bounds to normalize
     * @return IntVector of the shift that was applied
     */
    private IntVector2 normalizeBoundsList(Iterable<Bounds> boundsList) {
        IntVector2 shift = new IntVector2(Integer.MAX_VALUE, Integer.MAX_VALUE);

        for (Bounds bounds : boundsList) {
            if (bounds.x < shift.x) {
                shift.x = bounds.x;
            }
            if (bounds.y < shift.y) {
                shift.y = bounds.y;
            }
        }

        for (Bounds bounds : boundsList) {
            bounds.x -= shift.x;
            bounds.y -= shift.y;
        }

        return shift;
    }

    /**
     * Returns the max bounds of the given list.
     *
     * @param boundsList A normalized list of bounds-objects
     * @return The max bounds as an IntVector2
     */
    private IntVector2 getMaxBounds(Iterable<Bounds> boundsList) {
        IntVector2 max = new IntVector2(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Bounds bounds : boundsList) {

            if (bounds.x + bounds.width > max.x) {
                max.x = bounds.x + bounds.width;
            }

            if (bounds.y + bounds.height > max.y) {
                max.y = bounds.y + bounds.height;
            }

        }
        return max;
    }

    private ArrayList<Line> getPlatforms(int[][] ints) {
        ArrayList<Line> lines = new ArrayList<>();
        for (int x = 0; x < ints.length; x++) {
            for (int y = 0; y < ints[0].length; y++) {
                if (ints[x][y] == 1) {
                    float newY = y + 1;
                    lines.add(new Line(x, newY, x + 1, newY));
                }
            }
        }
        return lines;
    }
}
