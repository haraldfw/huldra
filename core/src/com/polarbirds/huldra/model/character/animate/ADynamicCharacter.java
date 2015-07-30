package com.polarbirds.huldra.model.character.animate;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.animate.player.gear.GearWearer;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.utility.IHasGraphics;
import com.polarbirds.huldra.model.world.physics.DynamicBody;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class ADynamicCharacter implements GearWearer, IHasGraphics {

  public DynamicBody body;
  public Team team;

  public ADynamicCharacter(DynamicBody body, Team team) {
    this.body = body;
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
