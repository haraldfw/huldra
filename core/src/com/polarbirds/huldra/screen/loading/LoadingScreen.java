package com.polarbirds.huldra.screen.loading;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.model.utility.ALoader;

/**
 * Created by Harald on 23.07.2015.
 */
public class LoadingScreen implements Screen {

    private INeedsLoading screen;
    private ALoader[] loaders;

    public LoadingScreen(INeedsLoading screen, ALoader[] loaders) {
        this.screen = screen;
        this.loaders = loaders;

        for (ALoader loader : loaders) {
            loader.startThread();
        }
    }

    @Override
    public void render(float delta) {
        int loaded = 0;
        int max = 0;

        for (ALoader loader : loaders) {
            loaded += loader.getLoaded();
            max += loader.getMax();
        }

        System.out.println("Loaded: " + loaded + "/" + max);

        for (ALoader loader : loaders) {
            if (!loader.isDone()) {
                return;
            }
        }

        screen.nextScreen();
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
