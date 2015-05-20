package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.IntVector2;

import java.util.HashMap;

/**
 * Created by Harald Wilhelmsen on 13/5/2015.
 */
final class SectionBounds {

  public static final int MAX_HEIGHT = 3;
  public static final int MAX_WIDTH = 3;

  int x;
  int y;
  int width;
  int height;

  SectionBounds(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  /**
   * Returns a boolean that signifies whether the coordinates given overlap this SectionBounds.
   * @param v An IntVector2 containing the coordinates to check for.
   * @return  True if the coordinates overlap this section.
   */
  boolean contains(IntVector2 v) {
    return !(v.x < this.x || v.x > this.x + width || v.y < this.y || v.y > this.y + height);
  }

  boolean collides(int x, int y, int width, int height) {
    return !(this.x  + this.width < x || this.y + this.height < y || this.x > x + width ||this.y > y + height );
  }
}