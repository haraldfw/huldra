package com.polarbirds.huldra.model.entity.projectile;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.entity.ADrawableDynamic;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.stat.StatModifier;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.model.Tile;
import com.polarbirds.huldra.model.world.physics.DynamicBody;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 23/8/2015.
 */
public abstract class AProjectile extends ADrawableDynamic {

  protected boolean alive = true;

  public AProjectile(DynamicBody body, Team team) {
    super(body, team);
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
  public boolean isAlive() {
    return alive;
  }
}
