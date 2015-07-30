package com.polarbirds.huldra.model.particleengine;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.particleengine.effect.AParticle;
import com.polarbirds.huldra.model.utility.SpriteLoader;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 24/7/2015.
 */
public class ParticleEngine {

  private ArrayList<AParticle> particles;
  private ArrayList<Integer> toRemove;

  public ParticleEngine() {
    particles = new ArrayList<>();
    toRemove = new ArrayList<>();
  }

  public static void queueAssets(SpriteLoader loader) { // queue the particles' sprites

  }

  public void update(float delta) {
    for (int i = 0; i < particles.size(); i++) {
      AParticle particle = particles.get(i);
      if (particle.isAlive()) {
        particle.update(delta);
      } else {
        toRemove.add(i);
      }
    }
    for (int i = toRemove.size() - 1; i >= 0; i--) {
      particles.remove(i);
    }
  }

  public void draw(Batch batch) {
    for (AParticle particle : particles) {
      particle.draw(batch);
    }
  }

  public void addParticle(AParticle particle) {
    particles.add(particle);
  }
}
