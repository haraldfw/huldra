package com.polarbirds.huldra.model.world;

/**
 * Created by Harald Wilhelmsen on 13/5/2015.
 */
class OpeningArray {

  protected Opening[] openings;

  OpeningArray(Opening[] openings) {
    this.openings = openings;
  }

  boolean contains(Opening opening) {
    for (Opening o : openings) {
      if (o == opening)
        return true;
    }
    return false;
  }
}
