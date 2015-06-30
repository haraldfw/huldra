package com.polarbirds.huldra.model.entity.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * A class for storing, updating and drawing animations. Created by Harald Wilhelmsen on 10/6/2015.
 */
public abstract class AAnimation {

  protected float timePassed;

  public void update(float delta) {
    timePassed += delta;
    float totalTime = getTotalTime();
    if (timePassed > totalTime) {
      timePassed -= totalTime;
    }
  }

  protected abstract float getTotalTime();

  protected abstract Sprite getCurrentFrame();

  public final void draw(Batch sb, Vector2 pos) {
    Sprite s = getCurrentFrame();
    s.setPosition(s.getX() + pos.x, s.getY() + pos.y);
    s.draw(sb);
    s.setPosition(s.getX() - pos.x, s.getY() - pos.x);
  }
}
