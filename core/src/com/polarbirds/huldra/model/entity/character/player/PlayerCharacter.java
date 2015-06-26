package com.polarbirds.huldra.model.entity.character.player;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.controller.player.Keyboard;
import com.polarbirds.huldra.controller.player.XboxController;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends AWalkingCharacter {

  public PlayerCharacter(Vector2 pos, World world, Team team, HuldraGame game) {
    super(pos, world, team);
    this.input = Controllers.getControllers().size > 0 ?
                 new XboxController(Controllers.getControllers().get(0))
                                                       : new Keyboard(game.camera);
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  public Vector2 getPosition() {
    return new Vector2(body.getPosition());
  }

  // TODO Implement a system for storing base-stats.
  @Override
  protected float getMoveStrength() {
    return 25f;
  }

  @Override
  protected float getJumpStrength() {
    return 5f;
  }
}
