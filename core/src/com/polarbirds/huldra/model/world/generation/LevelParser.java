package com.polarbirds.huldra.model.world.generation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.model.WorldType;

/**
 * Created by Harald Wilhelmsen on 28/7/2015.
 */
public class LevelParser {

  public String[] enemyTypes;
  String worldTypeString;
  WorldType type;
  int amountOfSections;
  int chests;

  public LevelParser(int level, SpriteLoader spriteLoader) {
    JsonValue json = new JsonReader().parse(Gdx.files.internal("levels/" + level + ".json"));
    worldTypeString = json.getString("worldtype");
    switch (worldTypeString) {
      case "caves":
        type = WorldType.CAVES;
        break;
      case "forest":
        type = WorldType.FOREST;
        break;
      default:
        type = WorldType.TEST_STAGE;
    }
    amountOfSections = json.getInt("sectioncount");
    enemyTypes = json.get("enemytypes").asStringArray();
    // TODO queue assets in spriteLoader

  }
}
