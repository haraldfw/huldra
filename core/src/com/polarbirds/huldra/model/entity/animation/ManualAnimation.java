package com.polarbirds.huldra.model.entity.animation;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Harald Wilhelmsen on 30/6/2015.
 */
public class ManualAnimation extends AAnimation {

  private Sprite[] frames;
  private int activeFrame;

  public ManualAnimation(Sprite[] frames) {
    this.frames = frames;
    activeFrame = 0;
  }

  public void setFrame(int frameId) {
    activeFrame = frameId;
  }

  @Override
  public void update(float delta) {
  }

  @Override
  protected float getTotalTime() {
    return 0;
  }

  @Override
  protected Sprite getCurrentFrame() {
    return frames[activeFrame];
  }
}
