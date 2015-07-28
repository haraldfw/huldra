package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Harald on 19.07.2015.
 */
public class Level {

    public final int difficulty;
    public Tile[][] tiles;
    public Vector2 spawn;
    public PlayerCharacter[] players;

    public Level(PlayerCharacter[] players, Random random) {
        this.players = players;
        difficulty = 1; // 1 is starting difficulty, increments by 1 for each new
        parseLevel(difficulty, random);
    }

    public Level(Level lastLevel, Random random) {
        players = lastLevel.players;
        difficulty = lastLevel.difficulty + 1;
        parseLevel(difficulty, random);
    }

    public void draw(Batch batch) {
        batch.begin();
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                batch.draw(tiles[x][y].texture,
                           x * HuldraGame.PIXELS_PER_TILESIDE,
                           y * HuldraGame.PIXELS_PER_TILESIDE,
                           HuldraGame.PIXELS_PER_TILESIDE,
                           HuldraGame.PIXELS_PER_TILESIDE
                );
            }
        }
        batch.end();
    }

    private void parseLevel(int level, Random random) {
        try {
            BufferedReader reader = new BufferedReader(
                new FileReader(new File("levels/" + level + ".lvl")));

            WorldType type;
            String typeString = reader.readLine();
            switch (typeString) {
                case "caves":
                    type = WorldType.CAVES;
                    break;
                case "forest":
                    type = WorldType.FOREST;
                    break;
                default:
                    type = WorldType.TEST_STAGE;
            }
            TileArrayWithSpawn tileArrayWithSpawn =
                new WorldGenerator(type, random)
                    .asTiles(Integer.parseInt(reader.readLine()), random);
            tiles = convertTiles(tileArrayWithSpawn.tiles);
            spawn = tileArrayWithSpawn.spawn;

            placeTextures(tiles, parseTextures(typeString));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private HashMap<String, ArrayList<Texture>> parseTextures(String typeString) {
        HashMap<String, ArrayList<Texture>> textureLists = new HashMap<>();
        String dirString = "graphics/world/tiles/" + typeString + "/";
        for (String path : new File(dirString).list()) {
            if (path.contains(".png")) {
                System.out.println(path);
                String key = path.substring(0, path.lastIndexOf("_"));
                Texture t = new Texture(Gdx.files.internal(dirString + path));
                if (!textureLists.containsKey(key)) {
                    textureLists.put(key, new ArrayList<>());
                }
                textureLists.get(key).add(t);
            }
        }
        return textureLists;
    }

    private void placeTextures(Tile[][] tiles,
                               HashMap<String, ArrayList<Texture>> textureLists) {
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[0].length; y++) {
                tiles[x][y].setTexture(getTexture(textureLists, tiles, x, y));
            }
        }
    }

    private Texture getTexture(HashMap<String, ArrayList<Texture>> textureMap,
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
}
