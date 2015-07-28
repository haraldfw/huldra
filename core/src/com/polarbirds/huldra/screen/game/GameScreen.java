package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.animation.AAnimation;
import com.polarbirds.huldra.model.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.utility.ASprite;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.Level;
import com.polarbirds.huldra.model.world.LevelFile;
import com.polarbirds.huldra.model.world.WorldGenerator;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.overlay.HudOverlay;
import com.polarbirds.huldra.screen.game.overlay.IOverlay;
import com.polarbirds.huldra.screen.game.overlay.PauseOverlay;
import com.polarbirds.huldra.screen.game.overlay.PlayerSpecOverlay;

import java.util.Map;
import java.util.Random;

/**
 * A screen for showing the game's world and all it's components. Created by Harald on 30.4.15.
 */
public class GameScreen implements Screen {

    public final HuldraGame game;
    private final OrthographicCamera gameCamera;
    public Level level;
    public Map<String, ASprite> sprites;
    public Map<String, AAnimation> animations;
    private State state;
    private Stage gameStage;         // stage containing game actors

    private IOverlay hudOverlay;        // Overlay to display player stats
    private IOverlay playerSpecOverlay; // Menu to display when a player
    private IOverlay pauseOverlay;      // Menu to display when the game is paused

    private ShapeRenderer sr;

    public GameScreen(HuldraGame game, PlayerCharacter[] players) {
        this.game = game;

        gameCamera = new OrthographicCamera(HuldraGame.X_TILES, HuldraGame.Y_TILES);

        hudOverlay = new HudOverlay(players);
        playerSpecOverlay = new PlayerSpecOverlay(this);
        pauseOverlay = new PauseOverlay(this);

        gameStage = new Stage(new ScreenViewport(gameCamera), game.batch);

        game.batch.setProjectionMatrix(gameCamera.combined);

        level = new Level(players);

        state = State.PRESPAWN;

        sr = new ShapeRenderer();
        sr.setProjectionMatrix(gameCamera.combined);
    }

    public void setNew(Map<String, ASprite> sprites,
                       Map<String, AAnimation> animations) {
        this.sprites = sprites;
        this.animations = animations;
    }

    private void startLevelTransition() {
        SpriteLoader spriteLoader = new SpriteLoader();
        game.setScreen(
            new GameLoadingScreen(
                game,
                this,
                new WorldGenerator(
                    new LevelFile(
                        level.difficulty + 1,
                        spriteLoader
                    ),
                    new Random(6)
                ),
                spriteLoader
            )
        );
    }

    @Override
    public void render(float delta) {
        gameStage.act(delta);

        updateCamera(gameCamera);

        level.draw(game.batch);

        if (state == State.PRESPAWN) {
            for (PlayerCharacter player : level.players) {
                player.setPosition(level.spawn.x, level.spawn.y);
            }
            level.integrate(delta);
            gameStage.draw();
            hudOverlay.render(game.batch);
        } else if (state == State.RUNNING) {
            level.integrate(delta);
            gameStage.draw();
            hudOverlay.render(game.batch);
        } else {
            gameStage.draw();
            switch (state) {
                case PLAYERSPEC:
                    playerSpecOverlay.render(game.batch);
                    break;
                case PAUSED:
                    pauseOverlay.render(game.batch);
                    break;
            }
        }

        sr.setAutoShapeType(true);
        sr.begin();
        level.debugDraw(sr);
        sr.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        state = State.PAUSED;
    }

    @Override
    public void resume() {
        state = State.RUNNING;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void updateCamera(OrthographicCamera camera) {
        // tan( 1/2 * field_of_view ) * ( 1/2 * distance_between_objects)
        PlayerCharacter[] players = level.players;
        if (players.length == 1) {
            Vector2 pos = players[0].body.pos;
            camera.position.set(pos.x, pos.y, 0);
            camera.zoom = 1;
            camera.update();
            return;
        }

        Vector3 vector = new Vector3();
        for (PlayerCharacter player : players) {
            Vector2 pos = player.body.pos;
            vector.add(pos.x, pos.y, 0);
        }
        vector.scl(1f / players.length);
    }

    public void openPlayerSpec() {
        state = State.PLAYERSPEC;
    }

    private enum State {
        PRESPAWN,
        RUNNING,
        PLAYERSPEC,
        PAUSED
    }
}
