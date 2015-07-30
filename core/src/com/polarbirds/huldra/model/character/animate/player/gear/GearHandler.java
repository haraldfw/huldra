package com.polarbirds.huldra.model.character.animate.player.gear;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 30/7/2015.
 */
public class GearHandler {

  private Map<GearSlot, AGear> gearMap;

  public GearHandler() {
    gearMap = new HashMap<>(8);
    gearMap.put(GearSlot.HEAD, null);
    gearMap.put(GearSlot.NECKLACE, null);
    gearMap.put(GearSlot.RING, null);
    gearMap.put(GearSlot.TORSO, null);
    gearMap.put(GearSlot.BRACERS, null);
    gearMap.put(GearSlot.BELT, null);
    gearMap.put(GearSlot.PANTS, null);
    gearMap.put(GearSlot.BOOTS, null);
  }

  public AGear equip(AGear gear) {
    if (gearMap.get(gear.gearSlot) != null) {
      gearMap.put(gear.gearSlot, gear);
      return null;
    } else {
      AGear temp = gearMap.get(gear.gearSlot);
      gearMap.put(gear.gearSlot, gear);
      return temp;
    }
  }
}
