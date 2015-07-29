package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.WorldGenerator;

/**
 * Created by Harald on 23.07.2015.
 */
public class GameLoadingScreen implements Screen {

    private HuldraGame game;

    private GameScreen gameScreen;
    private WorldGenerator worldGenerator;
    private SpriteLoader spriteLoader;

    public GameLoadingScreen(HuldraGame game, GameScreen gameScreen,
                             WorldGenerator worldGenerator, SpriteLoader spriteLoader) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.worldGenerator = worldGenerator;
        this.spriteLoader = spriteLoader;

        worldGenerator.startThread();
        spriteLoader.startThread();
    }

    @Override
    public void render(float delta) {
        int loaded = spriteLoader.loaded + worldGenerator.loaded;
        int max = spriteLoader.max + worldGenerator.max;
        System.out.println("Loaded: " + loaded + "/" + max);

        if (worldGenerator.done) {
            worldGenerator.placeTextures();
            if (spriteLoader.done) {
                gameScreen.level.setNew(worldGenerator.tiles, worldGenerator.spawn,
                                        gameScreen.level.difficulty + 1);
                gameScreen.setNew(spriteLoader.loadedSprites, spriteLoader.loadedAnimations);
                game.setScreen(gameScreen);
            }
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {

    }
}
