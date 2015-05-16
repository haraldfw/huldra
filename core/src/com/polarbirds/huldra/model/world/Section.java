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

  public void finalizeSection() {
    tiles = getTiles(entries, exits);
    if(hasEntry(OpeningType.LEFT)) {
      tiles[0][3] = TileType.EMPTY;
    }
    if(hasEntry(OpeningType.UP)) {
      tiles[3][7] = TileType.EMPTY;
    }
    if(hasEntry(OpeningType.RIGHT)) {
      tiles[7][3] = TileType.EMPTY;
    }
    if(hasEntry(OpeningType.DOWN)) {
      tiles[3][0] = TileType.EMPTY;
    }

    if(hasExit(OpeningType.LEFT)) {
      tiles[0][4] = TileType.EMPTY;
    }
    if(hasExit(OpeningType.UP)) {
      tiles[4][7] = TileType.EMPTY;
    }
    if(hasExit(OpeningType.RIGHT)) {
      tiles[7][4] = TileType.EMPTY;
    }
    if(hasExit(OpeningType.DOWN)) {
      tiles[4][0] = TileType.EMPTY;
    }
  }

  static Section getNew(
      Section[][] sections, int xPlacement, int yPlacement, Random random)  {
    float placeEntryThreshold = 0.5f;
    int additionalOpenings = random.nextInt(4) + 4;
    boolean leftFilled = false;
    boolean rightFilled = false;
    boolean upFilled = false;
    boolean downFilled = false;
    // if space to the left is empty, use a random-function to determine if there
    // should be and entry an/or exit there
    ArrayList<Opening> entries = new ArrayList<>();
    ArrayList<Opening> exits = new ArrayList<>();
    if(sections[xPlacement - 1][yPlacement] != null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.LEFT, false));
      exits.add(new Opening(OpeningType.LEFT, false));
      additionalOpenings--;
      leftFilled = true;
    }
    if(sections[xPlacement][yPlacement + 1] != null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.UP, false));
      exits.add(new Opening(OpeningType.UP, false));
      additionalOpenings--;
      upFilled = true;
    }
    if(sections[xPlacement + 1][yPlacement] != null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.RIGHT, false));
      exits.add(new Opening(OpeningType.RIGHT, false));
      additionalOpenings--;
      rightFilled = true;
    }

    if(sections[xPlacement][yPlacement - 1] != null) {
      if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.DOWN, false));
      exits.add(new Opening(OpeningType.DOWN, false));
      additionalOpenings--;
      downFilled = true;
    }

    while(additionalOpenings > 0) {
      int openingToFill = random.nextInt(4);
      if(openingToFill == 0 && !leftFilled) {
        exits.add(new Opening(OpeningType.LEFT, true));
        if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.LEFT, false));
        additionalOpenings--;
      }if(openingToFill == 1 && !rightFilled) {
        exits.add(new Opening(OpeningType.RIGHT, true));
        if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.RIGHT, false));
        additionalOpenings--;
      }if(openingToFill == 2 && !upFilled) {
        exits.add(new Opening(OpeningType.UP, true));
        if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.UP, false));
        additionalOpenings--;
      }if(openingToFill == 3 && !downFilled) {
        exits.add(new Opening(OpeningType.DOWN, true));
        if(random.nextFloat() > placeEntryThreshold) entries.add(new Opening(OpeningType.DOWN, false));
        additionalOpenings--;
      }

      System.out.println(additionalOpenings + ", " + leftFilled + ", " + rightFilled + ", " + upFilled + ", " + downFilled);
    }

    return new Section(entries, exits);
  }

  public boolean hasEntry(OpeningType checkFor) {
    for(Opening opening : entries) {
      if(opening.type == checkFor) return true;
    }
    return false;
  }

  public boolean hasExit(OpeningType checkFor) {
    for(Opening opening : exits) {
      if(opening.type == checkFor) return true;
    }
    return false;
  }

  public boolean hasOpening(OpeningType checkFor) {
    if(hasEntry(checkFor) || hasExit(checkFor)) return true;
    return false;
  }

  private static TileType[][] getTiles(ArrayList<Opening> entries, ArrayList<Opening> exits) {
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