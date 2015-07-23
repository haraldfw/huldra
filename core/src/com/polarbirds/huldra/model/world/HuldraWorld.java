package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.StaticBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class for this game's World. HuldraWorld contains a 2d array of Tile objects and an ArrayList
 * of Interactables. Created by Harald on 30.4.15.
 */
public final class HuldraWorld {


    public Level level;

    private List<DynamicBody> dynamicBodies;
    private List<StaticBody> staticBodies;


    public HuldraWorld() {
        dynamicBodies = new ArrayList<>();
        staticBodies = new ArrayList<>();
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

    public void draw(Batch batch) {
        level.draw(batch);
    }

    public void firstLevel(PlayerCharacter[] players, Random random) {
        level = new Level(players, random);
    }

    public void nextLevel(Random random) {
        level = new Level(level, random);
    }

    public void debugDraw(ShapeRenderer sr) {
        for (int x = 0; x < level.tiles.length; x++) {
            for (int y = 0; y < level.tiles[0].length; y++) {
                switch (level.tiles[x][y]) {
                    case PLATFORM:
                        sr.line(x, y + 1, x + 1, y + 1);
                        break;
                    case SOLID:
                        sr.rect(x, y, 1, 1);
                        break;
                }
            }
        }
        for (DynamicBody body : dynamicBodies) {
            body.debugDraw(sr);
        }
    }

    public void addDynamicBody(DynamicBody body) {
        dynamicBodies.add(body);
    }

    public void integrate(float delta) {
        for (DynamicBody body : dynamicBodies) {
            body.integrate(delta);
        }
    }
}
