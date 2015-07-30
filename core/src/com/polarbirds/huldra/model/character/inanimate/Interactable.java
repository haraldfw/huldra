package com.polarbirds.huldra.model.character.inanimate;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.model.character.animate.player.PlayerCharacter;

/**
 * An object than can be interacted with. Some by the player, and some by other entities like
 * enemies Created by Harald on 30.4.15.
 */
public abstract class Interactable extends Image {

  public Interactable() {

  }

  public abstract void interact(PlayerCharacter character);
}
