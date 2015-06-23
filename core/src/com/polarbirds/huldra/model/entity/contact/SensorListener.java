package com.polarbirds.huldra.model.entity.contact;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

/**
 * Created by Harald on 28.5.15.
 */
public interface SensorListener {

  void activate();

  public static FixtureDef getSensor(float width, float height) {
    FixtureDef sensor = new FixtureDef();
    sensor.isSensor = true;
    PolygonShape shape = new PolygonShape();
    shape.setAsBox(width, height);
    sensor.shape = shape;
    return sensor;
  }
}
