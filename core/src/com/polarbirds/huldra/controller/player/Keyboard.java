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

  @Override
  public float moveX() {
    float left = Gdx.input.isKeyPressed(Input.Keys.A) ? -1 : 0;
    float right = Gdx.input.isKeyPressed(Input.Keys.D) ? 1 : 0;
    return left + right;
  }

  @Override
  public float moveY() {
    float up = Gdx.input.isKeyPressed(Input.Keys.W) ? 1 : 0;
    float down = Gdx.input.isKeyPressed(Input.Keys.S) ? -1 : 0;
    return up + down;
  }

  @Override
  public float lookX() {
    return Gdx.input.getX() / (float) HuldraGame.X_PIXELS - camera.position.x;
  }

  @Override
  public float lookY() {
    return Gdx.input.getY() / (float) HuldraGame.Y_PIXELS - camera.position.y;
  }

  @Override
  protected boolean getPressed(int key) {
    return Gdx.input.isKeyPressed(key);
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
}
