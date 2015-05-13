package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.world.HuldraWorld;

import java.util.Random;

/**
 * A screen for showing the game's box2dWorld and all it's components.
 * Created by Harald on 30.4.15.
 */
public class GameScreen implements Screen {

  public final HuldraGame game;
  public final HuldraWorld world;
  private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
  public final Stage stage; // stage containing game actors

  public GameScreen(HuldraGame game) {
    this.game = game;
    stage = new Stage(); // create the game stage

    stage.setViewport(new ScreenViewport(game.camera));

    world = HuldraWorld.WorldTypes.TEST_STAGE.getNew(10, new Random().nextLong(), game.camera);

    //stage.addActor(new Parallax());
  }

  @Override
  public void render(float delta) {
    stage.act(delta);
    world.step(delta);
    stage.draw();
    debugRenderer.render(world.box2dWorld, game.camera.combined);
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
