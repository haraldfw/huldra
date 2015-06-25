package com.polarbirds.huldra.model.entity.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.contact.JumpSensor;
import com.polarbirds.huldra.model.entity.contact.SensorListener;
import com.polarbirds.huldra.model.entity.contact.WallJumpSensor;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class AWalkingCharacter extends ACharacter {

  public IMotiveProcessor input;

  private boolean canJump = false;

  public AWalkingCharacter(Vector2 pos, World world, Team team) {
    super(pos, world, team);
    applySensors();
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    input.update();
    if (canJump && input.jump()) {
      body.applyLinearImpulse(0, getJumpStrength(), getHalfWidth(), getHalfHeight(), true);
    }
    body.applyForce(input.moveX() * getMoveStrength(), 0, getHalfWidth(), getHalfHeight(), true);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
  }

  public void setCanJump(boolean canJump) {
    this.canJump = canJump;
  }

  private void applySensors() {
    float sensorThickness = 0.05f;
    float xShift = getHalfWidth() + sensorThickness / 3;
    SensorListener.createSensor(body, new WallJumpSensor(this, true), -xShift, 0,
                                sensorThickness, getHalfHeight());
    SensorListener.createSensor(body, new WallJumpSensor(this, false), xShift, 0,
                                sensorThickness, getHalfHeight());
    SensorListener.createSensor(body, new JumpSensor(this), 0, - getHalfHeight() - sensorThickness / 3,
                                getHalfWidth(), sensorThickness);
  }

  protected abstract float getMoveStrength();

  protected abstract float getJumpStrength();
}
