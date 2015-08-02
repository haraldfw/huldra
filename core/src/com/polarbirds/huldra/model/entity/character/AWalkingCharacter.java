package com.polarbirds.huldra.model.entity.character;

import com.polarbirds.huldra.model.entity.ADrawableDynamic;
import com.polarbirds.huldra.model.entity.character.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.stat.StatType;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class AWalkingCharacter extends ADrawableDynamic {

  public IMotiveProcessor input;

  protected CharacterState characterState = CharacterState.FALLING;

  public AWalkingCharacter(Level level, float width, float height, float inverseMass, Team team) {
    super(level, new Vector2(), width, height, inverseMass, team);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
    input.update();
    switch (characterState) {
      case FALLING:
        body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this) / 4, 0);
        break;
      case IDLE:
        body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this), 0);
        if (input.jump()) {
          body.applyImpulse(0, StatType.JUMP_STRENGTH.calculate(this));
        }
        break;
      case HANGING:
        if (input.moveY() < -0.2f) {
          setCharacterState(CharacterState.FALLING);
        }
        body.applyForce(input.moveX() * StatType.MOVE_STRENGTH.calculate(this), 0);
        break;
      case CLIMBING:
        body.applyForce(0, input.moveY() * StatType.MOVE_STRENGTH.calculate(this));
        break;
    }
  }

  public void setCharacterState(CharacterState characterState) {
    this.characterState = characterState;
  }
}
