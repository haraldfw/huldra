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

    public LevelFile(int level, SpriteLoader spriteLoader) {
        try {
            BufferedReader reader = new BufferedReader(
                new FileReader(new File("levels/" + level + ".lvl")));

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
            // TODO queue assets in spriteLoader
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
