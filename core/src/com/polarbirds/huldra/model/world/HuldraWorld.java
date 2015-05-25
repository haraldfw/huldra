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

/**
 * A class for this game's World. HuldraWorld contains a 2d array of Tile objects and an ArrayList
 * of Interactables. Created by Harald on 30.4.15.
 */
public final class HuldraWorld {

  public final World box2dWorld;

  private Parallax parallax;

  HuldraWorld(WorldType worldType, Iterable<Bounds> boundsList) {

    TilesWithOpenings.loadAndGetList();

    // normalize bounds. Since the spawn was previously on 0,0 it is now located on the shift
    // applied
    IntVector2 spawn = normalizeBoundsList(boundsList);

    box2dWorld = new World(new Vector2(0, -9.81f), false);

    IntVector2 maxBounds = getMaxBounds(boundsList);
    TileType[][] mapTiles =
        new TileType[maxBounds.x * Section.TILES_PER_SIDE][maxBounds.y * Section.TILES_PER_SIDE];

    boolean[][] reachableOpenings = new boolean[mapTiles.length][mapTiles[0].length];

    for (Bounds sectionBounds : boundsList) {
      TileType[][] sectionTiles = getTilesForSection(worldType, sectionBounds);
      int baseX = sectionBounds.x * Section.TILES_PER_SIDE;
      int baseY = sectionBounds.y * Section.TILES_PER_SIDE;
      for (int x = 0; x < sectionTiles.length; x++) {
        System.arraycopy(sectionTiles[x], 0, mapTiles[baseX + x], baseY, sectionTiles[0].length);
      }
    }

    // create int-array of what blocks are solid and not, to pass into UnifiablePolyedge
    int[][] ints = new int[mapTiles.length][mapTiles[0].length];
    for (int x = 0; x < mapTiles.length; x++) {
      for (int y = 0; y < mapTiles[0].length; y++) {
        TileType tile = mapTiles[x][y];
        if (tile != null) {
          switch (tile) {
            default:
              break;
            case SOLID:
              ints[x][y] = 1;
              break;
          }
        }
      }
    }

    UnifiablePolyedge p = new UnifiablePolyedge(ints);
    p.unify();
    createBodies(p.getEdges(), box2dWorld);
  }

  /**
   * Returns tiles for the given sectionBounds, taking into account the sectionBounds' openings
   */
  private TileType[][] getTilesForSection(WorldType type, Bounds bounds) {

    return placeholderTiles(bounds);
  }

  private TileType[][] placeholderTiles(Bounds bounds) {
    TileType[][] tiles =
        new TileType[bounds.width * Section.TILES_PER_SIDE][bounds.height * Section.TILES_PER_SIDE];
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[0].length; y++) {
        tiles[x][y] =
            x == 0 || y == 0 || x == tiles.length - 1 || y == tiles[0].length - 1 ? TileType.SOLID
                                                                                  : TileType.EMPTY;
      }
    }

    return tiles;
  }

  public void step(float delta) {
    box2dWorld.step(delta, 8, 8); // update box2d box2dWorld
  }

  private static void createBodies(Iterable<Line> edges, World box2dWorld) {
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

  /**
   * Normalizes the given list of bounds. What this means is that it makes sure no bounds have
   * negative coordinates, and the lowest coordinates will be (0, 0)
   *
   * @param boundsList List of bounds to normalize
   * @return IntVector of the shift that was applied
   */
  private static IntVector2 normalizeBoundsList(Iterable<Bounds> boundsList) {
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
  private static IntVector2 getMaxBounds(Iterable<Bounds> boundsList) {
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
}
