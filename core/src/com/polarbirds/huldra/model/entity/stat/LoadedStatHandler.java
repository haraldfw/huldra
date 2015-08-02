package com.polarbirds.huldra.model.entity.stat;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 31/7/2015.
 */
public class LoadedStatHandler {

  private final Map<String, StatModifier[]> statMap;

  public LoadedStatHandler(
      Map<String, StatModifier[]> statMap) {
    this.statMap = statMap;
  }

  public StatModifier[] getCopy(String path) {
    StatModifier[] toCopy = statMap.get(path);
    StatModifier[] copied = new StatModifier[toCopy.length];
    System.arraycopy(toCopy, 0, copied, 0, toCopy.length);
    return copied;
  }

  public StatModifier[] getInstance(String path) {
    return statMap.get(path);
  }
}
