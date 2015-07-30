package com.polarbirds.huldra.controller.player;

import com.polarbirds.huldra.controller.IMotiveProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 7/6/2015.
 */
public abstract class InputProcessor implements IMotiveProcessor {

  private HashMap<String, Integer> keys;
  private HashMap<String, Boolean> shouldToggle;
  private HashMap<String, Boolean> wasPressed;
  private HashMap<String, Boolean> isPressed;

  public InputProcessor(HashMap<String, Integer> keys) {
    this.keys = keys;

    String attack1 = "attack1";
    String attack2 = "attack2";
    String jump = "jump";
    String interact = "interact";
    String menu = "menu";
    String pause = "pause";
    String quick1 = "quick1";
    String quick2 = "quick2";
    String quick3 = "quick3";
    String quick4 = "quick14";

    shouldToggle = new HashMap<>();
    shouldToggle.put(attack1, true);
    shouldToggle.put(attack2, true);
    shouldToggle.put(jump, true);
    shouldToggle.put(interact, true);
    shouldToggle.put(menu, true);
    shouldToggle.put(pause, true);
    shouldToggle.put(quick1, true);
    shouldToggle.put(quick2, true);
    shouldToggle.put(quick3, true);
    shouldToggle.put(quick4, true);

    isPressed = new HashMap<>();
    isPressed.put(attack1, false);
    isPressed.put(attack2, false);
    isPressed.put(jump, false);
    isPressed.put(interact, false);
    isPressed.put(menu, false);
    isPressed.put(pause, false);
    isPressed.put(quick1, false);
    isPressed.put(quick2, false);
    isPressed.put(quick3, false);
    isPressed.put(quick4, false);

    wasPressed = new HashMap<>();
    wasPressed.put(attack1, false);
    wasPressed.put(attack2, false);
    wasPressed.put(jump, false);
    wasPressed.put(interact, false);
    wasPressed.put(menu, false);
    wasPressed.put(pause, false);
  }

  @Override
  public final boolean attack1() {
    return getButton("attack1");
  }

  @Override
  public final boolean attack2() {
    return getButton("attack2");
  }

  @Override
  public final boolean jump() {
    return getButton("jump");
  }

  @Override
  public final boolean interact() {
    return getButton("interact");
  }

  @Override
  public final boolean toggleMenu() {
    return getButton("menu");
  }

  @Override
  public final boolean pause() {
    return getButton("pause");
  }

  @Override
  public final void update() {
    for (Map.Entry<String, Boolean> pressed : isPressed.entrySet()) {
      // check if the key should be a toggle-key
      if (!shouldToggle.get(pressed.getKey())
          || !getIsDown(keys.get(pressed.getKey()))) {
        isPressed.put(pressed.getKey(), false);
      }
    }
  }

  private boolean getButton(String key) {
    if (!shouldToggle.get(key)) {
      return getIsDown(keys.get(key));
    }
    if (getIsDown(keys.get(key)) && !isPressed.get(key)) {
      isPressed.put(key, true);
      return true;
    }
    return false;
  }

  public abstract boolean getQuickSelect1();

  public abstract boolean getQuickSelect2();

  public abstract boolean getQuickSelect3();

  public abstract boolean getQuickSelect4();

  protected abstract boolean getIsDown(int key);
}
