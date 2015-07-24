package com.polarbirds.huldra.model.entity.particleengine.particle;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 24/7/2015.
 */
public abstract class AParticle {

    private float time;
    private float maxLife;

    protected AParticle(float maxLife) {
        time = 0;
        this.maxLife = maxLife;
    }

    public void update(float delta) {
        time += delta;
    }

    public abstract void draw(Batch batch);

    public boolean isAlive() {
        return time < maxLife;
    }

    protected static Vector2 getRandomNormalized() {
        return new Vector2((float) Math.random() - 0.5f, (float) Math.random() - 0.5f).nor();
    }
}
