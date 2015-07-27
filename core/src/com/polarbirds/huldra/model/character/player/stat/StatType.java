package com.polarbirds.huldra.model.character.player.stat;

import com.badlogic.gdx.math.Vector3;
import com.polarbirds.huldra.model.character.ADynamicCharacter;
import com.polarbirds.huldra.model.character.player.gear.AGear;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public enum StatType {
    DMG_OVERALL,
    DMG_PHYSICAL {
        @Override
        public float calculate(ADynamicCharacter character) {
            Vector3 stats = getTotal(character, this);
            merge(stats, getTotal(character, DMG_OVERALL));
            return get(stats);
        }
    },
    DMG_MAGICAL {
        @Override
        public float calculate(ADynamicCharacter character) {
            Vector3 stats = getTotal(character, this);
            merge(stats, getTotal(character, DMG_OVERALL));
            return get(stats);
        }
    },
    DMG_LIGHTNING {
        @Override
        public float calculate(ADynamicCharacter character) {
            Vector3 stats = getTotal(character, this);
            merge(stats, getTotal(character, DMG_OVERALL));
            return get(stats);
        }
    },
    DMG_FIRE {
        @Override
        public float calculate(ADynamicCharacter character) {
            Vector3 stats = getTotal(character, this);
            merge(stats, getTotal(character, DMG_OVERALL));
            return get(stats);
        }
    },
    DMG_ICE {
        @Override
        public float calculate(ADynamicCharacter character) {
            Vector3 stats = getTotal(character, this);
            merge(stats, getTotal(character, DMG_OVERALL));
            return get(stats);
        }
    },
    KNOCKBACK,
    JUMP_STRENGTH,
    MOVE_STRENGTH,
    MAX_HEALTH;

    protected final float get(Vector3 v) {
        return v.x * v.y + v.z;
    }

    protected final void merge(Vector3 v1, Vector3 v2) {
        v1.x += v2.x;
        v1.y *= v2.y;
        v1.z += v2.z;
    }

    public float calculate(ADynamicCharacter character) {
        Vector3 stats = getTotal(character, this);
        return get(stats);
    }

    protected final Vector3 getTotal(ADynamicCharacter character, StatType type) {
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
}

