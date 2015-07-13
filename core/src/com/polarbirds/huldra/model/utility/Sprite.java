package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 10.7.15.
 */
public class Sprite {

  public final Texture texture;
  public final Vector2 shift;

  public Sprite(Texture texture, Vector2 shift) {
    this.texture = texture;
    this.shift = shift;
  }

  public void draw(Batch sb, Vector2 pos) {
    sb.draw(texture, pos.x + shift.x, pos.y + shift.y);
  }
}
