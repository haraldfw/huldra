package com.polarbirds.huldra.model.particleengine.effect;

import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald Wilhelmsen on 26/7/2015.
 */
public interface IEffect {

    void update(float delta, Vector2 pos);
}
