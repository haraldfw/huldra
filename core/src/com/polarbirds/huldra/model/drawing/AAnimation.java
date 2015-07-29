package com.polarbirds.huldra.model.drawing;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.util.HashMap;
import java.util.Map;

/**
 * A class for storing, updating and drawing animations. Created by Harald Wilhelmsen on 10/6/2015.
 */
public abstract class AAnimation {

    protected Map<Object, Float> timePassed;

    public AAnimation() {
        timePassed = new HashMap<>();
    }

    public final void subscribe(Object newSubscriber) {
        timePassed.put(newSubscriber, 0f);
    }

    public final void unsubscribe(Object subscriber) {
        timePassed.remove(subscriber);
    }

    public void update(Object caller, float delta) {
        float time = timePassed.get(caller) + delta;
        float totalTime = getTotalTime();
        if (time > totalTime) {
            timePassed.put(caller, time - totalTime);
        } else {
            timePassed.put(caller, time);
        }
    }

    protected abstract float getTotalTime();

    protected abstract ASprite getCurrentFrame(Object caller);

    public final void draw(Object caller, Batch sb, Vector2 pos) {
        getCurrentFrame(caller).draw(sb, pos);
    }
}
