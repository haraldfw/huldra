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
  public void beginContact(Contact contact) {
    Fixture a = contact.getFixtureA();
    Fixture b = contact.getFixtureB();

    if (a.isSensor()) {
      ((SensorListener) a.getUserData()).activate(b.getUserData());
    } else if (b.isSensor()) {
      ((SensorListener) b.getUserData()).activate(a.getUserData());
    }
  }

  @Override
  public void endContact(Contact contact) {
    Fixture a = contact.getFixtureA();
    Fixture b = contact.getFixtureB();

    if (a.isSensor()) {
      ((SensorListener) a.getUserData()).deactivate();
    } else if (b.isSensor()) {
      ((SensorListener) b.getUserData()).deactivate();
    }
  }

  @Override
  public void preSolve(Contact contact, Manifold oldManifold) {
  }

  @Override
  public void postSolve(Contact contact, ContactImpulse impulse) {
  }
}
