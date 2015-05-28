package com.polarbirds.huldra.controller.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.polarbirds.huldra.controller.IMotiveProcessor;

/**
 * Created by Harald on 14.5.15.
 */
public class Keyboard implements IMotiveProcessor {

  @Override
  public void update() {

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
    float left = Gdx.input.isKeyPressed(Input.Keys.LEFT) ? -1 : 0;
    float right = Gdx.input.isKeyPressed(Input.Keys.RIGHT) ? 1 : 0;
    return left + right;
  }

  @Override
  public float lookY() {
    float up = Gdx.input.isKeyPressed(Input.Keys.UP) ? 1 : 0;
    float down = Gdx.input.isKeyPressed(Input.Keys.DOWN) ? -1 : 0;
    return up + down;
  }

  @Override
  public boolean attack1() {
    return false;
  }

  @Override
  public boolean attack2() {
    return false;
  }

  @Override
  public boolean jump() {
    return Gdx.input.isKeyPressed(Input.Keys.SPACE);
  }

  @Override
  public boolean interact() {
    return Gdx.input.isKeyPressed(Input.Keys.E);
  }

  @Override
  public boolean pause() {
    return false;
  }

  @Override
  public boolean toggleMenu() {
    return false;
  }
}
