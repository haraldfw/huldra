package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.polarbirds.huldra.model.entity.inanimateobject.Interactable;
import com.smokebox.lib.utils.IntVector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Harald on 16.5.15.
 */
public enum WorldType {

  FOREST {
    @Override
    public HuldraWorld getNew(double amountLargeSections, int amountOfSections, long seed,
                              OrthographicCamera camera) {
      return TEST_STAGE.getNew(amountLargeSections, amountOfSections, seed, camera);
    }
  },

  CAVES {
    @Override
    public HuldraWorld getNew(double sizeGaussianScale, int amountOfSections, long seed,
                              OrthographicCamera camera) {
      System.out.println("Creating Caves with " + amountOfSections + " sections");

      Random random = new Random(seed);

      ArrayList<SectionBounds> sectionBoundsList = new ArrayList<>();

      { // place spawn
        SectionBounds spawn = new SectionBounds(0, 0, 1, 1);

        // Create a side to replace the sides of the spawn.

        sectionBoundsList.add(spawn);
        System.out.println("Placed spawn");
      }

      // place down section until requirement amountOfSections is met
      int sectionsPlaced = 1;
      // loop until all sectionBoundsList are placed or the loop uses too many iterations
      for (int iterations = 0; iterations < 10000; iterations++) {
        System.out.println("amountofsections " + sectionsPlaced + "/" + amountOfSections);

        // find dimensions for a new sectionBounds
        int height = 1 + (int) (random.nextGaussian() * sizeGaussianScale
                                * SectionBounds.MAX_HEIGHT);
        int width = 1 + (int) (random.nextGaussian() * sizeGaussianScale
                               * SectionBounds.MAX_WIDTH);

        for (int iterations2 = 0; iterations2 < 10000; iterations2++) {
          // choose a random sectionBounds to expand from
          SectionBounds
              sectionBounds =
              sectionBoundsList.get(random.nextInt(sectionBoundsList.size()));

          // get this location's openLocations
          ArrayList<IntVector2> openLocations = getOpenLocationsAroundSection(sectionBounds,
                                                                              sectionBoundsList);
          // if no open locations, go to next iteration. This location is no good.
          if (openLocations.size() <= 0) {
            continue;
          }

          if (sectionsPlaced >= amountOfSections) {
            break;
          }

        }
        /*
        // make arrays to store openings
        ArrayList<IntVector2> locations;
        // find openings
        for(int x = 0; x < sectionBoundsList.length; x++) {
          for(int y = 0; y < sectionBoundsList[0].length; y++) {
            if(sectionBoundsList[x][y] != null) {
              ArrayList<Opening> entries = sectionBoundsList[x][y].entries;
              ArrayList<Opening> exits = sectionBoundsList[x][y].exits;

              SectionBounds sectionBounds = sectionBoundsList[x - 1][y];
              if(sectionBounds == null && sectionBoundsList[x][y].hasOpening(OpeningType.LEFT)) { // && entries contains LEFT
                openingXs.add(x - 1);
                openingYs.add(y);
              }
              sectionBounds = sectionBoundsList[x + 1][y];
              if(sectionBounds == null && sectionBoundsList[x][y].hasOpening(OpeningType.RIGHT)) {
                openingXs.add(x + 1);
                openingYs.add(y);
              }
              sectionBounds = sectionBoundsList[x][y - 1];
              if(sectionBounds == null && sectionBoundsList[x][y].hasOpening(OpeningType.DOWN)) {
                openingXs.add(x);
                openingYs.add(y - 1);
              }
              sectionBounds = sectionBoundsList[x][y + 1];
              if(sectionBounds == null && sectionBoundsList[x][y].hasOpening(OpeningType.UP)) {
                openingXs.add(x);
                openingYs.add(y + 1);
              }
            }
          } // y-loop
        } // x-loop

        // add sectionBoundsList in all openings
        System.out.println("openings " + openingXs.size());
        while(!openingXs.isEmpty()) {
          int openingRan = random.nextInt(openingXs.size());
          int x = openingXs.get(openingRan);
          int y = openingYs.get(openingRan);

          sectionBoundsList[x][y] = SectionBounds.getNew(sectionBoundsList, x, y, random);

          openingXs.remove(openingRan);
          openingYs.remove(openingRan);
          sectionsPlaced++;
          if(sectionsPlaced >= amountOfSections) break;
        }*/
      }

      return new HuldraWorld(this, sectionBoundsList);
    }
  },

