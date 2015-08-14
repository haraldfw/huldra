package com.polarbirds.huldra.model.entity.character.player;

import com.polarbirds.huldra.model.entity.ADrawableDynamic;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends APlayerCharacter {

  public static final String CHARACTER_NAME = "knight";

  public Knight(GameScreen gameScreen) {
    super(gameScreen.level,
          ADrawableDynamic.getAnimations("graphics/player/knight", gameScreen.animations),
          gameScreen.loadedStatHandler.getCopy("knight"), gameScreen.game);
  }

  @Override
  public void init(Vector2 pos, GameScreen gameScreen) {
    super.init(pos, gameScreen);
  }

  @Override
  public String getCharacterName() {
    return CHARACTER_NAME;
  }
}