package com.polarbirds.huldra.model.character.player.gear;

import com.polarbirds.huldra.model.character.player.stat.IStatContainer;
import com.polarbirds.huldra.model.character.player.stat.StatModifier;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public abstract class AGear implements IStatContainer {

  public final GearSlot gearSlot;
  private final StatModifier[] stats;

  public AGear(GearSlot gearSlot, StatModifier[] stats) {
    this.gearSlot = gearSlot;
    this.stats = stats;
  }

  @Override
  public StatModifier[] getMods() {
    return stats;
  }
}
