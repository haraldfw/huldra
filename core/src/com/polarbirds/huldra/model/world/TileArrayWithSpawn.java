package com.polarbirds.huldra.model.world;

import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 23.07.2015.
 */
public class TileArrayWithSpawn {

    public TileType[][] tiles;
    public Vector2 spawn;

    public TileArrayWithSpawn(TileType[][] tiles, Vector2 spawn) {
        this.tiles = tiles;
        this.spawn = spawn;
    }
}
