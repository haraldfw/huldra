package com.polarbirds.huldra.model.entity.particleengine.effect.fire;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.entity.particleengine.effect.AParticle;
import com.polarbirds.huldra.model.utility.Sprite;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 24/7/2015.
 */
public class FireParticle extends AParticle {

    private static Sprite sprite;

    private Vector2 pos;
    private float startX;
    private Vector2 vel;

    public FireParticle(Vector2 pos, Vector2 vel) {
        super(0.25f + (float) Math.random() * 0.75f);
        this.pos = pos;
        this.vel = vel;
        startX = pos.x;
    }

    public FireParticle(Vector2 pos) {
        this(pos, getRandomNormalized().add(0, 1));
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        vel.add((startX - pos.x) * delta, 10);
    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch, pos);
    }
}
