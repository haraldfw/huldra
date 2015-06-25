package com.polarbirds.huldra.model.entity.contact;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.polarbirds.huldra.model.entity.character.ACharacter;

/**
 * Created by Harald on 28.5.15.
 */
public interface SensorListener {

  void activate();

  public static void createSensor(Body body, Object userData, float xShift, float yShift, float width, float height) {
    FixtureDef sensor = new FixtureDef();
    sensor.isSensor = true;
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width, height, new Vector2(xShift, yShift), 0);
    sensor.shape = shape;

    body.createFixture(sensor).setUserData(userData);
  }
}