  TEST_STAGE {
    @Override
    public HuldraWorld getNew(double amountLargeSections, int amountOfSections, long seed,
                              OrthographicCamera camera) {
      ArrayList<SectionBounds> sectionBoundsList = new ArrayList<>();
      sectionBoundsList.add(new SectionBounds(0, 0, 1, 1));
      ArrayList<Interactable> interactables = new ArrayList<>();
      return new HuldraWorld(this, sectionBoundsList);
    }
  };

  private static SectionBounds getSectionAt(IntVector2 v, Iterable<SectionBounds> sections) {
    for (SectionBounds s : sections) {
      if (s.contains(v)) {
        return s;
      }
    }
    return null;
  }

  private static ArrayList<IntVector2> getOpenLocationsAroundSection(
      SectionBounds sectionBounds, Iterable<SectionBounds> sectionBoundsList) {
    ArrayList<IntVector2> locations = new ArrayList<>();
    for (int x = 0; x < sectionBounds.width; x++) {
      IntVector2 vector2 = new IntVector2(x, sectionBounds.y - 1);
      if (getSectionAt(vector2, sectionBoundsList) == null) {
        locations.add(vector2);
      }

      vector2 = new IntVector2(x, sectionBounds.y + sectionBounds.height + 1);
      if (getSectionAt(vector2, sectionBoundsList) == null) {
        locations.add(vector2);
      }
    }

    for (int y = 0; y < sectionBounds.height; y++) {
      IntVector2 vector2 = new IntVector2(sectionBounds.x - 1, y);
      if (getSectionAt(vector2, sectionBoundsList) == null) {
        locations.add(vector2);
      }

      vector2 = new IntVector2(sectionBounds.x + sectionBounds.width + 1, y);
      if (getSectionAt(vector2, sectionBoundsList) == null) {
        locations.add(vector2);
      }
    }
    return locations;
  }

  /**
   * Returns the location of where bounds2 can be placed with no intersections. Note: Can safely
   * return (0, 0) because this place will always be filled with spawn-section.
   *
   * @param width  Width of the section to place
   * @param height Height of the section to place
   * @param bounds SectionBounds to find location for.
   * @return The location of the bottom left corner of bounds2 if they can be combined, (0, 0) if
   * they could not be combined at all
   */
  private static IntVector2 getCombinedLocation(int width, int height,
                                                Iterable<SectionBounds> boundsList,
                                                SectionBounds bounds,
                                                Random random) {
    List<IntVector2> possibleLocations = new ArrayList<>();

    for (int x = bounds.x - width + 1; x < bounds.x + width - 1; x++) {
      if (collides(x, bounds.y + bounds.height, width, height, bounds, boundsList)) {
        possibleLocations.add(new IntVector2(x, bounds.y + bounds.height));
      }
      if (collides(x, bounds.y - height, width, height, bounds, boundsList)) {
        possibleLocations.add(new IntVector2(x, bounds.y - height));
      }
    }

    for (int y = bounds.y - height + 1; y < bounds.y + height - 1; y++) {
      if (collides(bounds.x + bounds.width, y, width, height, bounds, boundsList)) {
        possibleLocations.add(new IntVector2(bounds.x + bounds.width, y));
      }
      if (collides(bounds.x - width, y, width, height, bounds, boundsList)) {
        possibleLocations.add(new IntVector2(bounds.x - width, y));
      }
    }

    return possibleLocations.size() == 0 ? new IntVector2() :
           possibleLocations.get(random.nextInt(possibleLocations.size()));
  }

  private static boolean collides(int x, int y, int width, int height, SectionBounds bounds,
                                  Iterable<SectionBounds> boundsList) {
    for (SectionBounds boundsFromList : boundsList) {
      if (bounds.collides(x, y, width, height)) {
        return true;
      }
    }
    return false;
  }

  public abstract HuldraWorld getNew(double amountLargeSections, int amountOfSections, long seed,
                                     OrthographicCamera camera);
}