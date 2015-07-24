package com.polarbirds.huldra.screen.game.overlay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;

/**
 * Created by Harald on 21.07.2015.
 */
public class HudOverlay implements IOverlay {

    private PlayerCharacter[] players;

    public HudOverlay(PlayerCharacter[] players) {
        this.players = players;

    }

    @Override
    public void render(Batch batch) {

    }

    @Override
    public void init() {

    }

    @Override
    public void queueAssets() {

    }
}
