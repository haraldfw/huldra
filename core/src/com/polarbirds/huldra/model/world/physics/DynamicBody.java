package com.polarbirds.huldra.model.world.physics;

import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.shape.AShape;

/**
 * Created by Harald on 29.6.15.
 */
public class DynamicBody extends ABody {

  public final float inverseMass;

  public final Vector2 vel;
  private final Vector2 acc;
  private final Vector2 forceAcc;

  private final Level level;

  public DynamicBody(Vector2 pos, AShape shape, float inverseMass, Level level) {
    super(pos, shape);
    this.inverseMass = inverseMass;
    vel = new Vector2();
    acc = new Vector2();
    forceAcc = new Vector2();

    this.level = level;
  }

  public void integrate(float delta) {
    acc.mulAdd(forceAcc, inverseMass);
    clearForces();
    vel.mulAdd(acc, delta);
    pos.mulAdd(vel, delta);
  }

  public void applyForce(float x, float y) {
    forceAcc.add(x, y);
  }

  public void applyForce(Vector2 force) {
    forceAcc.add(force);
  }

  public void applyImpulse(float x, float y) {
    vel.mulAdd(x, y, inverseMass);
  }

  public void applyImpulse(Vector2 impulse) {
    vel.mulAdd(impulse, inverseMass);
  }

  private void clearForces() {
    forceAcc.setZero();
  }
}
