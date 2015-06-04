package com.polarbirds.huldra.model.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends Image {

  private static final float sJump = 5f; // probably should be around 8
  private final float moveStrength;
  private final float baseDamage;
  private final float halfWidth;
  private final float halfHeight;
  GameScreen game;
  private boolean canJump = false;
  private Body body;
  private IMotiveProcessor input;

  public PlayerCharacter(Body body, float halfWidth, float halfHeight, float moveStrength,
                         float baseDamage, GameScreen game,
                         IMotiveProcessor input) {
    this.body = body;
    this.halfWidth = halfWidth;
    this.halfHeight = halfHeight;
    this.moveStrength = moveStrength;
    this.baseDamage = baseDamage;

    this.game = game;
    this.input = input;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    input.update();
    if (input.jump()) {
      body.applyLinearImpulse(0, sJump, halfWidth, halfHeight, true);
    }
    body.applyForce(input.moveX() * moveStrength, 0, halfWidth, halfHeight, true);
  }

  public Vector2 getPosition() {
    return new Vector2(body.getPosition());
  }

  public void setCanJump(boolean canJump) {
    this.canJump = canJump;
  }
}
