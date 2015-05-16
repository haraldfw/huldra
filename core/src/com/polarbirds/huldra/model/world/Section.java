package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.IntVector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Harald Wilhelmsen on 13/5/2015.
 */
final class Section {

  public static final int SECTION_SIDE_SIZE = 8;

  int x;
  int y;
  int width;
  int height;

  /*
  A hashtable of boolean-arrays representing the sides of the section with
    a boolean array for each side, showing which tiles must be open along the edges
  */
  private HashMap<Side, boolean[]> openings;

  Section(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    openings = new HashMap<>();

    openings.put(Side.LEFT, new boolean[SECTION_SIDE_SIZE*width]);
    openings.put(Side.RIGHT, new boolean[SECTION_SIDE_SIZE*width]);
    openings.put(Side.BOTTOM, new boolean[SECTION_SIDE_SIZE*height]);
    openings.put(Side.TOP, new boolean[SECTION_SIDE_SIZE*height]);
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
   * Returns a boolean that signifies whether the coordinates given overlap this Section.
   * @param v An IntVector2 containing the coordinates to check for.
   * @return  True if the coordinates overlap this section.
   */
  boolean contains(IntVector2 v) {
    return !(v.x < this.x || v.x > this.x + width || v.y < this.y || v.y > this.y + height);
  }

  boolean[] getSideArray(Side side) {
    return openings.get(side);
  }

  public void setSide(Side side, boolean[] sideArray) {
    int thisSideLength = openings.get(side).length;
    if(thisSideLength != sideArray.length) {
      System.out.println("Tried to set side '" + side + "' with an array with incorrect length!"
                         + "\nLength was '" + sideArray
                         + "' but should have been '" + thisSideLength + "'");
      return;
    }

    openings.put(side, sideArray);
  }

  public static TileType[][] getTiles() {
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

  enum Side {
    LEFT, RIGHT, BOTTOM, TOP
  }
}