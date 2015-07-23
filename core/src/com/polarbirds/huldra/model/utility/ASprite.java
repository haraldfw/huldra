package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 14.7.15.
 */
public abstract class ASprite implements Disposable {

    protected Vector2 shift;

    public ASprite(Vector2 shift) {
        this.shift = shift;
    }

    public abstract void draw(Batch sb, Vector2 pos);
}
