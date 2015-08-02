package com.polarbirds.huldra.model.entity.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.entity.ADrawableDynamic;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.stat.StatModifier;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.RectShape;

/**
 * Created by Harald Wilhelmsen on 2/8/2015.
 */
public abstract class AObject extends ADrawableDynamic {

  public AObject(Level level, Vector2 pos, float width, float height, float inverseMass,
                 Team team) {
    super(new DynamicBody(pos, new RectShape(width, height), inverseMass, level), team);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
  }

  @Override
  public void draw(Batch batch) {
    super.draw(batch);
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
