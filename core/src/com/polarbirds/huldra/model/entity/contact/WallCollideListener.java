package com.polarbirds.huldra.model.entity.contact;

import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;

/**
 * Created by Harald on 28.5.15.
 */
public class WallCollideListener implements SensorListener {

  private final PlayerCharacter player;

  public WallCollideListener(PlayerCharacter player) {
    this.player = player;
  }

  @Override
  public void activate() {

  }
}
