package com.polarbirds.huldra.model.entity.character;

import com.polarbirds.huldra.model.entity.ADrawableDynamic;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.character.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.entity.stat.StatType;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.RectShape;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class AWalkingCharacter extends ADrawableDynamic {

  public final IMotiveProcessor motive;

  protected MoveState moveState = MoveState.FALLING;

  public AWalkingCharacter(Level level, float width, float height, float inverseMass,
                           IMotiveProcessor motive, Team team) {
    super(new DynamicBody(new Vector2(), new RectShape(width, height), inverseMass, level), team);
    this.motive = motive;
  }

  @Override
  public void update(float delta) {
    super.update(delta);
    motive.update();
    switch (moveState) {
      case FALLING:
        body.applyForce(motive.moveX() * StatType.MOVE_STRENGTH.calculate(this) / 4, 0);
        break;
      case IDLE:
        body.applyForce(motive.moveX() * StatType.MOVE_STRENGTH.calculate(this), 0);
        if (motive.jump()) {
          body.applyImpulse(0, StatType.JUMP_STRENGTH.calculate(this));
        }
        break;
      case HANGING:
        if (motive.moveY() < -0.2f) {
          setMoveState(MoveState.FALLING);
        }
        body.applyForce(motive.moveX() * StatType.MOVE_STRENGTH.calculate(this), 0);
        break;
      case CLIMBING:
        body.applyForce(0, motive.moveY() * StatType.MOVE_STRENGTH.calculate(this));
        break;
    }
  }

  public void setMoveState(MoveState moveState) {
    this.moveState = moveState;
  }
}
