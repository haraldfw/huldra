package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;

/**
 * Created by Harald on 23.07.2015.
 */
public class LoadingScreen implements Screen {

    private HuldraGame game;
    private INeedsLoading screen;

    public LoadingScreen(HuldraGame game, INeedsLoading screen) {
        this.game = game;
        this.screen = screen;
        game.spriteLoader.run();
    }

    @Override
    public void render(float delta) {
        System.out.println(game.spriteLoader.getProgress());
        if(game.spriteLoader.isDone) {
            screen.nextScreen();
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
