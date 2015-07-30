package com.polarbirds.huldra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polarbirds.huldra.screen.mainmenu.CharacterSelectionScreen;

public class HuldraGame extends Game {

  public static final int X_TILES = 32;
  public static final int Y_TILES = 18;
  public static final int PIXELS_PER_TILESIDE = 50;
  public static final int X_PIXELS = X_TILES * PIXELS_PER_TILESIDE;
  public static final int Y_PIXELS = Y_TILES * PIXELS_PER_TILESIDE;

  public Batch batch;
  public OrthographicCamera staticViewCamera;
  public float timeStep;

  @Override
  public void create() {
    batch = new SpriteBatch();
    staticViewCamera = new OrthographicCamera();
    staticViewCamera.setToOrtho(false, X_TILES, Y_TILES);
    staticViewCamera.position.set(X_TILES / 2, Y_TILES / 2, 0);

    timeStep = 0.01666666666666666666666666666667f; // 1/60, 60fps
    setScreen(new CharacterSelectionScreen(this));
  }

  @Override
  public void render() {
    Gdx.graphics.getGL30().glClearColor(0.1f, 0.1f, 0.1f, 1);
    Gdx.graphics.getGL30().glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

    if (screen != null) {
      screen.render(timeStep);
    }

    if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
      staticViewCamera.zoom++;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.X)) {
      staticViewCamera.zoom--;
    }
    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
      staticViewCamera.position.add(-1, 0, 0);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
      staticViewCamera.position.add(1, 0, 0);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
      staticViewCamera.position.add(0, 1, 0);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
      staticViewCamera.position.add(0, -1, 0);
    }
  }

  @Override
  public void dispose() {
    super.dispose();
    batch.dispose();
  }
}
