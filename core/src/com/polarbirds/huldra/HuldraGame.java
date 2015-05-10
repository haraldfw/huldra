package com.polarbirds.huldra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.polarbirds.huldra.screen.game.GameScreen;
import com.polarbirds.huldra.screen.mainmenu.SplashScreen;

public class HuldraGame extends Game {

  public static final int X_TILES = 32;
  public static final int Y_TILES = 18;
  public static final int PIXELS_PER_UNIT = 16;
  public static final int X_PIXELS = X_TILES *50;
  public static final int Y_PIXELS = Y_TILES *50;

  public OrthographicCamera camera;
  public float timeStep;

  public static final AssetManager assetManager = new AssetManager();

  @Override
  public void create () {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, X_TILES, Y_TILES);

    timeStep = 0.01666666666666666666666666666667f; // 1/60, 60fps
    setScreen(new GameScreen(this));
  }

  @Override
  public void render() {
    if(screen != null) screen.render(timeStep);
  }
}
