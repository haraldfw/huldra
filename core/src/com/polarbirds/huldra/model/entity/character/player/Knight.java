package com.polarbirds.huldra.model.entity.character.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.controller.IMotiveProcessor;
import com.polarbirds.huldra.model.entity.Team;

import java.util.ArrayList;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  private static final float sMove = 25f;
  private static final float sDmg = 1f;

  public Knight(Vector2 pos, World world, Team team, IMotiveProcessor input) {
    super(pos, world, team, input);
  }

  @Override
  protected float getHalfWidth() {
    return 0.25f;
  }

  @Override
  protected float getHalfHeight() {
    return 0.35f;
  }

  @Override
  protected ArrayList<FixtureDef> getSensors() {
    return concatSensors();
  }
}
