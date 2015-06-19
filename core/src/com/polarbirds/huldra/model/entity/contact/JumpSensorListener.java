package com.polarbirds.huldra.model.entity.contact;

import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;

/**
 * Created by Harald on 28.5.15.
 */
public class JumpSensorListener implements SensorListener {

  private final PlayerCharacter playerCharacter;

  public JumpSensorListener(PlayerCharacter playerCharacter) {
    this.playerCharacter = playerCharacter;
  }

  @Override
  public void activate() {
    playerCharacter.setCanJump(true);
  }
}
