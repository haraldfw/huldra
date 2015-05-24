package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.IntVector2;

import java.util.ArrayList;

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
   *
   * @param v An IntVector2 containing the coordinates to check for.
   * @return True if the coordinates overlap this section.
   */
  boolean contains(IntVector2 v) {
    return !(v.x < x || v.x > x + width || v.y < y || v.y > y + height);
  }

  boolean collides(int x, int y, int width, int height) {
    return !(x > this.x + this.width ||
             x + width < this.x ||
             y + height > this.y ||
             y < y + height);
  }

  TileType[][] getTiles() {
    TileType[][] tiles =
        new TileType[width * Section.TILES_PER_SIDE][height * Section.TILES_PER_SIDE];
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[0].length; y++) {
        tiles[x][y] =
            x == 0 || y == 0 || x == tiles.length - 1 || y == tiles[0].length - 1 ? TileType.SOLID
                                                                                  : TileType.EMPTY;
      }
    }
    return tiles;
  }

  boolean collidesList(Iterable<SectionBounds> boundsList) {
    for (SectionBounds boundsFromList : boundsList) {
      if (boundsFromList.collides(x, y, width, height)) {
        return true;
      }
    }
    return false;
  }


  /**
   * Returns a boolean representing if the given rect-bounds collide with any
   *
   * @param x          X-value of bounds to check collision for
   * @param y          Y-value of bounds to check collision for
   * @param width      Width of bounds to check collision for
   * @param height     Height of bounds to check collision for
   * @param boundsList List of bounds to check collisions with
   */
}