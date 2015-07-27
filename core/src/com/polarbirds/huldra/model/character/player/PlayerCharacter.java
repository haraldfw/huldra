package com.polarbirds.huldra.model.character.player;

import com.badlogic.gdx.controllers.Controllers;
import com.polarbirds.huldra.controller.player.Keyboard;
import com.polarbirds.huldra.controller.player.XboxController;
import com.polarbirds.huldra.model.character.AWalkingCharacter;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.player.gear.AGear;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald on 1.5.15.
 */
public abstract class PlayerCharacter extends AWalkingCharacter {

    public Map<String, AGear> gear;

    public PlayerCharacter(Vector2 pos, Team team, GameScreen gameScreen) {
        super(pos, 0.5f, 0.7f, 0.0167f, team, gameScreen);
        this.input = Controllers.getControllers().size > 0 ?
                     new XboxController(Controllers.getControllers().get(0))
                                                           : new Keyboard(
                                                               gameScreen.game.staticViewCamera);
        gear = new HashMap<>(10);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public Map<String, AGear> getGear() {
        return null;
    }
}
