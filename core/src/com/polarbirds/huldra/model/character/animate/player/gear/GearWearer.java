package com.polarbirds.huldra.model.character.animate.player.gear;

import com.polarbirds.huldra.model.character.stat.StatModifier;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public interface GearWearer {

  StatModifier[] getBaseStats();

  GearHandler getGear();
}
