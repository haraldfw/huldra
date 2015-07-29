package com.polarbirds.huldra.controller.player;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;

import java.util.HashMap;

/**
 * A motive-processor for Xbox-controllers, intended for player-use Created by Harald on 14.5.15.
 */
public final class XboxController extends InputProcessor {

    Controller controller;

    public XboxController(Controller controller) {
        super(getKeys());
        this.controller = controller;
    }

    @Override
    protected boolean getIsDown(int key) {
        return controller.getButton(key);
    }

    @Override
    public float moveX() {
        float move = controller.getAxis(1);
        return Math.abs(move) > 0.2f ? move : 0;
    }

    @Override
    public float moveY() {
        return controller.getAxis(0);
    }

    @Override
    public float lookX() {
        return controller.getAxis(3);
    }

    @Override
    public float lookY() {
        return controller.getAxis(2);
    }

    @Override
    public boolean getQuickSelect1() {
        return controller.getPov(0) == PovDirection.north;
    }

    @Override
    public boolean getQuickSelect2() {
        return controller.getPov(0) == PovDirection.east;
    }

    @Override
    public boolean getQuickSelect3() {
        return controller.getPov(0) == PovDirection.south;
    }

    @Override
    public boolean getQuickSelect4() {
        return controller.getPov(0) == PovDirection.west;
    }

    private static HashMap<String, Integer> getKeys() {
        HashMap<String, Integer> keys = new HashMap<>();
        keys.put("attack1", 4);
        keys.put("attack2", 5);
        keys.put("jump", 0);
        keys.put("interact", 2);
        keys.put("menu", 6);
        keys.put("pause", 7);
        return keys;
    }
}
