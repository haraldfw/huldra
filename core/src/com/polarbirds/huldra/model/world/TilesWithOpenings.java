package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.smokebox.lib.utils.geom.Bounds;

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

  final int width;
  final int height;

  TilesWithOpenings(TileType[][] tiles, Map<Side, boolean[]> reachableOpenings) {
    this.tiles = tiles;
    this.reachableOpenings = reachableOpenings;

    width = tiles.length / Section.TILES_PER_SIDE;
    height = tiles[0].length / Section.TILES_PER_SIDE;
  }

  public boolean matches(Bounds bs, boolean[][] openings) {
    return bs.width == width && bs.height == height &&
           overlaps(getHorLine(bs.x * Section.TILES_PER_SIDE,
                               bs.y * Section.TILES_PER_SIDE,
                               Section.TILES_PER_SIDE * bs.width, openings),
                    reachableOpenings.get(Side.BOTTOM)) &&
           overlaps(getHorLine(bs.x * Section.TILES_PER_SIDE,
                               (bs.y + bs.height) * Section.TILES_PER_SIDE - 1,
                               Section.TILES_PER_SIDE * bs.width, openings),
                    reachableOpenings.get(Side.TOP)) &&
           overlaps(getHorLine(bs.x * Section.TILES_PER_SIDE,
                               bs.y * Section.TILES_PER_SIDE,
                               Section.TILES_PER_SIDE * bs.height, openings),
                    reachableOpenings.get(Side.LEFT)) &&
           overlaps(getHorLine((bs.x + bs.width) * Section.TILES_PER_SIDE - 1,
                               bs.y * Section.TILES_PER_SIDE,
                               Section.TILES_PER_SIDE * bs.height, openings),
                    reachableOpenings.get(Side.RIGHT));
  }

  private boolean overlaps(boolean[] required, boolean[] booleans) {
    System.out.print("req: ");
    for (boolean b : booleans) {
      System.out.print("[" + (b ? " " : "X") + "]");
    }
    System.out.print("\nbol: ");
    for (boolean b : required) {
      System.out.print("[" + (b ? " " : "X") + "]");
    }
    List<List<Integer>> groups = getGroups(booleans);
    for (List<Integer> group : groups) {
      boolean covered = false;
      for (Integer i : group) {
        if (required[i]) {
          covered = true;
        }
      }
      if (!covered) {
        System.out.println("\nNO MATCH");
        return false;
      }
    }
    System.out.println("MATCHES");
    return true;
  }

  private List<List<Integer>> getGroups(boolean[] booleans) {
    ArrayList<List<Integer>> groups = new ArrayList<>();
    ArrayList<Integer> group = new ArrayList<>();
    for (int i = 0; i < booleans.length; i++) {
      if (booleans[i]) {
        group.add(i);
      } else {
        groups.add(group);
        group = new ArrayList<>();
      }
    }
    return groups;
  }

  private boolean[] getHorLine(int sx, int sy, int length, boolean[][] openings) {
    boolean[] booleans = new boolean[length];
    for (int x = sx; x < booleans.length; x++) {
      booleans[0] = openings[x][sy];
    }
    return booleans;
  }

  private boolean[] getVerLine(int sx, int sy, int length, boolean[][] openings) {
    boolean[] booleans = new boolean[length];
    for (int y = sy; y < booleans.length; y++) {
      booleans[0] = openings[sx][y];
    }
    return booleans;
  }

  static List<TilesWithOpenings> loadAndGetList() {
    List<TilesWithOpenings> sections = new ArrayList<>();
    FileHandle dir = Gdx.files.internal("sections");
    System.out.println(dir);
    for (FileHandle file : dir.list()) {
      System.out.print(new FileHandle(file.toString()).file().getAbsolutePath());
      if (file.name().contains(".sec")) {
        sections.add(parseSection(file.file().getAbsoluteFile()));
      }
    }
    System.out.println("Loaded " + sections.size() + " TilesWithOpenings-files!");
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
