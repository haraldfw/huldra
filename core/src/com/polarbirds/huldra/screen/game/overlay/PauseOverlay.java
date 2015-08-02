package com.polarbirds.huldra.screen.game.overlay;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 21.07.2015.
 */
public class PauseOverlay implements IOverlay {

  private GameScreen gameScreen;

  public PauseOverlay(GameScreen gameScreen) {
    this.gameScreen = gameScreen;
  }

  @Override
  public void render(Batch batch) {

  }

  @Override
  public void init() {

  }
}
