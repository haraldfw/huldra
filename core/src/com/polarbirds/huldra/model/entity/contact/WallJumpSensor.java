package com.polarbirds.huldra.model.entity.contact;

import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;

/**
 * Created by Harald Wilhelmsen on 23/6/2015.
 */
public class WallJumpSensor implements SensorListener {

  private final AWalkingCharacter character;
  private final boolean left;

  public WallJumpSensor(AWalkingCharacter character, boolean left) {
    this.character = character;
    this.left = left;
  }

  @Override
  public void activate(Object userData) {
    character.body.getFixtureList().get(0).setFriction(0);
  }

  @Override
  public void deactivate() {
    character.body.getFixtureList().get(0).setFriction(1);
  }
}
