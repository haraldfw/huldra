package com.polarbirds.huldra.model.entity.contact;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public class HuldraContactListener implements ContactListener {

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {

  }

  @Override
  public void beginContact(Contact contact) {

  }

  @Override
  public void endContact(Contact contact) {
    Fixture a = contact.getFixtureA();
    Fixture b = contact.getFixtureB();

    if(a.isSensor()) {
      reactSensor(a.getUserData(), b);
    } else if(b.isSensor()) {
      reactSensor(b.getUserData(), a);
    }
  }

  private void reactSensor(Object sensor, Fixture fixture) {
    if(sensor instanceof SensorListener) {
      ((SensorListener) sensor).activate();
    }
  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {

  }
}
