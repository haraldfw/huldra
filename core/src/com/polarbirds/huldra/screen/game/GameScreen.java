package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.controller.player.Keyboard;
import com.polarbirds.huldra.model.entity.player.Knight;
import com.polarbirds.huldra.model.entity.player.PlayerCharacter;
import com.polarbirds.huldra.model.world.HuldraWorld;
import com.polarbirds.huldra.model.world.WorldType;

import java.util.Random;

/**
 * A screen for showing the game's box2dWorld and all it's components. Created by Harald on
 * 30.4.15.
 */
public class GameScreen implements Screen {

  public final HuldraGame game;
  public Stage stage; // stage containing game actors
  private final Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
  public HuldraWorld world;
  private PlayerCharacter player;

  public GameScreen(HuldraGame game) {
    this.game = game;
    init();
  }

  private void init() {
    stage = new Stage(); // create the game stage

    stage.setViewport(new ScreenViewport(game.camera));

    world = WorldType.CAVES.getNew(1, 50, new Random(), game.camera);
    player = new Knight(this, world.spawn,
                        new Keyboard(game.camera));
    stage.addActor(player);
  }

  @Override
  public void render(float delta) {
    stage.act(delta);
    world.step(delta);
    game.camera.position.set(player.getPosition(), 0);
    stage.draw();
    debugRenderer.render(world.box2dWorld, game.camera.combined);
    if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
      init();
    }
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
