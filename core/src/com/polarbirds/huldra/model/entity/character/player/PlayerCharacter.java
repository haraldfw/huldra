package com.polarbirds.huldra.model.entity.character.player;

import com.badlogic.gdx.controllers.Controllers;
import com.polarbirds.huldra.controller.player.Keyboard;
import com.polarbirds.huldra.controller.player.XboxController;
import com.polarbirds.huldra.model.entity.character.Team;
import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends AWalkingCharacter {

  public PlayerCharacter(Vector2 pos, Team team, GameScreen game) {
    super(pos, 0.5f, 0.7f, 0.0167f, game, team);
    this.input = Controllers.getControllers().size > 0 ?
                 new XboxController(Controllers.getControllers().get(0))
                                                       : new Keyboard(game.game.camera);
  }

  @Override
  public void act(float delta) {
    super.act(delta);

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
