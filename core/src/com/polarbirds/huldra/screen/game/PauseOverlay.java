package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Harald on 21.07.2015.
 */
public class PauseOverlay implements IOverlay {

    private GameScreen gameScreen;

    public PauseOverlay(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void init(Stage stage) {

    }

    @Override
    public void queueAssets() {

    }
}
