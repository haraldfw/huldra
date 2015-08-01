package com.polarbirds.huldra.screen.mainmenu;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.character.animate.player.APlayerCharacter;
import com.polarbirds.huldra.model.character.stat.StatLoader;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.generation.LevelParser;
import com.polarbirds.huldra.model.world.generation.WorldGenerator;
import com.polarbirds.huldra.screen.game.GameLoadingScreen;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Harald on 23.07.2015.
 */
public class CharacterSelectionScreen implements Screen {

  private final HuldraGame game;

  private final ArrayList<String> playerList;

  public CharacterSelectionScreen(HuldraGame game) {
    this.game = game;
    playerList = new ArrayList<>();
    playerList.add("knight");
  }

  @Override
  public void render(float delta) {
    startGame();
  }

  public void startGame() {
    SpriteLoader spriteLoader = new SpriteLoader();
    LevelParser levelParser = new LevelParser(1, spriteLoader);

    List<String> paths = new ArrayList<>();
    paths.addAll(Arrays.asList(levelParser.enemyTypes));
    paths.addAll(playerList);

    game.setScreen(
        new GameLoadingScreen(
            game,
            new GameScreen(
                game,
                new APlayerCharacter[0]
            ),
            new WorldGenerator(
                levelParser,
                new Random(6)
            ),
            spriteLoader,
            new StatLoader(paths.toArray(new String[paths.size()])))
        );
  }

  @Override
  public void dispose() {

  }

  @Override
  public void show() {

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
}
