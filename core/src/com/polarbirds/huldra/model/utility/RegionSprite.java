package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 14.7.15.
 */
public class RegionSprite extends ASprite {

    public TextureRegion textureRegion;

    public RegionSprite(Vector2 shift) {
        super(shift);
    }

    @Override
    public void draw(Batch sb, Vector2 pos) {
        sb.draw(textureRegion, pos.x + shift.x, pos.y + shift.y);
    }

    @Override
    public void dispose() {
        textureRegion.getTexture().dispose();
    }
}
