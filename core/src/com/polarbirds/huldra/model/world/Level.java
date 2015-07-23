package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Harald on 19.07.2015.
 */
public class Level {

    public final int difficulty;
    public TileType[][] tiles;
    public Vector2 spawn;
    public PlayerCharacter[] players;
    private HashMap<TileType, Texture> tileTextures;

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

    }

    private void parseLevel(int level, Random random) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("level/" + level)));

            WorldType type;
            String readLine = reader.readLine();
            switch (readLine) {
                case "caves":
                    type = WorldType.CAVES;
                    break;
                case "forest":
                    type = WorldType.FOREST;
                    break;
                default:
                    type = WorldType.TEST_STAGE;
            }
            int sectionAmount = Integer.parseInt(reader.readLine());
            TileArrayWithSpawn tileArrayWithSpawn =
                    new BoundGenerator(type, random).asTiles(sectionAmount, random);
            tiles = tileArrayWithSpawn.tiles;
            spawn = tileArrayWithSpawn.spawn;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
