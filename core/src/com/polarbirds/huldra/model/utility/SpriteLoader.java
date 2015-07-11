package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.graphics.Texture;
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
public class SpriteLoader extends ALoader {

  public boolean isDone = false;
  private double progress = 0;
  private Collection<String> paths = new ArrayList<>();
  private Map<String, Sprite> loadedSprites;

  @Override
  public void run() {
    loadedSprites = new HashMap<>();

    double progressIncrement = 1.0 / paths.size();
    for (String path : paths) {
      if (!loadedSprites.containsKey(path)) {
        loadedSprites.put(path, new Sprite(new Texture(path), parseGraphicsDescriptor(path)));
      }
    }

    paths.clear();
    isDone = true;
  }

  public void queueAsset(String path) {
    paths.add(path);
  }

  public Sprite getSprite(String path) {
    return loadedSprites.get(path);
  }

  @Override
  public double getProgress() {
    double progress = loadedSprites.size() / paths.size();
    System.out.println(
        "Texture-loading progress: " + loadedSprites.size() + "/" + paths.size() + ", "
        + progress * 100 + "%");
    return progress;
  }

  private Vector2 parseGraphicsDescriptor(String path) {
    try {
      String graphicsFile = path.substring(path.length() - 4);
      BufferedReader reader = new BufferedReader(new FileReader(graphicsFile));
      return new Vector2(Float.parseFloat(reader.readLine()), Float.parseFloat(reader.readLine()));
    } catch (Exception e) {
      System.out.println("No descriptor for png '" + path + "' found. Using shift [0, 0]");
    }
    return new Vector2();
  }

}
