package com.polarbirds.huldra.model.entity.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.controller.MotiveProcessor;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  public Knight(GameScreen screen, float mass, float moveStrength,
                float jumpStrength, float baseDamage, MotiveProcessor input) {
    super(createBody(screen.world.box2dWorld),
          mass, moveStrength, jumpStrength, baseDamage, screen, input);
  }

  private static Body createBody(World world) {

    return null;
  }
}
