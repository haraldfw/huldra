package com.polarbirds.huldra.model.entity.animation;


import com.polarbirds.huldra.model.utility.ASprite;

/**
 * A class for animations with only one sprite Created by Harald Wilhelmsen on 16/6/2015.
 */
public class StaticAnimation extends AAnimation {

    private final ASprite s;

    public StaticAnimation(ASprite s) {
        this.s = s;
    }

    @Override
    public void update(Object caller, float delta) {
    }

    @Override
    protected float getTotalTime() {
        return 0;
    }

    @Override
    protected ASprite getCurrentFrame(Object caller) {
        return s;
    }

}
