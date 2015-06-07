package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.IntVector2;
import com.smokebox.lib.utils.geom.Bounds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Harald Wilhelmsen on 7/6/2015.
 */
final class BoundGeneration {

  float csHor;
  float csVer;
  float csSpread;
  double csSize;
  Random random;

  BoundGeneration(float csHor, float csVer, float csSpread, double csSize,
                         Random random) {
    this.csHor = csHor;
    this.csVer = csVer;
    this.csSize = csSize;
    this.random = random;

    this.csHor = cap(csHor, 0, 1);
    this.csVer = cap(csVer, 0, 1);
    this.csSpread = cap(csSpread, 1f, Float.MAX_VALUE);
  }

  private IntVector2 getSize() {
    int width = 1 + (int) Math.abs(random.nextGaussian() * csSize);
    int height = 1 + (int) Math.abs(random.nextGaussian() * csSize);

    if (width > Section.BOUNDS_MAX_WIDTH) {
      width = Section.BOUNDS_MAX_WIDTH;
    }

    if (height > Section.BOUNDS_MAX_HEIGHT) {
      height = Section.BOUNDS_MAX_HEIGHT;
    }
    return new IntVector2(width, height);
  }

  private IntVector2 getLocation(IntVector2 dimensions, Iterable<Bounds> boundsList, Bounds bounds) {
    // get this location's open possible surrounding locations
    List<IntVector2> locations = getLocationsAround(dimensions.x, dimensions.y, boundsList, bounds);
    // if no open locations, return null, This bounds-object is no good
    if (locations.isEmpty()) {
      return null;
    } else {
      return locations.get(random.nextInt(locations.size()));
    }
  }

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
  private List<IntVector2> getLocationsAround(int width, int height,
                                              Iterable<Bounds> boundsList,
                                              Bounds bounds) {
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

    return possibleLocations;
  }

  private void addIfNotCollides(Bounds bounds, Iterable<Bounds> boundsList,
                                Collection<IntVector2> locations) {
    if (!bounds.overlapsList(boundsList)) {
      locations.add(new IntVector2(bounds.x, bounds.y));
    }
  }

  private int getNewSelection(int size) {
    return (int) cap((float) (Math.abs(random.nextGaussian()) * csSpread), 0, size - 1);
  }

  private float cap(float value, float min, float max) {
    return value < min ? min : value > max ? max : value;
  }

  List<Bounds> generateBoundsList(int amountOfSections) {
    ArrayList<Bounds> boundsList = new ArrayList<>();

    boundsList.add(new Bounds(0, 0, 1, 1));
    System.out.println("Placed spawn");

    // place down section until required amountOfSections is met
    int sectionsPlaced = 1;

    // loop until all sectionBoundsList are placed or the loop uses too many iterations
    for (int iterations = 0; iterations < 10000; iterations++) {
      System.out.println("Sections placed: " + sectionsPlaced + "/" + amountOfSections);

      // find dimensions for a new sectionBounds
      IntVector2 dimensions = getSize();

      SpreadComparator spreadComparator = new SpreadComparator();

      // find a section to expand from, and place a section there
      System.out.println(
          "Finding combined location for dimensions: " + dimensions.y + ", " + dimensions.y);
      for (int iterations2 = 0; iterations2 < 10000; iterations2++) {
        // Sort boundsList by their distance from spawn
        boundsList.sort(spreadComparator);

        // choose a random section to try to expand from
        Bounds bounds = boundsList.get(getNewSelection(boundsList.size()));
        System.out.print("SB:(" + bounds.width + "," + bounds.height + ")#");

        // Find location for new bounds
        IntVector2 location = getLocation(dimensions, boundsList, bounds);
        if (location == null) {
          continue;
        }

        Bounds toAdd = new Bounds(location.x, location.y, dimensions.x, dimensions.y);
        boundsList.add(toAdd);
        sectionsPlaced++;
        break;
      }
      System.out.println();

      if (sectionsPlaced >= amountOfSections) {
        System.out.println("Placed all sections");
        break;
      }
    }
    return boundsList;
  }
}
