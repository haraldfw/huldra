package com.polarbirds.huldra.model.world;

import com.polarbirds.huldra.model.utility.SpriteLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Harald Wilhelmsen on 28/7/2015.
 */
public class LevelFile {

    public String worldTypeString;
    public WorldType type;
    public int amountOfSections;

    public LevelFile(int level) {
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
            amountOfSections = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void queueAssets(SpriteLoader loader) {

    }
}
