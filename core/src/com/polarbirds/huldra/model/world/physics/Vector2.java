package com.polarbirds.huldra.model.world.physics;

/**
 * Created by Harald on 29.6.15.
 */
public class Vector2 extends com.badlogic.gdx.math.Vector2 {

  public Vector2() {
    x = 0;
    y = 0;
  }

  public Vector2(float x, float y) {
    super(x, y);
  }

  public com.badlogic.gdx.math.Vector2 mulAdd(float x, float y, float scale) {
    this.x += x * scale;
    this.y += y * scale;
    return this;
  }

}
