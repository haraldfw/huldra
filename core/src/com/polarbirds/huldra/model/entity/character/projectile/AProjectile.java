package com.polarbirds.huldra.model.entity.character.projectile;

import com.polarbirds.huldra.model.entity.character.ADynamicCharacter;
import com.polarbirds.huldra.model.entity.character.Team;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 29.6.15.
 */
public abstract class AProjectile extends ADynamicCharacter {

  public AProjectile(DynamicBody body, Vector2 vel, Team team, GameScreen game) {
    super(body, team, game);
    body.vel.set(vel);
  }


}
