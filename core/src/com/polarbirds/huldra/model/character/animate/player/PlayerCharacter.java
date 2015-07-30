package com.polarbirds.huldra.model.character.animate.player;

import com.badlogic.gdx.controllers.Controllers;
import com.polarbirds.huldra.controller.player.Keyboard;
import com.polarbirds.huldra.controller.player.XboxController;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.animate.AWalkingCharacter;
import com.polarbirds.huldra.model.character.animate.player.gear.AGear;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends AWalkingCharacter {

  private final Map<String, AGear> gear;
  private AAnimation[] animations;
  private int activeAnimation;

  public PlayerCharacter(Team team) {
    super(0.5f, 0.7f, 0.0167f, team);
    gear = new HashMap<>(10);
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
  public Map<String, AGear> getGear() {
    return null;
  }

  @Override
  protected AAnimation getCurrentAnimation() {
    return animations[activeAnimation];
  }

  @Override
  public void initGraphics(Map<String, ASprite> sprites, Map<String, AAnimation> animations) {
    List<AAnimation> animationList = new ArrayList<>();
    animationList.add(animations.get("graphics/player/knight/walk.json"));
    this.animations = animationList.toArray(new AAnimation[animationList.size()]);
  }

  @Override
  public void queueAssets(SpriteLoader spriteLoader) {
    spriteLoader.queueAsset("graphics/player/knight/walk.json");
  }
}
