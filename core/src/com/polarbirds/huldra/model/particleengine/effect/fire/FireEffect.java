package com.polarbirds.huldra.model.particleengine.effect.fire;

import com.polarbirds.huldra.model.particleengine.ParticleEngine;
import com.polarbirds.huldra.model.particleengine.effect.AParticle;
import com.polarbirds.huldra.model.particleengine.effect.IEffect;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public class FireEffect implements IEffect {

  private Vector2 pos;
  private int particlesPerSecond;
  private float radius;
  private ParticleEngine engine;

  public FireEffect(ParticleEngine engine, int particlesPerSecond, float radius) {
    this.engine = engine;
    this.particlesPerSecond = particlesPerSecond;
    this.radius = radius;
  }

  @Override
  public void update(float delta, Vector2 pos) {
    Vector2 vel;
    if (this.pos == null) {
      this.pos = new Vector2(pos);
    }
    vel = new Vector2(pos).sub(this.pos).scl(delta);

    int particlesThisIteration = (int) Math.ceil(particlesPerSecond / delta);

    for (int i = 0; i < particlesThisIteration; i++) {
      engine.addParticle(
          new FireParticle(pos, AParticle.getRandomNormalized().scl(vel.len()).add(vel)));
    }
  }
}
