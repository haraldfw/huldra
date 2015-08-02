package com.polarbirds.huldra.model.entity.character.player;

import com.badlogic.gdx.controllers.Controllers;
import com.polarbirds.huldra.model.entity.character.controller.player.Keyboard;
import com.polarbirds.huldra.model.entity.character.controller.player.XboxController;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.character.AWalkingCharacter;
import com.polarbirds.huldra.model.entity.character.MoveState;
import com.polarbirds.huldra.model.entity.character.player.gear.GearHandler;
import com.polarbirds.huldra.model.entity.character.player.gear.GearWearer;
import com.polarbirds.huldra.model.entity.stat.StatModifier;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.Map;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class APlayerCharacter extends AWalkingCharacter implements GearWearer {

  private final GearHandler gearHandler;
  private final Map<MoveState, AAnimation> animations;
  private final StatModifier[] baseStats;

  public APlayerCharacter(Level level, Map<MoveState, AAnimation> animations, StatModifier[] baseStats) {
    super(level, 0.5f, 0.7f, 0.0167f, Team.PLAYER);
    this.animations = animations;
    this.baseStats = baseStats;
    gearHandler = new GearHandler();
  }

  public void init(Vector2 pos, GameScreen gameScreen) {
    this.input =
        Controllers.getControllers().size > 0 ? new XboxController(
            Controllers.getControllers().get(0)) :
        new Keyboard(gameScreen.game.staticViewCamera);
    body.pos.set(pos);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
  }

  @Override
  public StatModifier[] getBaseStats() {
    return baseStats;
  }

  @Override
  public GearHandler getGear() {
    return gearHandler;
  }

  @Override
  protected AAnimation getCurrentAnimation() {
    return animations.get(moveState);
  }

  public abstract String getCharacterName();
}
