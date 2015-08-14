package com.polarbirds.huldra.model.entity.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.entity.Team;
import com.polarbirds.huldra.model.entity.character.player.APlayerCharacter;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * An object than can be interacted with. Some by the player, and some by other entities like
 * enemies Created by Harald on 30.4.15.
 */
public abstract class AInteractable extends AObject {

  public AInteractable(Level level, Vector2 pos, float width, float height, float inverseMass,
                       Team team) {
    super(level, pos, width, height, inverseMass, team);
  }

  @Override
  public void draw(Batch batch) {
    super.draw(batch);
  }

  @Override
  public void update(float delta) {
    super.update(delta);
  }

  public abstract void interact(APlayerCharacter character);
}
