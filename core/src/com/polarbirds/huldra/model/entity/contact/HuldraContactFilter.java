package com.polarbirds.huldra.model.entity.contact;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;

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
    Object userA = fixtureA.getUserData();
    Object userB = fixtureB.getUserData();

    if(userA instanceof String && userA.equals("platform")) {
      return platformCollision(fixtureB, fixtureA);
    } else if(userB instanceof String && userB.equals("platform")) {
      return platformCollision(fixtureA, fixtureB);
    }


    Class<?> a = userA.getClass();
    Class<?> b = userB.getClass();

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

  private boolean platformCollision(Fixture character, Fixture platform) {
    if(character.getBody().getLinearVelocity().y >= 0) {
      return false;
    }

    float lowestY = Float.MAX_VALUE;

    PolygonShape shape = (PolygonShape) character.getShape();
    Vector2 tmp = new Vector2();
    for(int i = 0; i < shape.getVertexCount(); i++) {
      shape.getVertex(i, tmp);
      if(tmp.y < lowestY) lowestY = tmp.y;
    }
    ((EdgeShape) platform.getShape()).getVertex0(tmp);
    return lowestY > tmp.y;
  }

  private void correctOrder() {
    if (order[0].getSimpleName().compareTo(order[1].getSimpleName()) > 0) {
      Class<?> t = order[0];
      order[0] = order[1];
      order[1] = t;
    }
  }
}
