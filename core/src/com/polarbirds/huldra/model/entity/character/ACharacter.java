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
    body = createBody(pos, world);
  }

  protected abstract Body createBody(Vector2 pos, World world);

  @Override
  public void act(float delta) {

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {

  }

  protected abstract float getHalfWidth();

  protected abstract float getHalfHeight();
}
