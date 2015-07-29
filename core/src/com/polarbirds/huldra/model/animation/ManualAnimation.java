package com.polarbirds.huldra.model.animation;

import com.polarbirds.huldra.model.utility.ASprite;

import java.util.HashMap;

/**
 * Created by Harald Wilhelmsen on 30/6/2015.
 */
public class ManualAnimation extends AAnimation {

    public ASprite[] frames;
    private HashMap<Object, Integer> activeFrames;

    public ManualAnimation() {
        activeFrames = new HashMap<>();
    }

    public void setFrame(Object caller, int frameId) {
        activeFrames.put(caller, frameId);
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
        return frames[activeFrames.get(caller)];
    }

}
