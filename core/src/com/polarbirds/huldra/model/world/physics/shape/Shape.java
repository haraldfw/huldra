package com.polarbirds.huldra.model.world.physics.shape;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 29.6.15.
 */
public abstract class Shape {

    public abstract void debugDraw(Vector2 pos, ShapeRenderer sr);
}
