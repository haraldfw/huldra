package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.entity.character.Team;
import com.polarbirds.huldra.model.entity.character.player.Knight;
import com.polarbirds.huldra.model.entity.character.player.PlayerCharacter;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.HuldraWorld;
import com.polarbirds.huldra.model.world.WorldType;

import java.util.Random;

/**
 * A screen for showing the game's world and all it's components. Created by Harald on 30.4.15.
 */
public class GameScreen implements Screen {

    public final HuldraGame game;
    public Stage stage; // stage containing game actors
    public HuldraWorld world;
    public SpriteLoader spriteLoader;
    private PlayerCharacter player;
    private ShapeRenderer sr;

    public GameScreen(HuldraGame game) {
        spriteLoader = new SpriteLoader();
        this.game = game;
        init();
        sr = new ShapeRenderer();
        sr.setProjectionMatrix(game.camera.combined);
    }

    private void init() {
        stage = new Stage(); // create the game stage

        stage.setViewport(new ScreenViewport(game.camera));

        WorldType type = WorldType.CAVES;

        spriteLoader.queueAssets(type.texturePaths);
        world = new HuldraWorld(20, type, new Random());
        player = new Knight(world.spawn, Team.PLAYER, this);
        stage.addActor(player);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        game.camera.position.set(player.body.pos, 0);
        world.integrate(delta);
        stage.draw();
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
