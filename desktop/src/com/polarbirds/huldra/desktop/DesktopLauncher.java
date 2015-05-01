package com.polarbirds.huldra.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.polarbirds.huldra.HuldraGame;

public final class DesktopLauncher {
  public static void main (String[] arg) {
    LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
    int scale = 100;
    config.width = 16*scale;
    config.height = 9*scale;
    config.foregroundFPS = 60;
    new LwjglApplication(new HuldraGame(), config);
  }
}
