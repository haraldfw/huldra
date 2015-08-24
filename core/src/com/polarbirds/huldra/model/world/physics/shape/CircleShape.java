package com.polarbirds.huldra.model.world.physics.shape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 29.6.15.
 */
public class CircleShape extends AShape {

  public final float radius;

  public CircleShape(float radius) {
    this.radius = radius;
  }

  @Override
  public void debugDraw(Vector2 pos, ShapeRenderer sr) {
    sr.circle(pos.x, pos.y, radius);
  }

  @Override
  public float getW() {
    return radius * 2;
  }

  @Override
  public float getH() {
    return radius * 2;
  }
}
