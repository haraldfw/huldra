package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.screen.game.world.HuldraWorld;

/**
 * A screen for showing the game's world and all it's components.
 * Created by Harald on 30.4.15.
 */
public class GameScreen implements Screen {

  private final HuldraGame game;
  private final HuldraWorld world;
  public final Stage stage; // stage containing game actors (not GUI, but actual game elements)

  public GameScreen(HuldraGame game) {
    this.game = game;
    stage = new Stage(); // create the game stage

    stage.setViewport(new ScreenViewport(game.camera));

    world = HuldraWorld.Generate.TEST_STAGE.generate("");
  }

  @Override
  public void render(float delta) {
    System.out.println("Render in GameScreen");


    stage.act(delta);
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
