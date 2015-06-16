package com.polarbirds.huldra.model.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * A class for storing, updating and drawing animations. Created by Harald Wilhelmsen on 10/6/2015.
 */
public abstract class AAnimation {

  protected float timePassed;

  public final boolean update(float delta) {
    timePassed += delta;
    float totalTime = getTotalTime();
    if (timePassed > totalTime) {
      timePassed -= totalTime;
      return true;
    }
    return false;
  }

  protected abstract float getTotalTime();

  protected abstract Sprite getCurrentFrame();

  public final void draw(SpriteBatch sb, Vector2 pos) {
    Sprite s = getCurrentFrame();
    s.setPosition(s.getX() + pos.x, s.getY() + pos.y);
    s.draw(sb);
    s.setPosition(s.getX() - pos.x, s.getY() - pos.x);
  }
}
