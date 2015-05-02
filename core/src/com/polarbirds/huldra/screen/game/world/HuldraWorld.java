package com.polarbirds.huldra.screen.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.screen.game.world.inanimateObject.Interactable;

import java.util.ArrayList;

/**
 * A class for this game's World. The world contains a 2d array of Tile objects
 * and an ArrayList of Interactables.
 * Created by Harald on 30.4.15.
 */
public final class HuldraWorld {

  World world;

  private HuldraWorld(Tile[][] tiles, ArrayList<Interactable> interactables) {
    world = new World(new Vector2(0, -9.81f), false);
  }

  public void step(float delta) {
    world.step(delta, 8, 8); // update box2d world
  }

  public enum Generate {
    CAVES_DEEP {
      @Override
      public HuldraWorld generate(String seed) {
        Tile[][] tiles = new Tile[0][];
        ArrayList<Interactable> interactables = new ArrayList<>();

        // generate

        return new HuldraWorld(tiles, interactables);
      }
    },

    TEST_STAGE {
      @Override
      public HuldraWorld generate(String seed) {
        Tile[][] tiles = new Tile[0][];
        ArrayList<Interactable> interactables = new ArrayList<>();

        // generate

        return new HuldraWorld(tiles, interactables);
      }
    }

    ;

    public abstract HuldraWorld generate(String seed);
  }
}
