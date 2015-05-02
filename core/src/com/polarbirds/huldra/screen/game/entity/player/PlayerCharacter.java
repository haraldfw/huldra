package com.polarbirds.huldra.screen.game.entity.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.screen.game.GameScreen;
import com.polarbirds.huldra.screen.game.entity.BasicEntity;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends BasicEntity {

  GameScreen game;

  private final float mass;
  private final float moveStrength;
  private final float jumpStrength;
  private final float baseDamage;

  private Body body;

  public PlayerCharacter(GameScreen game, float mass, float moveStrength, float jumpStrength, float baseDamage) {
    super(null); // TODO replace null with image
    this.mass = mass;
    this.moveStrength = moveStrength;
    this.jumpStrength = jumpStrength;
    this.baseDamage = baseDamage;

    this.game = game;


  }

  @Override
  public void act(float delta) {
    super.act(delta);

  }

  @Override
  public void reset() {

  }
}
