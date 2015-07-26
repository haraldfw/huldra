package com.polarbirds.huldra.model.particleengine.effect.spark;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.particleengine.effect.AParticle;
import com.polarbirds.huldra.model.utility.RegionSprite;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 24/7/2015.
 */
public class SparkParticle extends AParticle {

    private static RegionSprite sprite;

    private Vector2[] pos;
    private Vector2 vel;

    public SparkParticle(Vector2 start, Vector2 vel) {
        super((float) Math.random() / 2);
        pos = new Vector2[5];
        pos[0] = start;
        for (int i = 1; i < pos.length; i++) {
            pos[i] = new Vector2(pos[0]);
        }
        this.vel = vel;
    }

    public SparkParticle(Vector2 pos) {
        this(pos, getRandomNormalized());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        /*for (int i = pos.length - 1; i >= 1; i--) {
            pos[i] = pos[i - 1];
        }*/
        System.arraycopy(pos, 0, pos, 1, pos.length - 1);
        vel.add(0, -0.1f * delta);
        pos[0] = new Vector2(pos[1]).mulAdd(vel.x, vel.y, delta);
    }

    @Override
    public void draw(Batch batch) {
        for (int i = 0; i - 1 < pos.length; i++) {
            float rotation = new Vector2(pos[i]).sub(pos[i + 1]).angle();
            batch.draw(sprite.textureRegion, pos[i].x, pos[i].y, 0, 0, 1, 1, 1, 1, rotation);
        }
    }
}
