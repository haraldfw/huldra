package com.polarbirds.huldra.model.entity.contact;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.polarbirds.huldra.model.entity.character.ACharacter;

/**
 * Created by Harald on 28.5.15.
 */
public interface SensorListener {

  void activate();

  public static void createSensor(Body body, Object userData, float width, float height) {
    FixtureDef sensor = new FixtureDef();
    sensor.isSensor = true;
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width, height);
    sensor.shape = shape;

    body.createFixture(sensor).setUserData(userData);
  }
}
