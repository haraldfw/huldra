package com.polarbirds.huldra.model.utility;

import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 30/7/2015.
 */
public interface IHasGraphics {

  void queueAssets(SpriteLoader spriteLoader);

  void initGraphics(Map<String, ASprite> sprites, Map<String, AAnimation> animations);
}
