package com.polarbirds.huldra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.polarbirds.huldra.screen.game.GameScreen;

public class HuldraGame extends Game {

  public static final int X_TILES = 32;
  public static final int Y_TILES = 18;
  public static final int PIXELS_PER_TILESIDE = 16;
  public static final int X_PIXELS = X_TILES * 50;
  public static final int Y_PIXELS = Y_TILES * 50;

  public OrthographicCamera camera;
  public float timeStep;

  public static final AssetManager assetManager = new AssetManager();

  @Override
  public void create () {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, X_TILES, Y_TILES);
    camera.position.set(0, 0, 0);

    timeStep = 0.01666666666666666666666666666667f; // 1/60, 60fps
    setScreen(new GameScreen(this));
  }

  @Override
  public void render() {
    Gdx.graphics.getGL30().glClearColor(0.1f, 0.1f, 0.1f, 1);
    Gdx.graphics.getGL30().glClear( GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT );


    if(screen != null) screen.render(timeStep);

    if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
      camera.zoom++;
    }
    if(Gdx.input.isKeyPressed(Input.Keys.X)) {
      camera.zoom--;
    }
    if(Gdx.input.isKeyPressed(Input.Keys.A)) {
      camera.position.add(-1, 0, 0);
    }
    if(Gdx.input.isKeyPressed(Input.Keys.D)) {
      camera.position.add(1, 0, 0);
    }
    if(Gdx.input.isKeyPressed(Input.Keys.W)) {
      camera.position.add(0, 1, 0);
    }
    if(Gdx.input.isKeyPressed(Input.Keys.S)) {
      camera.position.add(0, -1, 0);
    }
  }
}
