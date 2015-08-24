package com.polarbirds.huldra.model.entity.objects;

import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.character.player.APlayerCharacter;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 2/8/2015.
 */
public abstract class AItem extends AInteractable {

  public AItem(Level level, Vector2 pos, float width, float height, float inverseMass, Team team) {
    super(level, pos, width, height, inverseMass, team);
  }

  @Override
  public void interact(APlayerCharacter character) {

  }
}
