package com.polarbirds.huldra.model.loading;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.polarbirds.huldra.model.character.stat.LoadedStatHandler;
import com.polarbirds.huldra.model.character.stat.StatClass;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.character.stat.StatType;
import com.polarbirds.huldra.model.loading.ALoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 31/7/2015.
 */
public class StatLoader extends ALoader {

  private final String[] pathEndings;
  private final HashMap<String, StatModifier[]> statMap = new HashMap<>();
  private Map<String, StatType> stringStatTypeMap;

  public StatLoader(String[] pathEndings) {
    this.pathEndings = pathEndings;
    max = pathEndings.length;
  }

  @Override
  public void run() {
    initStringTypeMap();

    for (String characterName : pathEndings) {
      JsonValue json =
          new JsonReader().parse(Gdx.files.internal("stats/" + characterName + ".json"));

      List<StatModifier> stats = new ArrayList<>();

      // loop through each stat
      for (JsonValue entry = json.child(); entry != null; entry = entry.next()) {
        StatType type = stringStatTypeMap.get(entry.name());
        addIfExists(entry, "base", type, StatClass.BASE, stats);
        addIfExists(entry, "scale", type, StatClass.SCALE, stats);
        addIfExists(entry, "add", type, StatClass.ADD, stats);
      }

      statMap.put(characterName, stats.toArray(new StatModifier[stats.size()]));
      loaded++;
    }

    done = true;
  }

  private void addIfExists(JsonValue entry, String name, StatType statT, StatClass statC,
                           Collection<StatModifier> stats) {
    try {
      float f = entry.getFloat(name);
      stats.add(new StatModifier(statT, statC, f));
    } catch (IllegalArgumentException e) {

    }
  }

  public LoadedStatHandler getHandler() {
    return new LoadedStatHandler(statMap);
  }

  private void initStringTypeMap() {
    stringStatTypeMap = new HashMap<>();
    stringStatTypeMap.put("dmgoverall", StatType.DMG_OVERALL);
    stringStatTypeMap.put("dmgphysical", StatType.DMG_PHYSICAL);
    stringStatTypeMap.put("dmgmagical", StatType.DMG_MAGICAL);
    stringStatTypeMap.put("dmglightning", StatType.DMG_LIGHTNING);
    stringStatTypeMap.put("dmgfire", StatType.DMG_FIRE);
    stringStatTypeMap.put("dmgice", StatType.DMG_ICE);
    stringStatTypeMap.put("jumpstrength", StatType.JUMP_STRENGTH);
    stringStatTypeMap.put("movestrength", StatType.MOVE_STRENGTH);
    stringStatTypeMap.put("knockback", StatType.KNOCKBACK);
    stringStatTypeMap.put("maxhealth", StatType.MAX_HEALTH);
  }
}
