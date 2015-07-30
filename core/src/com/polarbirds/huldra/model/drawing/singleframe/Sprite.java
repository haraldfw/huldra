package com.polarbirds.huldra.model.drawing.singleframe;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.drawing.drawable.Drawable;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 10.7.15.
 */
public class Sprite extends ASprite {

  public Drawable drawable;

  public Sprite(Vector2 shift) {
    super(shift);
    this.shift = shift;
  }

  public Sprite(Vector2 shift, Drawable drawable) {
    super(shift);
    this.drawable = drawable;
  }

  @Override
  public void draw(Batch batch, Vector2 pos) {
    drawable.draw(batch, pos.x + shift.x, pos.y + shift.y);
  }

  @Override
  public void set(Drawable drawable) {
    this.drawable = drawable;
  }
}
