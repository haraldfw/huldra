package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.model.entity.inanimateobject.Interactable;
import com.smokebox.lib.utils.geom.Line;
import com.smokebox.lib.utils.geom.UnifiablePolyedge;

import java.util.ArrayList;

/**
 * A class for this game's World. HuldraWorld contains a 2d array of Tile objects
 * and an ArrayList of Interactables.
 * Created by Harald on 30.4.15.
 */
public final class HuldraWorld {

  public final World box2dWorld;

  private Parallax parallax;

  HuldraWorld(WorldType worldType, Iterable<SectionBounds> sections,
              ArrayList<Interactable> interactables) {

    box2dWorld = new World(new Vector2(0, -9.81f), false);

    int boundx1 = Integer.MAX_VALUE;
    int boundx2 = Integer.MIN_VALUE;
    int boundy1 = Integer.MAX_VALUE;
    int boundy2 = Integer.MIN_VALUE;

    for(SectionBounds sectionBounds : sections) {
      if(sectionBounds.x < boundx1) boundx1 = sectionBounds.x;
      if(sectionBounds.x + sectionBounds.width > boundx2) boundx2 = sectionBounds.x + sectionBounds.width;
      if(sectionBounds.y < boundy1) boundy1 = sectionBounds.y;
      if(sectionBounds.y + sectionBounds.height > boundy2) boundy2 = sectionBounds.y + sectionBounds.height;
    }

    TileType[][] mapTiles = new TileType[boundx2 - boundx1][boundy2 - boundy1];
    for(SectionBounds sectionBounds : sections) {
      TileType[][] sectionTiles = getTilesForSection(worldType, sectionBounds);
      for (int x = 0; x < sectionTiles.length; x++) {
        System.arraycopy(sectionTiles[x], 0,
                         mapTiles[sectionBounds.x + x], sectionBounds.x, sectionTiles[x].length);
      }
    }

    // create int-array of what blocks are solid and not to pass into UnifiablePolyedge
    int[][] ints = new int[mapTiles.length][mapTiles[0].length];
    for(int x = 0; x < mapTiles.length; x++) {
      for(int y = 0; y < mapTiles[0].length; y++) {
        TileType tile = mapTiles[x][y];
        if(tile != null) {
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

    for(Line l : p.getEdges()) {
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

  public void step(float delta) {
    box2dWorld.step(delta, 8, 8); // update box2d box2dWorld
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
   * Returns tiles for the given sectionBounds, taking into account the sectionBounds openings
   */
  static TileType[][] getTilesForSection(WorldType type, SectionBounds sectionBounds) {
    TileType[][] tiles =
        new TileType
            [sectionBounds.width* Section.TILES_PER_SIDE]
            [sectionBounds.height* Section.TILES_PER_SIDE];
    for(int x = 0; x < tiles.length; x++) {
      for(int y = 0; y < tiles[0].length; y++) {

      }
    }
    return Section.getTiles();
  }
}
