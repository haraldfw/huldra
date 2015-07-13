package com.polarbirds.huldra.model.entity.animation;

import com.polarbirds.huldra.model.utility.Sprite;

/**
 * A class for animations with only one sprite Created by Harald Wilhelmsen on 16/6/2015.
 */
public class StaticAnimation extends AAnimation {

  private final Sprite s;

  public StaticAnimation(Sprite s) {
    this.s = s;
  }

  @Override
  public void update(Object caller, float delta) {
  }

  @Override
  protected float getTotalTime() {
    return 0;
  }

  @Override
  protected Sprite getCurrentFrame(Object caller) {
    return s;
  }

}
