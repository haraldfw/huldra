package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.entity.character.Team;
import com.polarbirds.huldra.model.entity.character.player.Knight;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.HuldraWorld;
import com.polarbirds.huldra.model.world.WorldType;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * A screen for showing the game's world and all it's components. Created by Harald on 30.4.15.
 */
public class GameScreen implements Screen {

    private State state;
    private Stage gameStage;       // stage containing game actors
    private Stage hudStage;        // stage containing hud-elements
    private Stage pauseStage;      // stage containing pause-menu
    private Stage playerMenuStage; // stage containing menu for player-speccing

    private PauseOverlay pauseMenu;           // Menu to display when the game is paused
    private PlayerSpecOverlay playerSpecMenu; // Menu to display when a player

    public final HuldraGame game;

    public HuldraWorld world;
    public SpriteLoader spriteLoader;
    private PlayerCharacter player;
    private ShapeRenderer sr;

    public GameScreen(HuldraGame game) {

        pauseMenu = new PauseOverlay(this);

        spriteLoader = new SpriteLoader();
        this.game = game;
        init();
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(game.camera.combined);
    }

    private void init() {
        state = State.RUNNING;
        gameStage = new Stage(); // create the game stage

        gameStage.setViewport(new ScreenViewport(game.camera));

        WorldType type = WorldType.CAVES;

        spriteLoader.queueAssets(type.texturePaths);
        world = new HuldraWorld(20, type, new Random());
        player = new Knight(world.spawn, Team.PLAYER, this);
        gameStage.addActor(player);
    }

    @Override
    public void render(float delta) {
        gameStage.act(delta);
        game.camera.position.set(player.body.pos, 0);
        if(state == State.RUNNING) {
            world.integrate(delta);
            gameStage.draw();
        } else {
            gameStage.draw();
            switch (state) {
                case PLAYERSPEC:
                    break;
                case PAUSED:
                    break;
            }
        }
        sr.setAutoShapeType(true);
        sr.begin();
        world.debugDraw(sr);
        sr.end();
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

    private Vector3 updateCamera(OrthographicCamera camera) {
        // tan( 1/2 * field_of_view ) * ( 1/2 * distance_between_objects)
        ArrayList<PlayerCharacter> players = world.level.players;
        if (players.size() == 1) {
            Vector2 pos = players.get(0).body.pos;
            camera.position.set(pos.x, pos.y, 0);
            camera.zoom = 1;
            camera.update();
        }

        Vector3 vector = new Vector3();
        for (PlayerCharacter player : players) {
            Vector2 pos = player.body.pos;
            vector.add(pos.x, pos.y, 0);
        }
        vector.scl(1f / players.size());


        return vector;
    }

    public void openPlayerSpec() {
        state = State.PLAYERSPEC;
    }

    private enum State {
        RUNNING,
        PLAYERSPEC,
        PAUSED
    }
}
