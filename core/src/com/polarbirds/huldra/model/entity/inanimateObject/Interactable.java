package com.polarbirds.huldra.model.entity.inanimateObject;

import com.polarbirds.huldra.model.entity.BasicEntity;

/**
 * An object than can be interacted with. Some by the player, and some
 * Created by Harald on 30.4.15.
 */
public interface Interactable {

  void interact(BasicEntity entity);
}
