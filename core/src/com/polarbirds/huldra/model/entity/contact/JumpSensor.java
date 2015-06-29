package com.polarbirds.huldra.model.entity.contact;

import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;

/**
 * Created by Harald on 28.5.15.
 */
public class JumpSensor implements SensorListener {

  private final AWalkingCharacter character;

  public JumpSensor(AWalkingCharacter character) {
    this.character = character;
  }

  @Override
  public void activate(Object userData) {
    if (userData != null && userData.equals("ground")) {
      character.setOnGround(true);
    } else {
      deactivate();
    }
  }

  @Override
  public void deactivate() {
    character.setOnGround(false);
  }
}
