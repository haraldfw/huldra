package com.polarbirds.huldra.model.entity.animation;

import com.polarbirds.huldra.model.utility.Sprite;

/**
 * A class for animations with different frametimes per frame. Created by Harald Wilhelmsen on
 * 16/6/2015.
 */
public class AdvancedAnimation extends AAnimation {

  private final Sprite[] frames;
  private final float[] frameTimes;

  public AdvancedAnimation(Sprite[] frames, float[] frameTimes) {
    super();
    this.frames = frames;
    this.frameTimes = frameTimes;
  }

  @Override
  protected float getTotalTime() {
    float totalTime = 0;
    for (float f : frameTimes) {
      totalTime += f;
    }
    return totalTime;
  }

  @Override
  protected Sprite getCurrentFrame(Object caller) {
    float t = 0;
    for (int i = 0; i < frames.length; i++) {
      t += frameTimes[i];
      if (t > timePassed.get(this)) {
        return frames[i];
      }
    }
    return frames[0];
  }

}
