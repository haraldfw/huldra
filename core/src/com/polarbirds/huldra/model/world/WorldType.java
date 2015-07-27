package com.polarbirds.huldra.model.world;

/**
 * Created by Harald on 16.5.15.
 */
public enum WorldType {
    // Order: float rsHor, float rsVer, float rsSpread, float rsSize, String[] texturePaths
    FOREST(1, 0.1f, Float.MAX_VALUE, 1, new String[0]),
    CAVES(1, 1, 1, 1, new String[]{
        "graphics/world/tiles/caves/solid.png",
        "graphics/world/tiles/caves/solid_ftffffff.png",
        "graphics/world/tiles/caves/solid_ttffffft.png",
        "graphics/world/tiles/caves/solid_ftttffff.png",
    }),
    TEST_STAGE(1, 1, 1, 1, new String[0]);
    /**
     * Chance for rooms to be spread horizontally, rather than vertically. 0 <= x <= 1
     */
    public final float rsHor;
    /**
     * Chance for rooms to be spread vertically, rather than horizontally. 0 <= x <= 1
     */
    public final float rsVer;
    /**
     * Chance for rooms to occupy spaces further from the initial spawn room. 1 <= x <=
     * Float.MAX_VALUE
     */
    public final float rsSpread;
    /**
     * Change for rooms to be max size. The higher the number the closer to max size they will be.
     * The larger this float is, the more rooms will be max size. If this float is Float.MAX_VALUE,
     * all rooms will be max size.
     */
    public final float rsSize;

    public final String[] texturePaths;

    WorldType(float rsHor, float rsVer, float rsSpread, float rsSize, String[] texturePaths) {
        this.rsHor = rsHor;
        this.rsVer = rsVer;
        this.rsSpread = rsSpread;
        this.rsSize = rsSize;
        this.texturePaths = texturePaths;
    }
}