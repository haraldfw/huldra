package com.polarbirds.huldra.model.drawing.drawable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Harald Wilhelmsen on 29/7/2015.
 */
public class TextureDrawable implements Drawable {

    public Texture texture;

    public TextureDrawable(Texture texture) {
        this.texture = texture;
    }

    @Override
    public void draw(Batch batch, float x, float y) {
        batch.draw(texture, x, y);
    }
}
