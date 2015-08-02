package com.polarbirds.huldra.model.character.animate.player;

import com.badlogic.gdx.controllers.Controllers;
import com.polarbirds.huldra.controller.player.Keyboard;
import com.polarbirds.huldra.controller.player.XboxController;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.animate.AWalkingCharacter;
import com.polarbirds.huldra.model.character.animate.CharacterState;
import com.polarbirds.huldra.model.character.animate.player.gear.GearHandler;
import com.polarbirds.huldra.model.character.animate.player.gear.GearWearer;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.Map;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class APlayerCharacter extends AWalkingCharacter implements GearWearer {

  private final GearHandler gearHandler;
  private final Map<CharacterState, AAnimation> animations;
  private final StatModifier[] baseStats;

  public APlayerCharacter(Map<CharacterState, AAnimation> animations, StatModifier[] baseStats,
                          Team team) {
    super(0.5f, 0.7f, 0.0167f, team);
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
    return animations.get(characterState);
  }

  public abstract String getCharacterName();
}
