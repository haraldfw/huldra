package com.polarbirds.huldra.model.character.animate;

import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.stat.StatType;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.RectShape;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class AWalkingCharacter extends ADynamicCharacter {

  public IMotiveProcessor input;

  private WalkingState walkingState = WalkingState.FALLING;

  public AWalkingCharacter(float width, float height, float inverseMass, Team team) {
    super(new DynamicBody(new Vector2(), new RectShape(width, height), inverseMass), team);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
    input.update();
    switch (walkingState) {
      case FALLING:
        body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this) / 4, 0);
        break;
      case ON_GROUND:
        body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this), 0);
        if (input.jump()) {
          body.applyImpulse(0, StatType.JUMP_STRENGTH.calculate(this));
        }
        break;
      case HANGING:
        if (input.moveY() < -0.2f) {
          setWalkingState(WalkingState.FALLING);
        }
        body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this), 0);
        break;
      case CLIMBING:
        body.applyForce(0, input.moveY() * StatType.MOVE_STRENGTH.calculate(this));
        break;
    }
  }

  public void setWalkingState(WalkingState walkingState) {
    this.walkingState = walkingState;
  }

  public enum WalkingState {
    FALLING,
    ON_GROUND,
    HANGING,
    CLIMBING;
  }
}
