package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.polarbirds.huldra.model.entity.inanimateobject.Interactable;
import com.smokebox.lib.utils.IntVector2;
import com.smokebox.lib.utils.geom.Bounds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Harald on 16.5.15.
 */
public enum WorldType {

  FOREST {
    @Override
    public HuldraWorld getNew(double amountLargeSections, int amountOfSections, Random random,
                              OrthographicCamera camera) {
      return TEST_STAGE.getNew(amountLargeSections, amountOfSections, random, camera);
    }
  },

  CAVES {
    @Override
    public HuldraWorld getNew(double sizeGaussianScale, int amountOfSections, Random random,
                              OrthographicCamera camera) {
      System.out.println("Creating Caves with " + amountOfSections + " sections");

      ArrayList<Bounds> sectionBoundsList = new ArrayList<>();

      { // place spawn
        Bounds spawn = new Bounds(0, 0, 1, 1);

        sectionBoundsList.add(spawn);
        System.out.println("Placed spawn");
      }

      // place down section until requirement amountOfSections is met
      int sectionsPlaced = 1;
      // loop until all sectionBoundsList are placed or the loop uses too many iterations
      for (int iterations = 0; iterations < 10000; iterations++) {
        System.out.println("Sections placed: " + sectionsPlaced + "/" + amountOfSections);

        // find dimensions for a new sectionBounds
        int width = 1 + (int) Math.abs(random.nextGaussian() * sizeGaussianScale);
        int height = 1 + (int) Math.abs(random.nextGaussian() * sizeGaussianScale);

        if (width > Section.BOUNDS_MAX_WIDTH) {
          width = Section.BOUNDS_MAX_WIDTH;
        }
        if (height > Section.BOUNDS_MAX_HEIGHT) {
          height = Section.BOUNDS_MAX_HEIGHT;
        }

        // find a section to expand from, and place a section there
        System.out.println("Finding combined location for dimensions: " + width + ", " + height);
        for (int iterations2 = 0; iterations2 < 10000; iterations2++) {
          // choose a random section to try to expand from
          Bounds bounds =
              sectionBoundsList.get(random.nextInt(sectionBoundsList.size()));

          System.out.print("SB:(" + bounds.width + "," + bounds.height + ")#");
          // Vector to store
          IntVector2 location;
          {
            // get this location's open possible surrounding locations
            List<IntVector2> locations =
                getLocationsAround(width, height, sectionBoundsList, bounds, random);
            // if no open locations, go to next iteration. This location is no good
            if (locations.isEmpty()) {
              continue;
            } else {
              location = locations.get(random.nextInt(locations.size()));
            }
          }

          sectionBoundsList.add(new Bounds(location.x, location.y, width, height));
          sectionsPlaced++;
          break;
        }
        System.out.println();

        if (sectionsPlaced >= amountOfSections) {
          break;
        }
      }
      return new HuldraWorld(this, random, sectionBoundsList);
    }
  },

  TEST_STAGE {
    @Override
    public HuldraWorld getNew(double amountLargeSections, int amountOfSections, Random random,
                              OrthographicCamera camera) {
      ArrayList<Bounds> sectionBoundsList = new ArrayList<>();
      sectionBoundsList.add(new Bounds(0, 0, 1, 1));
      ArrayList<Interactable> interactables = new ArrayList<>();
      return new HuldraWorld(this, random, sectionBoundsList);
    }
  };

  public abstract HuldraWorld getNew(double amountLargeSections, int amountOfSections,
                                     Random random, OrthographicCamera camera);

  /**
   * Returns the location of where bounds2 can be placed with no intersections. Note: Can safely
   * return (0, 0) because this place will always be filled with spawn-section.
   *
   * @param width      Width of the section to place
   * @param height     Height of the section to place
   * @param boundsList List of bounds to check collisions with
   * @param bounds     SectionBounds to find location around.
   * @return The location of the bottom left corner of bounds2 if they can be combined, (0, 0) if
   * they could not be combined at all
   */
  private static List<IntVector2> getLocationsAround(int width, int height,
                                                     Iterable<Bounds> boundsList,
                                                     Bounds bounds,
                                                     Random random) {
    List<IntVector2> possibleLocations = new ArrayList<>();
    Bounds newBounds = new Bounds(0, 0, width, height);

    for (int x = bounds.x - newBounds.width + 1;
         x < bounds.x + bounds.width;
         x++) {
      addIfNotCollides(new Bounds(x, bounds.y + bounds.height, width, height),
                       boundsList, possibleLocations);
      addIfNotCollides(new Bounds(x, bounds.y - height, width, height),
                       boundsList, possibleLocations);
    }

    for (int y = bounds.y - newBounds.height + 1;
         y < bounds.y + bounds.height;
         y++) {
      addIfNotCollides(new Bounds(bounds.x + bounds.width, y, width, height),
                       boundsList, possibleLocations);
      addIfNotCollides(new Bounds(bounds.x - width, y, width, height),
                       boundsList, possibleLocations);
    }

    System.out.print(possibleLocations.size() + " ");
    return possibleLocations;
  }

  private static void addIfNotCollides(Bounds bounds, Iterable<Bounds> boundsList,
                                       Collection<IntVector2> locations) {
    if (!bounds.overlapsList(boundsList)) {
      locations.add(new IntVector2(bounds.x, bounds.y));
    }
  }
}