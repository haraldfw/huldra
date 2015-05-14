package com.polarbirds.huldra.model.world;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Harald Wilhelmsen on 13/5/2015.
 */
final class Section {

  TileType[][] tiles;
  ArrayList<Opening> entries;
  ArrayList<Opening> exits;

  SectionType sectionType;

  private Section(ArrayList<Opening> entries,
                  ArrayList<Opening> exits) {
    this.entries = entries;
    this.exits = exits;
    sectionType = SectionType.FILL;
  }

  static Section getNew(
      Section[][] sections, int xPlacement, int yPlacement, Random random)  {
    final float placeEntryThreshold = 0.5f;
    final float placeExitThreshold = 0.5f;
    // if space to the left is empty, use a random-function to determine if there
    // should be and entry an/or exit there
    ArrayList<Opening> entries = new ArrayList<>();
    ArrayList<Opening> exits = new ArrayList<>();
    if(sections[xPlacement - 1][yPlacement] == null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.LEFT, true));
      if(random.nextFloat() > placeExitThreshold) exits.add(new Opening(OpeningType.LEFT, true));
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.LEFT, false));
      exits.add(new Opening(OpeningType.LEFT, false));
    }

    if(sections[xPlacement][yPlacement + 1] == null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.UP, true));
      if(random.nextFloat() > placeExitThreshold) exits.add(new Opening(OpeningType.UP, true));
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.UP, false));
      exits.add(new Opening(OpeningType.UP, false));
    }

    if(sections[xPlacement + 1][yPlacement] == null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.RIGHT, true));
      if(random.nextFloat() > placeExitThreshold) exits.add(new Opening(OpeningType.RIGHT, true));
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.RIGHT, false));
      exits.add(new Opening(OpeningType.RIGHT, false));
    }

    if(sections[xPlacement][yPlacement - 1] == null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.DOWN, true));
      if(random.nextFloat() > placeExitThreshold) exits.add(new Opening(OpeningType.DOWN, true));
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.DOWN, false));
      exits.add(new Opening(OpeningType.DOWN, false));
    }

    return new Section(entries, exits);
  }

  private static TileType[][] getTiles(ArrayList<OpeningType> entries, ArrayList<OpeningType> exits) {
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
    return tiles;
  }
}