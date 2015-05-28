package com.polarbirds.huldra.controller.player;

import com.badlogic.gdx.controllers.Controller;
import com.polarbirds.huldra.controller.IMotiveProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * A motive-processor for Xbox-controllers, intended for player-use Created by Harald on 14.5.15.
 */
public class XboxController implements IMotiveProcessor {

  Controller controller;

  private HashMap<String, Integer> keys;
  private HashMap<String, Boolean> shouldToggle;
  private HashMap<String, Boolean> isPressed;

  public XboxController(Controller controller) {
    this.controller = controller;

    String attack1 = "attack1";
    String attack2 = "attack2";
    String jump = "jump";
    String interact = "interact";
    String menu = "menu";
    String pause = "pause";

    keys = new HashMap<>();
    keys.put(attack1, 4);
    keys.put(attack2, 5);
    keys.put(jump, 0);
    keys.put(interact, 2);
    keys.put(menu, 6);
    keys.put(pause, 7);

    shouldToggle = new HashMap<>();
    shouldToggle.put(attack1, true);
    shouldToggle.put(attack2, true);
    shouldToggle.put(jump, true);
    shouldToggle.put(interact, true);
    shouldToggle.put(menu, true);
    shouldToggle.put(pause, true);

    isPressed = new HashMap<>();
    isPressed.put(attack1, false);
    isPressed.put(attack2, false);
    isPressed.put(jump, false);
    isPressed.put(interact, false);
    isPressed.put(menu, false);
    isPressed.put(pause, false);
  }

  @Override
  public void update() {
    for (Map.Entry<String, Boolean> pressed : isPressed.entrySet()) {
      // check if the key should be a toggle-key
      if (!shouldToggle.get(pressed.getKey())
          || !controller.getButton(keys.get(pressed.getKey()))) {
        isPressed.put(pressed.getKey(), false);
      }
    }
  }

  @Override
  public boolean attack1() {
    return getToggledButton("attack1");
  }

  @Override
  public boolean attack2() {
    return getToggledButton("attack2");
  }

  @Override
  public boolean jump() {
    return getToggledButton("jump");
  }

  @Override
  public boolean interact() {
    return getToggledButton("interact");
  }

  @Override
  public boolean toggleMenu() {
    return getToggledButton("menu");
  }

  @Override
  public boolean pause() {
    return getToggledButton("pause");
  }

  private boolean getToggledButton(String key) {
    if (!shouldToggle.get(key)) {
      return controller.getButton(keys.get(key));
    }
    if (controller.getButton(keys.get(key)) && !isPressed.get(key)) {
      isPressed.put(key, true);
      return true;
    }
    return false;
  }

  @Override
  public float moveX() {
    float move = controller.getAxis(1);
    return Math.abs(move) > 0.25f ? move : 0;
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
}
