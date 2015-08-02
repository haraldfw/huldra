package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.animate.player.APlayerCharacter;
import com.polarbirds.huldra.model.character.animate.player.Knight;
import com.polarbirds.huldra.model.character.stat.StatLoader;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.generation.WorldGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harald on 23.07.2015.
 */
public class GameLoadingScreen implements Screen {

  private final HuldraGame game;
  private final GameScreen gameScreen;

  private final WorldGenerator worldGenerator;
  private final SpriteLoader spriteLoader;
  private final StatLoader statLoader;

  private String[] playersToCreate;

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

  public GameLoadingScreen(String[] playersToCreate, HuldraGame game, GameScreen gameScreen,
                           WorldGenerator worldGenerator, SpriteLoader spriteLoader,
                           StatLoader statLoader) {
    this(game, gameScreen, worldGenerator, spriteLoader, statLoader);
    this.playersToCreate = playersToCreate;
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
      nextScreen();
    }
  }

  private void nextScreen() {
    gameScreen.setNew(spriteLoader.loadedSprites, spriteLoader.loadedAnimations,
                      statLoader.getHandler());

    gameScreen.level.setNew(worldGenerator.tiles, worldGenerator.spawn,
                            gameScreen.level.difficulty + 1);
    game.setScreen(gameScreen);
    if (playersToCreate != null) {
      List<APlayerCharacter> players = new ArrayList<>();
      for (String s : playersToCreate) {
        switch (s) {
          case Knight.CHARACTER_NAME:
            players.add(new Knight(gameScreen, Team.PLAYER));
            break;
        }
      }
      gameScreen.level.setPlayers(players.toArray(new APlayerCharacter[players.size()]));
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
