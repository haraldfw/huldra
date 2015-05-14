package com.polarbirds.huldra.model.entity.player;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.controller.MotiveProcessor;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends Image {

  GameScreen game;

  private final float mass;
  private final float moveStrength;
  private final float jumpStrength;
  private final float baseDamage;

  private Body body;

  private MotiveProcessor input;

  public PlayerCharacter(Body body, float mass, float moveStrength, float jumpStrength,
                         float baseDamage, GameScreen game, MotiveProcessor input) {
    this.mass = mass;
    this.moveStrength = moveStrength;
    this.jumpStrength = jumpStrength;
    this.baseDamage = baseDamage;

    this.game = game;
    this.input = input;
  }

  @Override
  public void act(float delta) {
    super.act(delta);

  }
}
