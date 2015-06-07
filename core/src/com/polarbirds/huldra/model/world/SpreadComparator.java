package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.geom.Bounds;

import java.util.Comparator;

/**
 * A comparator for sorting bounds by their distance to [0, 0]
 * Created by Harald Wilhelmsen on 7/6/2015.
 */
class SpreadComparator implements Comparator<Bounds> {

  @Override
  public int compare(Bounds b1, Bounds b2) {
    return (b1.x + b1.width / 2f) * (b1.y + b1.height / 2f) <
           (b2.x + b2.width / 2f) * (b2.y + b2.height / 2f) ? -1 : 1;
  }
}
