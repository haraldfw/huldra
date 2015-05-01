package com.polarbirds.huldra.screen.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Class for storing a tile with it's information
 * Created by Harald on 30.4.15.
 */
public interface Tile {

  void draw(SpriteBatch sb);


  enum Type {
    DIRT,
    STONE,
  }
}
