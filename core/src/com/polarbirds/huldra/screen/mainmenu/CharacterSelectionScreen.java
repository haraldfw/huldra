package com.polarbirds.huldra.screen.mainmenu;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.player.Knight;
import com.polarbirds.huldra.model.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.utility.ALoader;
import com.polarbirds.huldra.screen.game.GameLoadingScreen;
import com.polarbirds.huldra.screen.game.GameScreen;
import com.polarbirds.huldra.screen.loading.INeedsLoading;

import java.util.ArrayList;

/**
 * Created by Harald on 23.07.2015.
 */
public class CharacterSelectionScreen implements Screen, INeedsLoading {

    private HuldraGame game;

    private ArrayList<PlayerCharacter> playerList;


    public CharacterSelectionScreen(HuldraGame game) {
        this.game = game;
        playerList = new ArrayList<>();
        playerList.add(new Knight(Team.PLAYER));
    }

    @Override
    public void render(float delta) {
        game.setScreen(new GameLoadingScreen(this, new ALoader[]{
            game.spriteLoader
        }));
    }

    @Override
    public void loadingFinished() {
        game.setScreen(new GameScreen(
            game, playerList.toArray(new PlayerCharacter[playerList.size()])));
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
