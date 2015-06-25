package com.polarbirds.huldra.model.entity.contact;

import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald on 25.6.15.
 */
public class HuldraContactFilter implements ContactFilter {

  private final Map<Class<?>, ArrayList<Class<?>>> map;
  private Class<?>[] order = new Class<?>[2];

  public HuldraContactFilter() {
    map = new HashMap<>();
    ArrayList temp = new ArrayList();
    // JumpSensorListener
  }

  @Override
  public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
    if(fixtureA.getUserData() == null || fixtureB.getUserData() == null) return true;
    Class<?> a = fixtureA.getUserData().getClass();
    Class<?> b = fixtureB.getUserData().getClass();

    order[0] = a;
    order[1] = b;
    correctOrder();

    return !map.containsKey(a) || map.get(a).contains(b);
    /* Translates to
    if(map.containsKey(a)) {
      return map.get(a).contains(b);
    } else {
      return true;
    }
     */
  }

  private void correctOrder() {
    if (order[0].getSimpleName().compareTo(order[1].getSimpleName()) > 0) {
      Class<?> t = order[0];
      order[0] = order[1];
      order[1] = t;
    }
  }
}
