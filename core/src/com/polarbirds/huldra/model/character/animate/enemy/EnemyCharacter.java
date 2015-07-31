package com.polarbirds.huldra.model.character.animate.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.animate.AWalkingCharacter;
import com.polarbirds.huldra.model.character.animate.player.gear.GearHandler;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.utility.SpriteLoader;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 30/7/2015.
 */
public class EnemyCharacter extends AWalkingCharacter {

  public EnemyCharacter(float width, float height, float inverseMass, Team team) {
    super(width, height, inverseMass, team);
  }

  @Override
  public void update(float delta) {
    super.update(delta);

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    super.draw(batch, parentAlpha);
  }

  @Override
  protected AAnimation getCurrentAnimation() {
    return null;
  }

  @Override
  public void initGraphics(Map<String, ASprite> sprites, Map<String, AAnimation> animations) {

  }

  @Override
  public void queueAssets(SpriteLoader spriteLoader) {

  }

  @Override
  public GearHandler getGear() {
    return null;
  }

  @Override
  public StatModifier[] getBaseStats() {
    return new StatModifier[0];
  }
}
