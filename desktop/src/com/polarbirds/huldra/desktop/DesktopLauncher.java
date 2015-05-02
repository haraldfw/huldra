package com.polarbirds.huldra.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.polarbirds.huldra.HuldraGame;

public final class DesktopLauncher {
  public static void main (String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    int scale = 100;
    config.width = HuldraGame.PIXEL_WIDTH;
    config.height = HuldraGame.PIXEL_HEIGHT;
    config.foregroundFPS = 60;
    new LwjglApplication(new HuldraGame(), config);
  }
}
