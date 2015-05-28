package com.polarbirds.huldra.model.entity.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  private static final float halfWidth = 0.25f;
  private static final float halfHeight = 0.35f;
  private static final float sMove = 25f;
  private static final float sDmg = 1f;

  public Knight(GameScreen screen, Vector2 pos, IMotiveProcessor input) {
    super(createBody(pos, screen.world.box2dWorld), halfWidth, halfHeight,
          sMove, sDmg, screen, input);
  }

  private static Body createBody(Vector2 pos, World world) {

    PolygonShape shape = new PolygonShape();
    shape.setAsBox(halfWidth, halfHeight);

    FixtureDef fixtureDef = new FixtureDef();
    fixtureDef.shape = shape;
    fixtureDef.restitution = 0.05f;
    fixtureDef.density = 1f;
    fixtureDef.friction = 1;

    BodyDef bodyDef = new BodyDef();
    bodyDef.type = BodyDef.BodyType.DynamicBody;
    bodyDef.allowSleep = false;
    bodyDef.position.set(new Vector2(pos).add(-halfWidth, -halfHeight));
    bodyDef.fixedRotation = true;
    bodyDef.linearDamping = 0.5f;

    Body body = world.createBody(bodyDef);
    body.createFixture(fixtureDef);



    return body;
  }
}
