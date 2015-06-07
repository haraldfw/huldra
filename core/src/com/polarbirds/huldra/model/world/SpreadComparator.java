package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.geom.Bounds;

import java.util.Comparator;

/**
 * Created by Harald Wilhelmsen on 7/6/2015.
 */
class SpreadComparator implements Comparator<Bounds> {

  @Override
  public int compare(Bounds b1, Bounds b2) {
    return b1.x*b1.y - b2.x*b2.y;
  }
}
