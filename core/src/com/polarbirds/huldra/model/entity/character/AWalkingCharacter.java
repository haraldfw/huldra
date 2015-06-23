package com.polarbirds.huldra.model.entity.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.contact.SensorListener;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class AWalkingCharacter extends ACharacter {

  public AWalkingCharacter(Vector2 pos, World world, Team team) {
    super(pos, world, team);
  }

  @Override
  public void act(float delta) {
    super.act(delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
  }

  @Override
  protected ArrayList<FixtureDef> concatSensors() {
    ArrayList<FixtureDef> fixtureDefs = super.concatSensors();

    fixtureDefs.add(SensorListener.getSensor(0.05f, getHalfHeight()));
    fixtureDefs.add(SensorListener.getSensor(0.05f, getHalfHeight()));
    fixtureDefs.add(SensorListener.getSensor(getHalfWidth(), 0.05f));

    return fixtureDefs;
  }

  protected abstract float getMoveStrength();
}
