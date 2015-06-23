package com.polarbirds.huldra.model.entity.character.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends AWalkingCharacter {

  public PlayerCharacter(Vector2 pos, World world, Team team, IMotiveProcessor input) {
    super(pos, world, team);
    this.input = input;
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
