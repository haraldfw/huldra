package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.polarbirds.huldra.model.entity.inanimateobject.Interactable;
import com.smokebox.lib.utils.IntVector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Harald on 16.5.15.
 */
public enum WorldType {

  FOREST {
    @Override
    public HuldraWorld getNew(float amountLargeSections, int amountOfSections, long seed, OrthographicCamera camera) {
      TileType[][] tiles = new TileType[8][8];
      for(int x = 0; x < tiles.length; x++)
        for(int y = 0; y < tiles[x].length; y++)
          tiles[x][y] = TileType.EMPTY;

      ArrayList<Interactable> interactables = new ArrayList<>();
      return new HuldraWorld(tiles, interactables);
    }
  },

  CAVES {
    @Override
    public HuldraWorld getNew(float amountLargeSections, int amountOfSections, long seed, OrthographicCamera camera) {
      System.out.println("Creating Caves with " + amountOfSections + " sections");

      Random random = new Random(seed);

      ArrayList<Section> sections = new ArrayList<>();

      { // place spawn
        Section spawn = new Section(0, 0, 1, 1);

        boolean[] openSide = new boolean[Section.SECTION_SIDE_SIZE];
        openSide[0] = false;
        openSide[openSide.length - 1] = false;

        for(int i = 1; i < openSide.length - 1; i++) {
          openSide[i] = true;
        }

        spawn.setSide(Section.Side.LEFT, openSide);
        spawn.setSide(Section.Side.TOP, openSide);
        spawn.setSide(Section.Side.RIGHT, openSide);

        sections.add(spawn);
        System.out.println("Placed spawn");
      }

      // place down section until requirement amountOfSections is met
      int sectionsPlaced = 1;
      while(sectionsPlaced < amountOfSections) {
        System.out.println("amountofsections " + sectionsPlaced + "/" + amountOfSections);

        // make arrays to store openings
        ArrayList<IntVector2> locations;
        ArrayList<Integer> openingXs = new ArrayList<>();
        ArrayList<Integer> openingYs = new ArrayList<>();
        // find openings
        for(int x = 0; x < sections.length; x++) {
          for(int y = 0; y < sections[0].length; y++) {
            if(sections[x][y] != null) {
              ArrayList<Opening> entries = sections[x][y].entries;
              ArrayList<Opening> exits = sections[x][y].exits;

              Section section = sections[x - 1][y];
              if(section == null && sections[x][y].hasOpening(OpeningType.LEFT)) { // && entries contains LEFT
                openingXs.add(x - 1);
                openingYs.add(y);
              }
              section = sections[x + 1][y];
              if(section == null && sections[x][y].hasOpening(OpeningType.RIGHT)) {
                openingXs.add(x + 1);
                openingYs.add(y);
              }
              section = sections[x][y - 1];
              if(section == null && sections[x][y].hasOpening(OpeningType.DOWN)) {
                openingXs.add(x);
                openingYs.add(y - 1);
              }
              section = sections[x][y + 1];
              if(section == null && sections[x][y].hasOpening(OpeningType.UP)) {
                openingXs.add(x);
                openingYs.add(y + 1);
              }
            }
          } // y-loop
        } // x-loop

        // add sections in all openings
        System.out.println("openings " + openingXs.size());
        while(!openingXs.isEmpty()) {
          int openingRan = random.nextInt(openingXs.size());
          int x = openingXs.get(openingRan);
          int y = openingYs.get(openingRan);

          sections[x][y] = Section.getNew(sections, x, y, random);

          openingXs.remove(openingRan);
          openingYs.remove(openingRan);
          sectionsPlaced++;
          if(sectionsPlaced >= amountOfSections) break;
        }
      }

      TileType[][] tiles = new TileType[sections.length*8][sections[0].length*8];
      for (int x = 0; x < sections.length; x++) {
        for (int y = 0; y < sections[0].length; y++) {
          Section section = sections[x][y];
          if(section != null) {
            int startx = x*8;
            int starty = y*8;
            TileType[][] standard = getTilesForSection(null);
            for (int sx = 0; sx < standard.length; sx++) {
              for (int sy = 0; sy < standard[0].length; sy++) {
                tiles[startx + sx][starty + sy] = standard[sx][sy];
              }
            }
          }
        }
      }

      ArrayList<Interactable> interactables = new ArrayList<>();
      return new HuldraWorld(tiles, interactables);
    }
  },

  TEST_STAGE {
    @Override
    public HuldraWorld getNew(float amountLargeSections, int amountOfSections, long seed, OrthographicCamera camera) {
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
      ArrayList<Interactable> interactables = new ArrayList<>();
      return new HuldraWorld(tiles, interactables);
    }
  };

  public abstract HuldraWorld getNew(float amountLargeSections, int amountOfSections, long seed, OrthographicCamera camera);

  private static boolean coordinatesTaken(IntVector2 v, ArrayList<Section> sections) {
    for(Section s : sections) {
      if(s.contains(v)) return true;
    }
    return false;
  }

  private static TileType[][] getTilesForSection(Section section) {
    TileType[][] tiles =
        new TileType
            [section.width*Section.SECTION_SIDE_SIZE]
            [section.height*Section.SECTION_SIDE_SIZE];
    for(int x = 0; x < tiles.length; x++) {
      for(int y = 0; y < tiles[0].length; y++) {

      }
    }
    return Section.getTiles();
  }

  private static Section getSectionAt(IntVector2 v, ArrayList<Section> sections) {
    for(Section s : sections) {
      if(s.contains(v)) return s;
    }
    return null;
  }
}