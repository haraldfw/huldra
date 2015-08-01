package com.polarbirds.huldra.model.character.animate.player;

import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.animate.CharacterState;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.Map;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends APlayerCharacter {

  public Knight(Map<CharacterState, AAnimation> animations, StatModifier[] baseStats, Team team) {
    super(animations, baseStats, team);
  }

  @Override
  public void init(Vector2 pos, GameScreen gameScreen) {
    super.init(pos, gameScreen);
  }
}