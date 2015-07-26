package com.polarbirds.huldra.model.animation;

import com.polarbirds.huldra.model.utility.ASprite;

/**
 * A class for animations with equal frametimes for each frame. Created by Harald Wilhelmsen on
 * 16/6/2015.
 */
public class SimpleAnimation extends AAnimation {

    private final ASprite[] frames;
    private final float timePerFrame;

    public SimpleAnimation(ASprite[] frames, float timePerFrame) {
        this.frames = frames;
        this.timePerFrame = timePerFrame;
    }

    @Override
    protected float getTotalTime() {
        return timePerFrame * frames.length;
    }

    @Override
    protected ASprite getCurrentFrame(Object caller) {
        return frames[(int) (timePassed.get(this) / timePerFrame)];
    }

}
