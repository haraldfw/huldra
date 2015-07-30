package com.polarbirds.huldra.model.particleengine.effect.fire;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.drawing.singleframe.Sprite;
import com.polarbirds.huldra.model.particleengine.effect.AParticle;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 24/7/2015.
 */
public class FireParticle extends AParticle {

  private static Sprite sprite;

  private final Vector2 pos;
  private final float startX;
  private final Vector2 vel;

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

  @Override
  public void initGraphics(Map<String, ASprite> sprites, Map<String, AAnimation> animations) {

  }

  @Override
  public void queueAssets(SpriteLoader spriteLoader) {

  }
}
