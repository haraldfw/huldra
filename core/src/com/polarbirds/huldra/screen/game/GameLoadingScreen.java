package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.character.animate.player.PlayerLoading;
import com.polarbirds.huldra.model.character.stat.StatLoader;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.generation.WorldGenerator;

/**
 * Created by Harald on 23.07.2015.
 */
public class GameLoadingScreen implements Screen {

  private final HuldraGame game;
  private final GameScreen gameScreen;

  private final WorldGenerator worldGenerator;
  private final SpriteLoader spriteLoader;
  private final StatLoader statLoader;

  public GameLoadingScreen(HuldraGame game, GameScreen gameScreen, WorldGenerator worldGenerator,
                           SpriteLoader spriteLoader, StatLoader statLoader) {
    this.game = game;
    this.gameScreen = gameScreen;
    this.worldGenerator = worldGenerator;
    this.spriteLoader = spriteLoader;
    this.statLoader = statLoader;

    worldGenerator.startThread();
    spriteLoader.startThread();
    statLoader.startThread();
  }

  public GameLoadingScreen(HuldraGame game, GameScreen gameScreen, WorldGenerator worldGenerator,
                           SpriteLoader spriteLoader, StatLoader statLoader,
                           PlayerLoading playerLoading) {
    this(game, gameScreen, worldGenerator, spriteLoader, statLoader);
  }

  @Override
  public void render(float delta) {
    int loaded = spriteLoader.loaded + worldGenerator.loaded + statLoader.loaded;
    int max = spriteLoader.max + worldGenerator.max + statLoader.max;
    System.out.println("Loaded: " + loaded + "/" + max);

    if (worldGenerator.done) {
      worldGenerator.placeTextures();
    }

    if (spriteLoader.done) {
      spriteLoader.finish();
    }

    if (worldGenerator.done && spriteLoader.done && statLoader.done) {
      gameScreen.level.setNew(worldGenerator.tiles, worldGenerator.spawn,
                              gameScreen.level.difficulty + 1);
      gameScreen.setNew(spriteLoader.loadedSprites, spriteLoader.loadedAnimations,
                        statLoader.getHandler());
      game.setScreen(gameScreen);
    }
  }

  @Override
  public void dispose() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void pause() {

  }

  @Override
  public void resize(int width, int height) {

  }

  @Override
  public void show() {

  }
}
