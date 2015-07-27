package com.polarbirds.huldra.model.character.player.gear;

import com.polarbirds.huldra.model.character.player.stat.IStatContainer;
import com.polarbirds.huldra.model.character.player.stat.StatModifier;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public abstract class AGear implements IStatContainer {

    private StatModifier[] stats;

    public AGear(StatModifier[] stats) {
        this.stats = stats;
    }

    @Override
    public StatModifier[] getMods() {
        return stats;
    }
}
