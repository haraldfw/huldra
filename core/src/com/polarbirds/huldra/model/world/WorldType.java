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

        // find a section to expand from, and place a section there
        for (int iterations2 = 0; iterations2 < 10000; iterations2++) {
          // choose a random section to try to expand from
          SectionBounds sectionBounds =
              sectionBoundsList.get(random.nextInt(sectionBoundsList.size()));

          // get this location's openLocations
          IntVector2 location =
              getCombinedLocation(width, height, sectionBoundsList, sectionBounds, random);
          // if no open locations, go to next iteration. This location is no good.
          if (location.isZero()) {
            continue;
          }

          sectionBoundsList.add(new SectionBounds(location.x, location.y, width, height));
          sectionsPlaced++;
          break;
        }

        if (sectionsPlaced >= amountOfSections) {
          break;
        }
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

  /**
   * Returns the location of where bounds2 can be placed with no intersections. Note: Can safely
   * return (0, 0) because this place will always be filled with spawn-section.
   *
   * @param width      Width of the section to place
   * @param height     Height of the section to place
   * @param boundsList List of bounds to check collisions with
   * @param bounds     SectionBounds to find location for.
   * @return The location of the bottom left corner of bounds2 if they can be combined, (0, 0) if
   * they could not be combined at all
   */
  private static IntVector2 getCombinedLocation(int width, int height,
                                                Iterable<SectionBounds> boundsList,
                                                SectionBounds bounds,
                                                Random random) {
    List<IntVector2> possibleLocations = new ArrayList<>();

    for (int x = bounds.x - width + 1; x < bounds.x + width - 1; x++) {
      if (collides(x, bounds.y + bounds.height, width, height, boundsList)) {
        possibleLocations.add(new IntVector2(x, bounds.y + bounds.height));
      }
      if (collides(x, bounds.y - height, width, height, boundsList)) {
        possibleLocations.add(new IntVector2(x, bounds.y - height));
      }
    }

    for (int y = bounds.y - height + 1; y < bounds.y + height - 1; y++) {
      if (collides(bounds.x + bounds.width, y, width, height, boundsList)) {
        possibleLocations.add(new IntVector2(bounds.x + bounds.width, y));
      }
      if (collides(bounds.x - width, y, width, height, boundsList)) {
        possibleLocations.add(new IntVector2(bounds.x - width, y));
      }
    }

    return possibleLocations.size() == 0 ? new IntVector2() :
           possibleLocations.get(random.nextInt(possibleLocations.size()));
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
  private static boolean collides(int x, int y, int width, int height,
                                  Iterable<SectionBounds> boundsList) {
    for (SectionBounds boundsFromList : boundsList) {
      if (boundsFromList.collides(x, y, width, height)) {
        return true;
      }
    }
    return false;
  }

  public abstract HuldraWorld getNew(double amountLargeSections, int amountOfSections, long seed,
                                     OrthographicCamera camera);
}