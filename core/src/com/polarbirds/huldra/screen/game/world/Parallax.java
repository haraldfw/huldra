package com.polarbirds.huldra.screen.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * A class for drawing a parallax of the given layers.
 * Created by Harald on 8.5.15.
 */
public class Parallax extends Actor {

  private final Image[] images;
  private final float[] dividers;
  private final OrthographicCamera camera;

  public Parallax(OrthographicCamera camera, Image[] images, float[] dividers) {
    this.camera = camera;

    if(images.length != dividers.length*2) throw new IllegalArgumentException(
        "images.length != dividers.length*2");

    this.images = images;
    this.dividers = dividers;
  }

  @Override
  public void draw(Batch batch, float parentAlpha) {
    Vector3 camPos = camera.position;

    for(int i = 0; i < images.length; i++) {
      Image image = images[i];
      image.setPosition(camPos.x/dividers[i*2], camPos.y/dividers[i*2 + 1]);
      image.draw(batch, parentAlpha);
    }
  }
}
