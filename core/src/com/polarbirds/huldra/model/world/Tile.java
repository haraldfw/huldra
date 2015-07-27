package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Harald Wilhelmsen on 27/7/2015.
 */
public class Tile {

    public TileType tileType;
    public Texture texture;

    public Tile(TileType tileType) {
        this.tileType = tileType;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
