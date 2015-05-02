package com.polarbirds.huldra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;
import com.smokebox.lib.pcg.dungeon.Dungeon;

public class HuldraGame extends Game {

  public static final int UNIT_WIDTH = 32;
  public static final int UNIT_HEIGHTH = 18;
  public static final int PIXEL_WIDTH = UNIT_WIDTH*50;
  public static final int PIXEL_HEIGHT = UNIT_HEIGHTH*50;

  public OrthographicCamera camera;
  public Vector2 mousePos = new Vector2();
  public float timeStep;

  @Override
  public void create () {
    camera = new OrthographicCamera();
    camera.setToOrtho(false, UNIT_WIDTH, UNIT_HEIGHTH);

    timeStep = 0.01666666666666666666666666666667f; // 1/60, 60fps
    setScreen(new GameScreen(this));
  }

  @Override
  public void render() {
    updateMouse();
    if(screen != null) screen.render(timeStep);
  }

  private void updateMouse() {
    mousePos.set(Gdx.input.getX(), Gdx.input.getY());
  }
}
