package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;

/**
 * Created by Harald on 23.07.2015.
 */
public class CharacterSelectionScreen implements Screen, INeedsLoading {

    private HuldraGame game;

    private PlayerCharacter[] players;

    public CharacterSelectionScreen(HuldraGame game) {
        this.game = game;
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void nextScreen() {
        game.setScreen(new GameScreen(game, players));
    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

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
}
