package com.polarbirds.huldra.controller.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.polarbirds.huldra.HuldraGame;

import java.util.HashMap;

/**
 * Created by Harald on 14.5.15.
 */
public final class Keyboard extends InputProcessor {

  OrthographicCamera camera;

  public Keyboard(OrthographicCamera camera) {
    super(getKeys());
    this.camera = camera;
  }

  private static HashMap<String, Integer> getKeys() {
    HashMap<String, Integer> keys = new HashMap<>();
    keys.put("attack1", Input.Keys.J);
    keys.put("attack2", Input.Keys.K);
    keys.put("jump", Input.Keys.SPACE);
    keys.put("interact", Input.Keys.E);
    keys.put("menu", Input.Keys.TAB);
    keys.put("pause", Input.Keys.ESCAPE);
    return keys;
  }

  @Override
  public float moveX() {
    return (Gdx.input.isKeyPressed(Input.Keys.A) ? -1 : 0) +
           (Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0);
  }

  @Override
  public float moveY() {
    return (Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0) +
           (Gdx.input.isKeyPressed(Input.Keys.S) ? -1 : 0);
  }

  @Override
  public float lookX() {
    return 1 - 1 / ((Gdx.input.getX() - HuldraGame.X_PIXELS / 2) / (float) HuldraGame.X_PIXELS
                    + 1);
  }

  @Override
  public float lookY() {
    return 1 - 1 / ((Gdx.input.getY() - HuldraGame.Y_PIXELS / 2) / (float) HuldraGame.Y_PIXELS
                    + 1);
  }

  @Override
  protected boolean getIsDown(int key) {
    return Gdx.input.isKeyPressed(key);
  }

  @Override
  public boolean getQuickSelect1() {
    return Gdx.input.isKeyPressed(Input.Keys.NUM_1);
  }

  @Override
  public boolean getQuickSelect2() {
    return Gdx.input.isKeyPressed(Input.Keys.NUM_2);
  }

  @Override
  public boolean getQuickSelect3() {
    return Gdx.input.isKeyPressed(Input.Keys.NUM_3);
  }

  @Override
  public boolean getQuickSelect4() {
    return Gdx.input.isKeyPressed(Input.Keys.NUM_4);
  }
}
