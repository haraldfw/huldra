package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.polarbirds.huldra.model.entity.inanimate.Interactable;
import com.smokebox.lib.utils.IntVector2;
import com.smokebox.lib.utils.geom.Bounds;

import java.util.ArrayList;
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

      return new HuldraWorld(
          this,
          random,
          new BoundGeneration(1, 1, 0, sizeGaussianScale, random).generateBoundsList(amountOfSections)
      );
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
}