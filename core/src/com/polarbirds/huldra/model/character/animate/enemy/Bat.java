package com.polarbirds.huldra.model.character.animate.enemy;

import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 31/7/2015.
 */
public class Bat extends AEnemyCharacter {

  public Bat(Vector2 pos, StatModifier[] stats, Team team) {
    super(pos, 0.4f, 0.4f, 0.5f, team);
  }
}
