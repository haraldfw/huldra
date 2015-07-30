package com.polarbirds.huldra.model.character.player.gear;

import com.polarbirds.huldra.model.character.player.stat.StatModifier;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public interface GearWearer {

  StatModifier[] getBaseStats();

  Map<String, AGear> getGear();
}
