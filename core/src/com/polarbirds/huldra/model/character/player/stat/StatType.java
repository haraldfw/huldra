package com.polarbirds.huldra.model.character.player.stat;

import com.badlogic.gdx.math.Vector3;
import com.polarbirds.huldra.model.character.ADynamicCharacter;
import com.polarbirds.huldra.model.character.player.gear.AGear;

import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public enum StatType {
    DAMAGE,
    JUMP_STRENGTH,
    MOVE_STRENGTH,
    MAX_HEALTH;

    public float calculate(ADynamicCharacter character) {
        Vector3 stats = new Vector3();
        stats.add(calcAr(character.getBaseStats()));
        stats.add(calcMap(character.getGear()));
        return stats.x * stats.y + stats.z;
    }

    private Vector3 calcMap(Map<String, AGear> map) {
        Vector3 toReturn = new Vector3(0, 1, 0);
        for (Map.Entry<String, AGear> entry : map.entrySet()) {
            Vector3 gotten = calcAr(entry.getValue().getMods());
            merge(toReturn, gotten);
        }
        return toReturn;
    }

    private void merge(Vector3 v1, Vector3 v2) {
        v1.x += v2.x;
        v1.y *= v2.y;
        v1.z += v2.z;
    }

    private Vector3 calcAr(StatModifier[] mods) {
        Vector3 toReturn = new Vector3(0, 1, 0);
        for (StatModifier stat : mods) {
            if (stat.statType == this) {
                switch (stat.statClass) {
                    case BASE:
                        toReturn.x += stat.value;
                        break;
                    case SCALE:
                        toReturn.y *= stat.value;
                        break;
                    case ADD:
                        toReturn.z += stat.value;
                        break;
                }
            }
        }
        return toReturn;
    }
}

