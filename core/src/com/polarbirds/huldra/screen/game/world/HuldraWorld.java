package com.polarbirds.huldra.screen.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.screen.game.world.inanimateObject.Interactable;
import com.smokebox.lib.utils.geom.UnifiablePolyedge;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for this game's World. HuldraWorld contains a 2d array of Tile objects
 * and an ArrayList of Interactables.
 * Created by Harald on 30.4.15.
 */
public final class HuldraWorld {

  public final World box2dWorld;

  private Parallax parallax;

  private HuldraWorld(TileType[][] tiles, ArrayList<Interactable> interactables) {

    box2dWorld = new World(new Vector2(0, -9.81f), false);

    int[][] ints = new int[tiles.length][tiles[0].length];

    for(int x = 0; x < tiles.length; x++) {
      for(int y = 0; y < tiles[0].length; y++) {
        switch (tiles[x][y]) {
          default:
            break;
          case SOLID:
            ints[x][y] = 1;
            break;
        }
      }
    }

    UnifiablePolyedge polyedge = new UnifiablePolyedge();
  }

  public void step(float delta) {
    box2dWorld.step(delta, 8, 8); // update box2d box2dWorld
  }

  public static Parallax getParallax(OrthographicCamera camera, WorldTypes type) {
    Parallax parallax = null;

    switch (type) {
      case FOREST:
        // parallax = new Parallax(..);
        break;
      case CAVES_DEEP:
        // parallax = new Parallax(..);
        break;
      default: // case TEST_STAGE
        // parallax = new Parallax(..);
        break;
    }
    return parallax;
  }

  public enum WorldTypes {

    FOREST {
      @Override
      public HuldraWorld getNew(String seed, OrthographicCamera camera) {
        TileType[][] tiles = getEmptyTilesArray(16, 16);
        ArrayList<Interactable> interactables = new ArrayList<>();

        // generate

        return new HuldraWorld(tiles, interactables);
      }
    },

    CAVES_DEEP {
      @Override
      public HuldraWorld getNew(String seed, OrthographicCamera camera) {
        TileType[][] tiles = getEmptyTilesArray(16, 16);
        ArrayList<Interactable> interactables = new ArrayList<>();

        // generate

        return new HuldraWorld(tiles, interactables);
      }
    },

    TEST_STAGE {
      @Override
      public HuldraWorld getNew(String seed, OrthographicCamera camera) {
        TileType[][] tiles = getEmptyTilesArray(16, 16);
        ArrayList<Interactable> interactables = new ArrayList<>();

        for(int x = 0; x < tiles.length; x++) {
          for (int y = 0; y < tiles[0].length; y++) {
            if(x == 0 || x == tiles.length - 1 || y == 0 || y == tiles[0].length - 1) {
              tiles[x][y] = TileType.SOLID;
            }
          }
        }
        // generate

        return new HuldraWorld(tiles, interactables);
      }
    }

    ;

    public abstract HuldraWorld getNew(String seed, OrthographicCamera camera);
  }

  private static TileType[][] getEmptyTilesArray(int width, int height) {
    TileType[][] tiles = new TileType[width][height];

    for(int x = 0; x < tiles.length; x++)
        for(int y = 0; y < tiles[x].length; y++)
          tiles[x][y] = TileType.EMPTY;

    return tiles;
  }

  private enum TileType {
    EMPTY,
    SOLID,
    PLATFORM,
    LADDER
  }

  private enum SectionType {
    ALL,
    TOP,
    LEFT,
    RIGHT,
    DOWN,
    LEFT_RIGHT,
    TOP_DOWN
  }
}
