package com.polarbirds.huldra.model.entity.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.model.entity.Team;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class ACharacter extends Image {

  protected Team team;
  protected Body body;

  public ACharacter(Vector2 pos, World world, Team team) {
    this.team = team;
    this.body = createBody(pos, world);
  }

  @Override
  public void act(float delta) {

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {

  }

  private Body createBody(Vector2 pos, World world) {
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
    bodyDef.position.set(new Vector2(pos).add(-getHalfWidth(), -getHalfHeight()));
    bodyDef.fixedRotation = true;
    bodyDef.linearDamping = 0.5f;

    Body body = world.createBody(bodyDef);
    body.createFixture(fixtureDef);

    for (FixtureDef f : getSensors()) {
      body.createFixture(f);
    }

    return body;
  }

  protected abstract ArrayList<FixtureDef> getSensors();

  protected ArrayList<FixtureDef> concatSensors() {
    return new ArrayList<>();
  }

  protected abstract float getHalfWidth();

  protected abstract float getHalfHeight();
}
