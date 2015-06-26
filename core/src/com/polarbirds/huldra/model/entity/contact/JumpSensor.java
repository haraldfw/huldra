package com.polarbirds.huldra.model.entity.contact;

import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;

/**
 * Created by Harald on 28.5.15.
 */
public class JumpSensor implements SensorListener {

  private final AWalkingCharacter playerCharacter;

  public JumpSensor(AWalkingCharacter playerCharacter) {
    this.playerCharacter = playerCharacter;
  }

  @Override
  public void activate(Object userData) {
    if (userData != null && userData.equals("ground")) {
      playerCharacter.setOnGround(true);
    }
  }

  @Override
  public void deactivate() {
    playerCharacter.setOnGround(false);
  }
}
