package com.polarbirds.huldra.model.entity.character.skill;

import com.polarbirds.huldra.model.world.HuldraWorld;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.CircleShape;

/**
 * Created by Harald on 29.6.15.
 */
public class Skills {

  HuldraWorld world;

  public Skills(HuldraWorld world) {
    this.world = world;
  }

  public void slash(Vector2 loc, Vector2 vel, float inverseMass, float radius) {
    world.addDynamicBody(new DynamicBody(loc, new CircleShape(radius), inverseMass));
  }
}
