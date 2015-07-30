package com.polarbirds.huldra.screen.game.overlay;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Harald on 21.07.2015.
 */
public interface IOverlay {

  void init();

  void render(Batch batch);

  void queueAssets();
}
