package com.polarbirds.huldra.model.character.animate;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.stat.IHasBaseStats;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.RectShape;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class ADrawableDynamic implements IHasBaseStats {

  public DynamicBody body;
  public Team team;

  public ADrawableDynamic(Vector2 pos, float width, float height, float inverseMass, Team team) {
    this.body = new DynamicBody(pos, new RectShape(width, height), inverseMass);
    this.team = team;
  }

  public void update(float delta) {
    getCurrentAnimation().update(this, delta);
  }

  public void draw(Batch batch, float parentAlpha) {
    getCurrentAnimation().draw(this, batch, body.pos);

  }

  protected abstract AAnimation getCurrentAnimation();
}
