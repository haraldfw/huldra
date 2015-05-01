package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * A screen for showing the game
 * Created by Harald on 30.4.15.
 */
public class GameScreen implements Screen {

  final Game game;

  public GameScreen(Game game) {
    this.game = game;
  }

  @Override
  public void render(float delta) {
    System.out.println("Render in GameScreen");
  }

  @Override
  public void show() {

  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }
}
