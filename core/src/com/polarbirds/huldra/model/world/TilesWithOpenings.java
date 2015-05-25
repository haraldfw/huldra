package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to hold a name of a section and it's openings Created by Harald on 23.5.15.
 */
final class TilesWithOpenings {

  final TileType[][] tiles;
  final Map<Side, boolean[]> reachableOpenings;

  final int sectionWidth;
  final int sectionHeight;

  TilesWithOpenings(TileType[][] tiles, Map<Side, boolean[]> reachableOpenings) {
    this.tiles = tiles;
    this.reachableOpenings = reachableOpenings;

    sectionWidth = tiles.length / Section.TILES_PER_SIDE;
    sectionHeight = tiles[0].length / Section.TILES_PER_SIDE;
  }

  static List<TilesWithOpenings> loadAndGetList() {
    List<TilesWithOpenings> sections = new ArrayList<>();
    for (FileHandle file : Gdx.files.internal("sections").list()) {
      if (file.name().contains(".sec")) {
        sections.add(parseSection(file.file()));
      }
    }
    return sections;
  }

  private static TilesWithOpenings parseSection(File file) {
    try {
      FileInputStream inputStream = new FileInputStream(file);

      //Construct BufferedReader from InputStreamReader
      BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

      // get dimensions
      int width = Integer.parseInt(br.readLine());
      int height = Integer.parseInt(br.readLine());

      // instantiate array to return in object
      TileType[][] tiles = new TileType[width][height];

      // fill tiles-array
      for (int y = height - 1; y >= 0; y--) {
        TileType[] tileRow = getStringAsTiles(br.readLine());
        for (int x = 0; x < width; x++) {
          tiles[x][y] = tileRow[x];
        }
        break;
      }

      HashMap<Side, boolean[]> reachableOpenings = new HashMap<>();

      reachableOpenings.put(Side.LEFT, getStringAsBooleans(br.readLine()));
      reachableOpenings.put(Side.RIGHT, getStringAsBooleans(br.readLine()));
      reachableOpenings.put(Side.TOP, getStringAsBooleans(br.readLine()));
      reachableOpenings.put(Side.BOTTOM, getStringAsBooleans(br.readLine()));

      br.close();

      return new TilesWithOpenings(tiles, reachableOpenings);

    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static TileType[] getStringAsTiles(String string) {
    // array of the tiles to return
    TileType[] tiles = new TileType[string.length()];

    // convert string-array to tiles-array
    for (int i = 0; i < string.length(); i++) {
      switch (string.charAt(i)) {
        case 'E':
          tiles[i] = TileType.EMPTY;
          break;
        case 'L':
          tiles[i] = TileType.LADDER;
          break;
        case 'P':
          tiles[i] = TileType.PLATFORM;
          break;
        case 'T':
          tiles[i] = TileType.TOP_LADDER_PLATFORM;
          break;
        default: // case 'S'
          tiles[i] = TileType.SOLID;
          break;
      }
    }
    return tiles;
  }

  private static boolean[] getStringAsBooleans(String string) {
    // array of the tiles to return
    boolean[] tiles = new boolean[string.length()];

    // convert string-array to tiles-array
    for (int i = 0; i < string.length(); i++) {
      switch (string.charAt(i)) {
        case 'T':
          tiles[i] = true;
          break;
        default: // case 'F'
          tiles[i] = false;
          break;
      }
    }
    return tiles;
  }
}
