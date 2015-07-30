package com.polarbirds.huldra.model.world.physics;

/**
 * Created by Harald on 29.6.15.
 */
public class Vector2 extends com.badlogic.gdx.math.Vector2 {

  public Vector2() {
    x = 0;
    y = 0;
  }

  public Vector2(Vector2 v) {
    this.x = v.x;
    this.y = v.y;
  }

  public Vector2(float x, float y) {
    super(x, y);
  }

  public Vector2 mulAdd(float x, float y, float scale) {
    this.x += x * scale;
    this.y += y * scale;
    return this;
  }

  public Vector2 mulAdd(Vector2 v, float scale) {
    this.x += v.x * scale;
    this.y += v.y * scale;
    return this;
  }

  public Vector2 add(float x, float y) {
    super.add(x, y);
    return this;
  }

  public Vector2 sub(float x, float y) {
    super.sub(x, y);
    return this;
  }

  public Vector2 add(Vector2 v) {
    super.add(v);
    return this;
  }

  public Vector2 sub(Vector2 v) {
    super.sub(v);
    return this;
  }

  public Vector2 scl(float scalar) {
    super.scl(scalar);
    return this;
  }

  public Vector2 nor() {
    super.nor();
    return this;
  }
}
