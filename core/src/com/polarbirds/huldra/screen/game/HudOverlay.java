package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;

import java.util.ArrayList;

/**
 * Created by Harald on 21.07.2015.
 */
public class HudOverlay implements IOverlay {

    private ArrayList<PlayerCharacter> players;

    public HudOverlay(ArrayList<PlayerCharacter> players) {
        this.players = players;

    }

    @Override
    public void init(Stage stage) {

    }

    @Override
    public void queueAssets() {

    }
}
