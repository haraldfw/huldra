package com.polarbirds.huldra.model.world;

import com.polarbirds.huldra.model.entity.inanimate.Interactable;
import com.smokebox.lib.utils.geom.Bounds;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Harald on 16.5.15.
 */
public enum WorldType {

  FOREST {
    @Override
    public HuldraWorld getNew(double csSize, int sectionCount, Random random) {
      return TEST_STAGE.getNew(csSize, sectionCount, random);
    }
  },

  CAVES {
    @Override
    public HuldraWorld getNew(double csSize, int sectionCount, Random random) {
      System.out.println("Creating Caves with " + sectionCount + " sections");

      return new HuldraWorld(
          this, random,
          new BoundGenerator(1, 0, 1000, csSize, random).generateBoundsList(sectionCount)
      );
    }
  },

  TEST_STAGE {
    @Override
    public HuldraWorld getNew(double csSize, int sectionCount, Random random) {
      ArrayList<Bounds> sectionBoundsList = new ArrayList<>();
      sectionBoundsList.add(new Bounds(0, 0, 1, 1));
      ArrayList<Interactable> interactables = new ArrayList<>();
      return new HuldraWorld(this, random, sectionBoundsList);
    }
  };

  /**
   * Method for generating a HuldraWorld-object of the given type and parameters.
   *
   * @param csSize       Chance scale size. The higher the number, the more large rooms.
   * @param sectionCount How many sections to generate
   * @param random       Random-object to use
   * @return A HuldraWorld instance with the information
   */
  public abstract HuldraWorld getNew(double csSize, int sectionCount, Random random);
}