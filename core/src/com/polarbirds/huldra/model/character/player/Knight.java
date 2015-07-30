package com.polarbirds.huldra.model.character.player;

import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.player.stat.StatClass;
import com.polarbirds.huldra.model.character.player.stat.StatModifier;
import com.polarbirds.huldra.model.character.player.stat.StatType;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  private final StatModifier[] baseStats = new StatModifier[]{
      new StatModifier(StatType.JUMP_STRENGTH, StatClass.BASE, 1),
      new StatModifier(StatType.MOVE_STRENGTH, StatClass.BASE, 25f),
      new StatModifier(StatType.DMG_PHYSICAL, StatClass.BASE, 1)
  };
  private AAnimation[] animations;

  private int activeAnimation;

  public Knight(Team team) {
    super(team);
  }

  @Override
  public void init(Vector2 pos, GameScreen gameScreen) {
    super.init(pos, gameScreen);
  }

  @Override
  protected AAnimation getCurrentAnimation() {
    return animations[activeAnimation];
  }

  @Override
  public StatModifier[] getBaseStats() {
    return baseStats;
  }

  @Override
  public void initGraphics(Map<String, ASprite> sprites, Map<String, AAnimation> animations) {
    ArrayList<AAnimation> animationList = new ArrayList<>();
    animationList.add(animations.get("graphics/player/knight/walk.json"));
    this.animations = animationList.toArray(new AAnimation[animationList.size()]);
  }

  @Override
  public void queueAssets(SpriteLoader spriteLoader) {
    spriteLoader.queueAsset("graphics/player/knight/walk.json");
  }
}