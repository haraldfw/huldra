package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.smokebox.lib.utils.IntVector2;
import com.smokebox.lib.utils.geom.Bounds;
import com.smokebox.lib.utils.geom.Line;
import com.smokebox.lib.utils.geom.UnifiablePolyedge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * A class for this game's World. HuldraWorld contains a 2d array of Tile objects and an ArrayList
 * of Interactables. Created by Harald on 30.4.15.
 */
public final class HuldraWorld {


  public final World box2dWorld;

  private Parallax parallax;

  HuldraWorld(WorldType worldType, Random random, Iterable<Bounds> boundsList) {
    List<TilesWithOpenings> twos = TilesWithOpenings.loadAndGetList();

    // normalize bounds. Since the spawn was previously on 0,0 it is now located on the shift
    // applied
    IntVector2 spawn = normalizeBoundsList(boundsList);

    box2dWorld = new World(new Vector2(0, -9.81f), false);

    Collection<Section> sections = new ArrayList<>();
    for (Bounds bounds : boundsList) {
      sections.add(new Section(bounds));
    }

    IntVector2 maxBounds = getMaxBounds(boundsList);

    // Array for map-tiles
    TileType[][] mapTiles =
        new TileType[maxBounds.x * Section.TILES_PER_SIDE + 2][maxBounds.y * Section.TILES_PER_SIDE
                                                               + 2];

    for (int x = 0; x < mapTiles.length; x++) {
      Arrays.fill(mapTiles[x], TileType.SOLID);
    }

    // Array of reachable openings
    boolean[][] reachableOpenings =
        addSectionOpenings(sections, new boolean[mapTiles.length][mapTiles[0].length]);

    // Add the section's tiles to the map
    for (Section section : sections) {
      TileType[][] sectionTiles = getTilesForSection(section, reachableOpenings, twos, random);
      int baseX = section.bounds.x * Section.TILES_PER_SIDE;
      int baseY = section.bounds.y * Section.TILES_PER_SIDE;
      for (int x = 0; x < sectionTiles.length; x++) {
        System.arraycopy(sectionTiles[x], 0, mapTiles[baseX + x + 1], baseY + 1,
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

    UnifiablePolyedge p = new UnifiablePolyedge(getInts(mapTiles, TileType.SOLID));
    p.unify();
    createBodies(p.getEdges());
    p = new UnifiablePolyedge(getPlatforms(getInts(mapTiles, TileType.PLATFORM)));
    p.unify();
    createBodies(p.getEdges());
  }

  public void step(float delta) {
    box2dWorld.step(delta, 8, 8); // update box2dWorld
  }

  /**
   * Returns tiles for the given sectionBounds, taking into account the sectionBounds' openings
   */
  private TileType[][] getTilesForSection(Section section, boolean[][] openings,
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
    return candidates.size() != 0 ? candidates.get(random.nextInt(candidates.size())).tiles
                                  : placeholderTiles(section);
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
        for (int y = b.y * Section.TILES_PER_SIDE; y < (b.y + b.height) * Section.TILES_PER_SIDE;
             y++) {
          fills[x][y] = true;
        }
      }
    }

    // Make sure boundaries of sections are true in reachableOpenings-array
    for (Section section : sections) {
      Bounds b = section.bounds;
      for (int y = b.y * Section.TILES_PER_SIDE; y < (b.y + b.height) * Section.TILES_PER_SIDE;
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

  private void createBodies(Iterable<Line> edges) {
    for (Line l : edges) {
      EdgeShape edgeShape = new EdgeShape();
      edgeShape.set(l.x, l.y, l.x2, l.y2);

      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.shape = edgeShape;

      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyDef.BodyType.StaticBody;
      Body body = box2dWorld.createBody(bodyDef);

      body.createFixture(fixtureDef);
    }
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

  public static Parallax getParallax(OrthographicCamera camera, WorldType type) {
    Parallax parallax = null;

    switch (type) {
      case FOREST:
        // parallax = new Parallax(..);
        break;
      case CAVES:
        // parallax = new Parallax(..);
        break;
      default: // case TEST_STAGE
        // parallax = new Parallax(..);
        break;
    }
    return parallax;
  }
}
