package com.polarbirds.huldra.model.world;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Harald Wilhelmsen on 13/5/2015.
 */
final class Section {

  TileType[][] tiles;
  OpeningArray entries;
  OpeningArray exits;

  SectionType sectionType;

  private Section(OpeningArray entries,
          OpeningArray exits) {
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
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.LEFT);
      if(random.nextFloat() > placeExitThreshold) exits.add(Opening.LEFT);
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.LEFT);
      exits.add(Opening.LEFT);
    }

    if(sections[xPlacement][yPlacement + 1] == null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.UP);
      if(random.nextFloat() > placeExitThreshold) exits.add(Opening.UP);
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.UP);
      exits.add(Opening.UP);
    }

    if(sections[xPlacement + 1][yPlacement] == null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.RIGHT);
      if(random.nextFloat() > placeExitThreshold) exits.add(Opening.RIGHT);
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.RIGHT);
      exits.add(Opening.RIGHT);
    }

    if(sections[xPlacement][yPlacement - 1] == null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.DOWN);
      if(random.nextFloat() > placeExitThreshold) exits.add(Opening.DOWN);
    } else { // left section exists
      if(random.nextFloat() > placeEntryThreshold) entries.add(Opening.DOWN);
      exits.add(Opening.DOWN);
    }

    return new Section(
        new OpeningArray((Opening[]) entries.toArray()),
        new OpeningArray((Opening[]) exits.toArray()));
  }
}