package com.polarbirds.huldra.model.world;

import java.util.HashMap;

/**
 * Created by Harald on 16.5.15.
 */
public class Section {

  SectionBounds bounds;

  /*
  A hashtable of boolean-arrays representing the sides of the section with
    a boolean array for each side, showing which tiles must be open along the edges
  */
  private HashMap<Side, boolean[]> openings;

  public Section(SectionBounds bounds) {
    this.bounds = bounds;

    openings = new HashMap<>();

    openings.put(Side.LEFT, new boolean[TILES_PER_SIDE* bounds.width]);
    openings.put(Side.RIGHT, new boolean[TILES_PER_SIDE * bounds.width]);
    openings.put(Side.BOTTOM, new boolean[TILES_PER_SIDE * bounds.height]);
    openings.put(Side.TOP, new boolean[TILES_PER_SIDE * bounds.height]);
  }
}
