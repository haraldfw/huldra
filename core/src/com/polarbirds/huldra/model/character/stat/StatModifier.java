package com.polarbirds.huldra.model.character.stat;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public class StatModifier {

  public final StatType statType;
  public final StatClass statClass;
  public final float value;

  public StatModifier(StatType statType, StatClass statClass, float value) {
    this.statType = statType;
    this.statClass = statClass;
    this.value = value;
  }
}
