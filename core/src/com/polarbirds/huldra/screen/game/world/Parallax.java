package com.polarbirds.huldra.screen.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.polarbirds.huldra.HuldraGame;

/**
 * A class for drawing a parallax of the given layers.
 * Created by Harald on 8.5.15.
 */
public class Parallax extends Actor {

  private final Image backGround;
  private final Image midGround;
  private final Image foreGround;
  private final OrthographicCamera camera;

  public Parallax(OrthographicCamera camera, Image backGround, Image midGround, Image foreGround) {
    this.camera = camera;
    this.backGround = backGround;
    this.midGround = midGround;
    this.foreGround = foreGround;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Vector3 camPos = camera.position;

    backGround.setPosition(camPos.x, camPos.y);
    backGround.draw(batch, parentAlpha);

    midGround.setPosition(camPos.x/3, camPos.y);
    midGround.draw(batch, parentAlpha);

    foreGround.setPosition(camPos.x/2, camPos.y);
    foreGround.draw(batch, parentAlpha);
  }
}
