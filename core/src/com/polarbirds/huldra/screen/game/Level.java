package com.polarbirds.huldra.screen.game;

import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.world.HuldraWorld;
import com.polarbirds.huldra.model.world.WorldType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Harald on 19.07.2015.
 */
public class Level {
    PlayerCharacter player;
    private HuldraWorld world;

    public Level(PlayerCharacter player, int level, Random random) {
        this.player = player;
        parseLevel(level, random);
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

            world = new HuldraWorld(sectionAmount, type, random);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
