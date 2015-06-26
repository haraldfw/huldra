package com.polarbirds.huldra.model.entity.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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

  private boolean onGround = false;

  public AWalkingCharacter(Vector2 pos, World world, Team team) {
    super(pos, world, team);
    applySensors();
  }

  @Override
  protected Body createBody(Vector2 pos, World world) {
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(getHalfWidth(), getHalfHeight());

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.restitution = 0.05f;
    fixtureDef.density = 1f;
    fixtureDef.friction = 1;

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.allowSleep = false;
    bodyDef.position.set(new Vector2(pos).add(getHalfWidth(), getHalfHeight()));
    bodyDef.fixedRotation = true;
    bodyDef.linearDamping = 0.5f;

    Body body = world.createBody(bodyDef);
    body.createFixture(fixtureDef);

    return body;
  }

  @Override
  public void act(float delta) {
    super.act(delta);
    input.update();
    if (onGround && input.jump()) {
      body.applyLinearImpulse(0, getJumpStrength(), getHalfWidth(), getHalfHeight(), true);
      setOnGround(false);
    }
    body.applyForce(input.moveX() * getMoveStrength(), 0, getHalfWidth(), getHalfHeight(), true);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
  }

  public void setOnGround(boolean onGround) {
    this.onGround = onGround;
  }

  public boolean isOnGround() {
    return onGround;
  }

  private void applySensors() {
    float sensorThickness = 0.05f;
    float thick3 = sensorThickness / 3;
    float xShift = getHalfWidth() + thick3;
    SensorListener.createSensor(body, new WallJumpSensor(this, true), -xShift, thick3,
                                sensorThickness, getHalfHeight() - thick3);
    SensorListener.createSensor(body, new WallJumpSensor(this, false), xShift, thick3,
                                sensorThickness, getHalfHeight() - thick3);
    SensorListener.createSensor(body, new JumpSensor(this), 0, - getHalfHeight() - thick3,
                                getHalfWidth(), sensorThickness);
  }

  protected abstract float getMoveStrength();

  protected abstract float getJumpStrength();
}
