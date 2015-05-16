package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.IntVector2;

import java.util.HashMap;

/**
 * Created by Harald Wilhelmsen on 13/5/2015.
 */
final class SectionBounds {

  public static final int TILES_PER_SIDE = 8;
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
   * Changes the given coordinate of the given side to the given boolean.
   * @param side            The side to set.
   * @param tileCoordinate  The coordinate for the tile to change.
   * @param open            The boolean to set the tile to.
   */
  void setTileOpen(Side side, int tileCoordinate, boolean open) {
    openings.get(side)[tileCoordinate] = open;
  }

  /**
   * Returns a boolean that signifies whether the coordinates given overlap this SectionBounds.
   * @param v An IntVector2 containing the coordinates to check for.
   * @return  True if the coordinates overlap this section.
   */
  boolean contains(IntVector2 v) {
    return !(v.x < this.x || v.x > this.x + width || v.y < this.y || v.y > this.y + height);
  }

  boolean[] getSideArray(Side side) {
    return openings.get(side);
  }

  void setSide(Side side, boolean[] sideArray) {
    int thisSideLength = openings.get(side).length;
    if(thisSideLength != sideArray.length) {
      System.out.println("Tried to set side '" + side + "' with an array with incorrect length!"
                         + "\nLength was '" + sideArray
                         + "' but should have been '" + thisSideLength + "'");
      return;
    }

    System.arraycopy(sideArray, 0, openings.get(side), 0, sideArray.length);
  }

  static TileType[][] getTiles() {
    TileType[][] tiles = new TileType[][]{
        {TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID}
    };
    return tiles;
  }
}