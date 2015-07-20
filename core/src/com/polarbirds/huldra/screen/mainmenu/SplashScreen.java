package com.polarbirds.huldra.screen.mainmenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Screen for displaying a simple image before the mainMenu. Created by Harald on 30.4.15.
 */
public class SplashScreen implements Screen {

    private Texture texture = new Texture("splashimg.png");
    private Image image = new Image(texture);
    private Stage stage = new Stage();

    @Override
    public void show() {
        image.setX(stage.getWidth() / 2f - image.getWidth() / 2f);
        image.setY(stage.getHeight() / 2f - image.getHeight() / 2f);

        stage.addActor(image);

        image.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.5f),
                Actions.delay(2), Actions.run(
                        new Runnable() {
                            @Override
                            public void run() {
                                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
                            }
                        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
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
}
