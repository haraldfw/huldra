package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.graphics.Texture;
import com.polarbirds.huldra.model.entity.animation.AAnimation;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.io.BufferedReader;
import java.io.File;
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
  private Map<String, AAnimation> loadedAnimations;

  @Override
  public void run() {
    loadedSprites = new HashMap<>();

    double progressIncrement = 1.0 / paths.size();
    for (String path : paths) {
      if (path.contains(".anim")) {
        loadAnimation(path);
      } else {
        loadSprite(path);
      }
    }

    paths.clear();
    isDone = true;
  }

  private void loadSprite(String path) {
    if (!loadedSprites.containsKey(path)) {
      loadedSprites.put(path, new Sprite(new Texture(path), parseGraphicsDescriptor(path)));
    }
  }

  private void loadAnimation(String path) {
    Texture texture = new Texture(path.substring(0, path.length() - 5) + ".png");
    int i = 0;
    while (true) {
      File file = new File(
          path.substring(path.lastIndexOf("\\"), path.length() - 5) + i);
      if (file.isFile() && file.canWrite() && file.length() > 0) {
        // File exists
        try {
          BufferedReader reader = new BufferedReader(new FileReader(file));
          ArrayList<Sprite> sprites = new ArrayList<>();

          int width = Integer.parseInt(reader.readLine());
          int height = Integer.parseInt(reader.readLine());

          ArrayList<Vector2> shifts = parseShifts(texture.getWidth()/width*(texture.getHeight()/height), reader);

          if (texture.getWidth() == width && texture.getHeight() == height) {
            parseStaticAnimation(texture, shifts);
          } else if (reader.readLine() == null) {
            parseManualAnimation(width, height, texture, shifts);
          } else {
            float frameTime = Float.parseFloat(reader.readLine());
            if(Integer.parseInt(reader.readLine()) == 0) {

            }
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        break;
      }
    }
  }

  private ArrayList<Vector2> parseShifts(int frames, BufferedReader reader) {
    ArrayList<Vector2> shifts = new ArrayList<>();

    return shifts;
  }

  private void parseStaticAnimation(Texture texture, ArrayList<Vector2> shifts) {

  }

  private void parseManualAnimation(int width, int height, Texture texture, ArrayList<Vector2> shifts) {

  }

  private void parseSimpleAnimation(int width, int height, Texture texture, ArrayList<Vector2> shifts) {

  }

  private void parseAdvancedAnimation(int width, int height, Texture texture, BufferedReader reader) {

  }

  public void queueAsset(String toAdd) {
    paths.add(toAdd);
  }

  public void queueAssets(String[] toAdd) {
    for (String path : paths) {
      paths.add(path);
    }
  }

  public Sprite getSprite(String path) {
    return loadedSprites.get(path);
  }

  public AAnimation getAnimation(String path) {
    return loadedAnimations.get(path);
  }

  /**
   * Returns progress in parts per one.
   *
   * @return Progress as a double
   */
  @Override
  public double getProgress() {
    double progress = (double) loadedSprites.size() / paths.size();
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

  private enum loadEnum {
    KNIGHT(new String[]{"graphics/player/knight/walk.anim",
                        "graphics/player/knight/idle.anim",
                        "graphics/player/knight/slash.anim",
                        "graphics/player/knight/dance.anim",
                        "graphics/player/knight/jump.anim"}
    ),;

    public final String[] loadString;

    loadEnum(String[] loadString) {
      this.loadString = loadString;
    }
  }

}
