package com.polarbirds.huldra.screen.game.overlay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.character.animate.player.PlayerCharacter;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.utility.IHasGraphics;
import com.polarbirds.huldra.model.utility.SpriteLoader;

import java.util.Map;

/**
 * Created by Harald on 21.07.2015.
 */
public class HudOverlay implements IOverlay, IHasGraphics {

  private PlayerCharacter[] players;

  public HudOverlay(PlayerCharacter[] players) {
    this.players = players;

  }

  @Override
  public void render(Batch batch) {

  }

  @Override
  public void init() {

  }

  @Override
  public void initGraphics(Map<String, ASprite> sprites, Map<String, AAnimation> animations) {

  }

  @Override
  public void queueAssets(SpriteLoader spriteLoader) {

  }
}
