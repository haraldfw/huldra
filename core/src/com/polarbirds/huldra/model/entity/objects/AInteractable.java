package com.polarbirds.huldra.model.entity.objects;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.model.entity.character.player.APlayerCharacter;

/**
 * An object than can be interacted with. Some by the player, and some by other entities like
 * enemies Created by Harald on 30.4.15.
 */
public abstract class AInteractable extends Image {

  public AInteractable() {

  }

  public abstract void interact(APlayerCharacter character);
}
