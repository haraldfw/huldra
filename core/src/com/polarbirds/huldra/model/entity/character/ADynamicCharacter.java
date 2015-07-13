package com.polarbirds.huldra.model.entity.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.model.entity.animation.AAnimation;
import com.polarbirds.huldra.model.utility.Sprite;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class ADynamicCharacter extends Image {

  public DynamicBody body;
  public Team team;

  public ADynamicCharacter(DynamicBody body, Team team, GameScreen game) {
    this.body = body;
    this.team = team;
  }

  @Override
  public void act(float delta) {
    getCurrentAnimation().update(this, delta);
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    getCurrentAnimation().draw(this, batch, body.pos);
  }

  protected abstract AAnimation getCurrentAnimation();
}
