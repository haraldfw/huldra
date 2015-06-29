package com.polarbirds.huldra.model.entity.character;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class ADynamicCharacter extends Image {

  public DynamicBody body;
  protected Team team;

  public ADynamicCharacter(DynamicBody body, GameScreen game, Team team) {
    this.team = team;
    this.body = body;
  }

  @Override
  public void act(float delta) {

  }

  @Override
  public void draw(Batch batch, float parentAlpha) {

  }
}
