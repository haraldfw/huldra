package com.polarbirds.huldra.model.world.physics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polarbirds.huldra.model.world.physics.shape.Shape;

/**
 * Created by Harald on 29.6.15.
 */
public abstract class ABody {

  public Vector2 pos;
  public Shape shape;

  public ABody(Vector2 pos, Shape shape) {
    this.pos = pos;
    this.shape = shape;
  }

  public final void debugDraw(ShapeRenderer sr) {
    shape.debugDraw(pos, sr);
  }
}
