package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.polarbirds.huldra.model.world.physics.Vector2;

/**
 * Created by Harald on 14.7.15.
 */
public class RegionSprite extends ASprite {

  private final TextureRegion textureRegion;

  public RegionSprite(TextureRegion textureRegion, Vector2 shift) {
    super(shift);
    this.textureRegion = textureRegion;
  }

  @Override
  public void draw(Batch sb, Vector2 pos) {
    sb.draw(textureRegion, pos.x + shift.x, pos.y + shift.y);
  }
}
