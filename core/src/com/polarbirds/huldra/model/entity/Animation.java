package com.polarbirds.huldra.model.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A class for storing, updating and drawing animations.
 * Created by Harald Wilhelmsen on 10/6/2015.
 */
public class Animation {

  private Sprite[] frames;
  private float timePerFrame;

  private float timePassed;

  public Animation(Sprite[] frames, float timePerFrame) {
    this.frames = frames;
    this.timePerFrame = timePerFrame;
  }

  public boolean update(float delta) {
    timePassed += delta;
    float totalTime = timePerFrame * frames.length;
    if (timePassed > totalTime) {
      timePassed -= totalTime;
      return true;
    }
    return false;
  }

  public void draw(SpriteBatch sb, Body body) {
    Sprite s = frames[(int) (timePassed / timePerFrame)];
    Vector2 pos = body.getPosition();
    s.setPosition(s.getX() + pos.x, s.getY() + pos.x);
    s.draw(sb);
    s.setPosition(s.getX() - pos.x, s.getY() - pos.x);
  }
}
