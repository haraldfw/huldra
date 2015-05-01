package com.polarbirds.huldra;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.polarbirds.huldra.screen.game.GameScreen;
import com.polarbirds.huldra.screen.mainmenu.SplashScreen;

public class HuldraGame extends Game {

  public SpriteBatch batch;
  float timeStep;

  @Override
  public void create () {
    timeStep = 0.01666666666666666666666666666667f; // 1/60, sixty fps
    batch = new SpriteBatch();
    setScreen(new GameScreen(this));
  }

  @Override
  public void render() {
    if(screen != null) screen.render(timeStep);
  }
}
