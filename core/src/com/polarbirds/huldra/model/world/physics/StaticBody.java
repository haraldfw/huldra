package com.polarbirds.huldra.model.world.physics;

import com.polarbirds.huldra.model.world.physics.shape.AShape;

/**
 * Created by Harald on 29.6.15.
 */
public class StaticBody extends ABody {

  public StaticBody(Vector2 pos, AShape shape) {
    super(pos, shape);

  }
}
