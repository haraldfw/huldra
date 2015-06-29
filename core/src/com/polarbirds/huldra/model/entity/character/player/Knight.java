package com.polarbirds.huldra.model.entity.character.player;

import com.polarbirds.huldra.model.entity.character.Team;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  private static final float sMove = 25f;
  private static final float sDmg = 1f;

  public Knight(Vector2 pos, Team team, GameScreen game) {
    super(pos, team, game);
  }
}
