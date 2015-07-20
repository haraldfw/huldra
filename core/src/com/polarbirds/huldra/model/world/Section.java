package com.polarbirds.huldra.model.world;

import com.smokebox.lib.utils.geom.Bounds;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald on 16.5.15.
 */
public class Section {

    public static final int TILES_PER_SIDE = 8;
    public static final int BOUNDS_MAX_HEIGHT = 3;
    public static final int BOUNDS_MAX_WIDTH = 3;

    Bounds bounds;

    TilesWithOpenings tilesWithOpenings;

    /*
    A hashtable of boolean-arrays representing the sides of the section with
      a boolean array for each side, showing which tiles must be open along the edges
    */
    Map<Side, boolean[]> openings;

    Section(Bounds bounds) {
        this.bounds = bounds;

        openings = new HashMap<>();

        openings.put(Side.LEFT, getTrueBoolean2(TILES_PER_SIDE * bounds.height));
        openings.put(Side.RIGHT, getTrueBoolean2(TILES_PER_SIDE * bounds.height));
        openings.put(Side.TOP, getTrueBoolean2(TILES_PER_SIDE * bounds.width));
        openings.put(Side.BOTTOM, getTrueBoolean2(TILES_PER_SIDE * bounds.width));
    }

    private boolean[] getTrueBoolean2(int length) {
        boolean[] booleans = new boolean[length];
        Arrays.fill(booleans, true);
        return booleans;
    }
}
