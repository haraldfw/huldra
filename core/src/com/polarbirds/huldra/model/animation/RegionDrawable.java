package com.polarbirds.huldra.model.animation;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Harald Wilhelmsen on 29/7/2015.
 */
public class RegionDrawable implements Drawable {

    TextureRegion region;

    public RegionDrawable(TextureRegion region) {
        this.region = region;
    }

    @Override
    public void draw(Batch batch, float x, float y) {
        batch.draw(region, x, y);
    }
}
