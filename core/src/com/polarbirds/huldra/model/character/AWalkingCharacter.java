package com.polarbirds.huldra.model.character;

import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.character.player.stat.StatType;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.RectShape;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class AWalkingCharacter extends ADynamicCharacter {

    public IMotiveProcessor input;

    private boolean onGround = false;

    public AWalkingCharacter(float width, float height, float inverseMass, Team team) {
        super(new DynamicBody(new Vector2(), new RectShape(width, height), inverseMass), team);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        input.update();
        if (onGround) {
            body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this), 0);
            if (input.jump()) {
                body.applyImpulse(0, StatType.JUMP_STRENGTH.calculate(this));
            }
        } else {
            body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this) / 4, 0);
        }
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }
}
