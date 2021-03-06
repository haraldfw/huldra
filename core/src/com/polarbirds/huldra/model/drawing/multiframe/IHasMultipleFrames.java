package com.polarbirds.huldra.model.drawing.multiframe;

import com.polarbirds.huldra.model.drawing.drawable.IDrawable;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;

/**
 * Created by Harald Wilhelmsen on 29/7/2015.
 */
public interface IHasMultipleFrames {

  ASprite[] getFrames();

  default void set(IDrawable[] drawables) {
    for (int i = 0; i < drawables.length; i++) {
      getFrames()[i].set(drawables[i]);
    }
  }
}
