package com.polarbirds.huldra.model.character.animate.player;

import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.animate.CharacterState;
import com.polarbirds.huldra.model.character.stat.StatLoader;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 31/7/2015.
 */
public enum PlayerLoading {

  KNIGHT {
    @Override
    public APlayerCharacter getInstance(GameScreen gameScreen, Vector2 pos) {
      Map<CharacterState, AAnimation> animations = new HashMap<>();
      animations.put(CharacterState.WALKING,
                     gameScreen.animations.get("graphics/player/knight/walk"));
      animations.put(CharacterState.CLIMBING,
                     gameScreen.animations.get("graphics/player/knight/climb"));
      animations.put(CharacterState.FALLING,
                     gameScreen.animations.get("graphics/player/knight/fall"));
      animations.put(CharacterState.IDLE,
                     gameScreen.animations.get("graphics/player/knight/idle"));
      animations.put(CharacterState.HANGING,
                     gameScreen.animations.get("graphics/player/knight/hang"));
      StatModifier[] baseStats = gameScreen.loadedStatHandler.getCopy("stats/knight");
      return new Knight(animations, baseStats, Team.PLAYER);
    }

    @Override
    public void queueAssets(SpriteLoader spriteLoader, StatLoader statLoader) {

    }
  };

  public abstract APlayerCharacter getInstance(GameScreen gameScreen, Vector2 pos);

  public abstract void queueAssets(SpriteLoader spriteLoader, StatLoader statLoader);
}
