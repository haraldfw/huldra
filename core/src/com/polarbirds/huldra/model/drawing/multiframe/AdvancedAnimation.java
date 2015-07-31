package com.polarbirds.huldra.model.drawing.multiframe;

import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;

/**
 * A class for animations with different frametimes per frame. Created by Harald Wilhelmsen on
 * 16/6/2015.
 */
public class AdvancedAnimation extends AAnimation {

  private final float[] frameTimes;
  private ASprite[] frames;

  public AdvancedAnimation(float[] frameTimes) {
    super();
    this.frameTimes = frameTimes;
  }

  public AdvancedAnimation(ASprite[] frames, float[] frameTimes) {
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
  protected ASprite getCurrentFrame(Object caller) {
    float t = 0;
    for (int i = 0; i < frames.length; i++) {
      t += frameTimes[i];
      if (t > timePassed.get(this)) {
        return frames[i];
      }
    }
    return frames[0];
  }

  @Override
  public ASprite[] getFrames() {
    return frames;
  }
}
