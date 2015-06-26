package com.polarbirds.huldra.model.entity.character.player;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.controller.player.Keyboard;
import com.polarbirds.huldra.controller.player.XboxController;
import com.polarbirds.huldra.model.entity.Team;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  private static final float sMove = 25f;
  private static final float sDmg = 1f;

  public Knight(Vector2 pos, World world, Team team, HuldraGame game) {
    super(pos, world, team, game);
  }

  @Override
  protected float getHalfWidth() {
    return 0.25f;
  }

  @Override
  protected float getHalfHeight() {
    return 0.35f;
  }
}
