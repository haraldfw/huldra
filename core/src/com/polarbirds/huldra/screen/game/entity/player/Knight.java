package com.polarbirds.huldra.screen.game.entity.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  public Knight(GameScreen screen,float mass, float moveStrength,
                float jumpStrength, float baseDamage) {
    super(screen, createBody(screen.world.box2dWorld), mass, moveStrength, jumpStrength, baseDamage);
  }

  @Override
  public void reset() {

  }

  private static Body createBody(World world) {
    return null;
  }
}
