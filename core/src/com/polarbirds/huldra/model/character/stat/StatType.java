package com.polarbirds.huldra.model.character.stat;

import com.badlogic.gdx.math.Vector3;
import com.polarbirds.huldra.model.character.animate.player.gear.AGear;
import com.polarbirds.huldra.model.character.animate.player.gear.GearWearer;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public enum StatType {
  DMG_OVERALL,
  DMG_PHYSICAL {
    @Override
    public float calculate(GearWearer character) {
      return calculateMerged(DMG_OVERALL, character);
    }
  },
  DMG_MAGICAL {
    @Override
    public float calculate(GearWearer character) {
      return calculateMerged(DMG_OVERALL, character);
    }
  },
  DMG_LIGHTNING {
    @Override
    public float calculate(GearWearer character) {
      return calculateMerged(DMG_OVERALL, character);
    }
  },
  DMG_FIRE {
    @Override
    public float calculate(GearWearer character) {
      return calculateMerged(DMG_OVERALL, character);
    }
  },
  DMG_ICE {
    @Override
    public float calculate(GearWearer character) {
      return calculateMerged(DMG_OVERALL, character);
    }
  },
  KNOCKBACK,
  JUMP_STRENGTH,
  MOVE_STRENGTH,
  MAX_HEALTH;

  protected final float convertToFloat(Vector3 v) {
    return v.x * v.y + v.z;
  }

  protected final void merge(Vector3 v1, Vector3 v2) {
    v1.x += v2.x;
    v1.y *= v2.y;
    v1.z += v2.z;
  }

  public float calculate(GearWearer character) {
    Vector3 stats = getTotal(character, this);
    return convertToFloat(stats);
  }

  protected final Vector3 getTotal(GearWearer character, StatType type) {
    Vector3 stats = new Vector3(0, 1, 0);
    mergeArray(stats, character.getBaseStats(), type);
    mergeMap(stats, character.getGear(), type);
    return stats;
  }

  private void mergeMap(Vector3 mergeInto, Map<String, AGear> map, StatType type) {
    for (Map.Entry<String, AGear> entry : map.entrySet()) {
      mergeArray(mergeInto, entry.getValue().getMods(), type);
    }
  }

  private void mergeArray(Vector3 mergeInto, StatModifier[] mods, StatType type) {
    for (StatModifier stat : mods) {
      if (stat.statType == type) {
        switch (stat.statClass) {
          case BASE:
            mergeInto.x += stat.value;
            break;
          case SCALE:
            mergeInto.y *= stat.value;
            break;
          case ADD:
            mergeInto.z += stat.value;
            break;
        }
      }
    }
  }

  protected final float calculateMerged(
      StatType mergeWith, GearWearer character) {
    Vector3 stats = getTotal(character, this);
    merge(stats, getTotal(character, mergeWith));
    return convertToFloat(stats);
  }
}

