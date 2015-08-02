package com.polarbirds.huldra.model.entity.character.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.ADrawableDynamic;
import com.polarbirds.huldra.model.entity.stat.IHasBaseStats;
import com.polarbirds.huldra.model.entity.stat.StatModifier;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 30/7/2015.
 */
public abstract class AEnemyCharacter extends ADrawableDynamic implements IHasBaseStats {

  private StatModifier[] baseStats;

  public AEnemyCharacter(Level level, Vector2 pos, float width, float height, float inverseMass, Team team) {
    super(level, pos, width, height, inverseMass, team);
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
  public StatModifier[] getBaseStats() {
    return new StatModifier[0];
  }
}
