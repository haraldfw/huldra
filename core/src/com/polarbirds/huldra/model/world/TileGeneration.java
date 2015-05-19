package com.polarbirds.huldra.model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Harald Wilhelmsen on 18/5/2015.
 */
public class TileGeneration {

  ArrayList<SectionTiles> sectionTilesList;
  ArrayList<TileType> openTiles;

  public TileGeneration(ArrayList<Section> sections) {
    sectionTilesList = new ArrayList<>();

    openTiles = new ArrayList<>();
    openTiles.add(TileType.EMPTY);
    openTiles.add(TileType.LADDER);
    openTiles.add(TileType.PLATFORM);
    // make tileSets
  }

  public void placeSection(TileType[][] tiles, SectionBounds bounds, Random random) {
    ArrayList<SectionTiles> candidates = new ArrayList<>(sectionTilesList);

  }

  private class SectionTiles {
    TileType[][] tiles;

    public SectionTiles(TileType[][] tiles) {
      this.tiles = tiles;
    }

    boolean isCandidate(TileType[] left, TileType[] right, TileType[] bottom, TileType[] top) {
      return overlap(left, tiles[0]) &&
             overlap(right, tiles[tiles.length - 1]) &&
             overlap(bottom, getHorizontalLine(0, tiles)) &&
             overlap(top, getHorizontalLine(tiles[0].length - 1, tiles));
    }

    private boolean overlap(TileType[]outside, TileType[] inside) {
      boolean overlap = true;
      for(int i = 0; i < inside.length; i++) {
        if(openTiles.contains(outside[i]) && !openTiles.contains(inside[i])) overlap = false;
      }
      return overlap;
    }
  }

  private static boolean[] tileTypeToBoolean(TileType[] tiles) {
    boolean[] booleans = new boolean[tiles.length];
    for(int i = 0; i < tiles.length; i++) {
      boolean b = false;
      switch (tiles[i]) {
        case EMPTY:
        case LADDER:
        case PLATFORM:
          b = true;
          break;
      }
      booleans[i] = b;
    }
    return booleans;
  }

  private static TileType[] getHorizontalLine(int yCoordinate, TileType[][] tiles) {
    TileType[] line = new TileType[tiles.length];
    for(int i = 0; i < tiles.length; i++) {
      line[i] = tiles[i][yCoordinate];
    }
    return line;
  }
}
