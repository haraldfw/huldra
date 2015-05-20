package com.polarbirds.huldra.model.world;

import java.util.HashMap;

/**
 * Created by Harald on 16.5.15.
 */
public class Section {

  public static final int TILES_PER_SIDE = 8;

  SectionBounds bounds;

  /*
  A hashtable of boolean-arrays representing the sides of the section with
    a boolean array for each side, showing which tiles must be open along the edges
  */
  private HashMap<Side, boolean[]> reachableOpenings;

  public Section(SectionBounds bounds) {
    this.bounds = bounds;

    reachableOpenings = new HashMap<>();

    reachableOpenings.put(Side.LEFT, new boolean[TILES_PER_SIDE * bounds.height]);
    reachableOpenings.put(Side.RIGHT, new boolean[TILES_PER_SIDE * bounds.height]);
    reachableOpenings.put(Side.BOTTOM, new boolean[TILES_PER_SIDE * bounds.width]);
    reachableOpenings.put(Side.TOP, new boolean[TILES_PER_SIDE * bounds.width]);
  }

  static TileType[][] getTiles() {
    TileType[][] tiles = new TileType[][]{
        {TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID,
         TileType.SOLID, TileType.SOLID, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY,
         TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY,
         TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY,
         TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY,
         TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY,
         TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY,
         TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
        {TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID,
         TileType.SOLID, TileType.SOLID, TileType.SOLID}
    };
    return tiles;
  }

  /**
   * Changes the given coordinate of the given side to the given boolean.
   *
   * @param side           The side to set.
   * @param tileCoordinate The coordinate for the tile to change.
   * @param open           The boolean to set the tile to.
   */
  void setTileOpen(Side side, int tileCoordinate, boolean open) {
    reachableOpenings.get(side)[tileCoordinate] = open;
  }

  boolean[] getSideArray(Side side) {
    return reachableOpenings.get(side);
  }

  void setSide(Side side, boolean[] sideArray) {
    int thisSideLength = reachableOpenings.get(side).length;
    if (thisSideLength != sideArray.length) {
      System.out.println("Tried to set side '" + side + "' with an array with incorrect length!"
                         + "\nLength was '" + sideArray.length
                         + "' but should have been '" + thisSideLength + "'");
      return;
    }

    System.arraycopy(sideArray, 0, reachableOpenings.get(side), 0, sideArray.length);
  }
}
