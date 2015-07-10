package com.polarbirds.huldra.screen.game;

import com.badlogic.gdx.graphics.Texture;
import com.polarbirds.huldra.model.utilities.Sprite;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald on 10.7.15.
 */
public class AssetManager implements Runnable {

  private double progress = 0;
  private Collection<String> paths = new ArrayList<>();
  private Map<String, Sprite> loadedSprites;

  @Override
  public void run() {
    loadedSprites = new HashMap<>();

    double progressIncrement = 1.0 / (double) paths.size();
    for (String path : paths) {
      if (!loadedSprites.containsKey(path)) {
        loadedSprites.put(path, new Sprite(new Texture(path), parseGraphicsDescriptor(path)));
      }
      progress += progressIncrement;
    }

    progress = 0;
    paths.clear();
  }

  public void startLoading() {
    Thread t = new Thread(this);
    t.start();
  }

  public void queueAsset(String path) {
    paths.add(path);
  }

  public Sprite getSprite(String path) {
    return loadedSprites.get(path);
  }

  public double getProgress() {
    return progress;
  }

  public boolean isDone() {
    return progress >= 1.0;
  }

  private Vector2 parseGraphicsDescriptor(String path) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(path));
      return new Vector2(Float.parseFloat(reader.readLine()), Float.parseFloat(reader.readLine()));
    } catch (Exception e) {
      System.out.println("No file of name '" + path + "' found. Using shift [0, 0]");
    }
    return new Vector2();
  }

}
