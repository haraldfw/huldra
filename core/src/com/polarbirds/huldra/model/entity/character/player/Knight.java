package com.polarbirds.huldra.model.entity.character.player;

import com.polarbirds.huldra.model.entity.animation.AAnimation;
import com.polarbirds.huldra.model.entity.character.Team;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.ArrayList;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  private static final float sMove = 25f;
  private static final float sDmg = 1f;

  private AAnimation[] animations;

  private int activeAnimation;

  public Knight(Vector2 pos, Team team, GameScreen game) {
    super(pos, team, game);
    ArrayList<AAnimation> animations = new ArrayList<>();

    animations.add(game.spriteLoader.getAnimation("graphics/player/knight/walk.anim"));

    this.animations = animations.toArray(new AAnimation[animations.size()]);

  }

  @Override
  protected AAnimation getCurrentAnimation() {
    return animations[activeAnimation];
  }

}