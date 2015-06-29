package com.polarbirds.huldra.model.world.physics.shape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 29.6.15.
 */
public class RectShape extends Shape {

  public float width;
  public float height;

  public RectShape(float width, float height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public void debugDraw(Vector2 pos, ShapeRenderer sr) {
    sr.rect(pos.x, pos.y, width, height);
  }
}
