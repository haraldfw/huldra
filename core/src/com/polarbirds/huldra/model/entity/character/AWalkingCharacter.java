package com.polarbirds.huldra.model.entity.character;

import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.RectShape;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class AWalkingCharacter extends ADynamicCharacter {

    public IMotiveProcessor input;

    private boolean onGround = false;

    public AWalkingCharacter(Vector2 pos, float width, float height, float inverseMass,
                             Team team, GameScreen game) {
        super(new DynamicBody(pos, new RectShape(width, height), inverseMass), team, game);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        input.update();
        if (onGround && input.jump()) {
            body.applyImpulse(0, getJumpStrength());
            setOnGround(false);
        }
        body.applyForce(input.moveX() * getMoveStrength(), 0);
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    protected abstract float getMoveStrength();

    protected abstract float getJumpStrength();
}
