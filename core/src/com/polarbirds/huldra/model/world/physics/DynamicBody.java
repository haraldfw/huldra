package com.polarbirds.huldra.model.world.physics;

import com.polarbirds.huldra.model.world.physics.shape.Shape;

/**
 * Created by Harald on 29.6.15.
 */
public class DynamicBody extends ABody {

    public Vector2 vel;
    public float inverseMass;
    private Vector2 acc;
    private Vector2 forceAcc;

    public DynamicBody(Vector2 pos, Shape shape, float inverseMass) {
        super(pos, shape);
        this.inverseMass = inverseMass;
        vel = new Vector2();
        acc = new Vector2();
        forceAcc = new Vector2();
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
