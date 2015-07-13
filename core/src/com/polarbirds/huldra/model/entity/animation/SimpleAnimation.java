package com.polarbirds.huldra.model.entity.animation;

import com.polarbirds.huldra.model.utility.Sprite;

/**
 * A class for animations with equal frametimes for each frame. Created by Harald Wilhelmsen on
 * 16/6/2015.
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
