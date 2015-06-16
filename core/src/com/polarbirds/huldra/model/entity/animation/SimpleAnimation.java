package com.polarbirds.huldra.model.entity.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A class for animations with equal framtimes for each frame.
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public class SimpleAnimation extends AAnimation {

  private final Sprite[] frames;
  private final float timePerFrame;

  SimpleAnimation(Sprite[] frames, float timePerFrame) {
    this.frames = frames;
    this.timePerFrame = timePerFrame;
  }

  @Override
  protected float getTotalTime() {
    return timePerFrame * frames.length;
  }

  @Override
  protected Sprite getCurrentFrame() {
    return frames[(int) (timePassed / timePerFrame)];
  }
}
