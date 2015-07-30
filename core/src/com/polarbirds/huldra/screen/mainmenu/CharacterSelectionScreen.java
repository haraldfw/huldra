package com.polarbirds.huldra.screen.mainmenu;

import com.badlogic.gdx.Screen;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.player.Knight;
import com.polarbirds.huldra.model.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.generation.LevelParser;
import com.polarbirds.huldra.model.world.generation.WorldGenerator;
import com.polarbirds.huldra.screen.game.GameLoadingScreen;
import com.polarbirds.huldra.screen.game.GameScreen;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Harald on 23.07.2015.
 */
public class CharacterSelectionScreen implements Screen {

  private final HuldraGame game;

  private final ArrayList<PlayerCharacter> playerList;

  public CharacterSelectionScreen(HuldraGame game) {
    this.game = game;
    playerList = new ArrayList<>();
    playerList.add(new Knight(Team.PLAYER));
  }

  @Override
  public void render(float delta) {
    startGame();
  }

  public void startGame() {
    SpriteLoader spriteLoader = new SpriteLoader();
    game.setScreen(new GameLoadingScreen(
        game, new GameScreen(game, playerList.toArray(new PlayerCharacter[playerList.size()])),
        new WorldGenerator(new LevelParser(1, spriteLoader), new Random(6)),
        spriteLoader
    ));
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
