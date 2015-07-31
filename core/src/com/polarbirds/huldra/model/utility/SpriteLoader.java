package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.drawable.RegionDrawable;
import com.polarbirds.huldra.model.drawing.drawable.TextureDrawable;
import com.polarbirds.huldra.model.drawing.multiframe.AdvancedAnimation;
import com.polarbirds.huldra.model.drawing.multiframe.ManualAnimation;
import com.polarbirds.huldra.model.drawing.multiframe.SimpleAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.drawing.singleframe.IHasSingleFrame;
import com.polarbirds.huldra.model.drawing.singleframe.Sprite;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by Harald on 10.7.15.
 */
public class SpriteLoader extends ALoader {

  private final Map<IHasSingleFrame, TextureData> dataMap = new HashMap<>();
  private final Map<String, TextureData> dataArrayMap = new HashMap<>();
  private final Collection<String> paths = new TreeSet<>();
  public final Map<String, ASprite> loadedSprites = new HashMap<>();
  public final Map<String, AAnimation> loadedAnimations = new HashMap<>();

  @Override
  public void run() {
    max = paths.size();

    for (String path : paths) {
      if (path.contains(".json")) {
        loadAnimationData(path);
      } else {
        loadSpriteData(path);
      }
      loaded++;
    }

    done = true;
  }

  private void loadSpriteData(String path) {
    Sprite s = new Sprite(parseGraphicsDescriptor(path));
    loadedSprites.put(path, s);
    dataMap.put(s, TextureData.Factory.loadFromFile(Gdx.files.internal(path), null, false));
  }

  private void loadAnimationData(String path) {
    dataArrayMap.put(path.substring(0, path.length() - 4) + "png",
                     TextureData.Factory.loadFromFile(Gdx.files.internal(path), false));
  }

  public void finish() {
    for (Map.Entry<String, ASprite> entry : loadedSprites.entrySet()) {
      entry.getValue().set(new TextureDrawable(new Texture(dataMap.get(entry.getValue()))));
    }

    for (Map.Entry<String, TextureData> entry : dataArrayMap.entrySet()) {
      parseAnimation(entry.getKey(), new Texture(entry.getValue()));
    }
  }

  private void parseAnimation(String name, Texture texture) {
    JsonValue animJson = new JsonReader().parse(Gdx.files.internal(name));
    List<Vector2> shifts = parseShifts(animJson.get("shifts").asFloatArray());
    int width = animJson.getInt("width");
    int height = animJson.getInt("height");
    ASprite[] regions = splitTexture(width, height, texture, shifts);

    AAnimation animation = null;

    switch (animJson.getString("type")) {
      case "simple":
        float frameTime = animJson.getFloat("frametime");
        animation = new SimpleAnimation(regions, frameTime);
        break;
      case "manual":
        animation = new ManualAnimation(regions);
        break;
      case "advanced":
        float[] frametimes = animJson.get("frametimes").asFloatArray();
        animation = new AdvancedAnimation(regions, frametimes);
        break;
    }

    loadedAnimations.put(name, animation);
  }

  private List<Vector2> parseShifts(float[] floats) {
    List<Vector2> shifts = new ArrayList<>();
    for (int i = 0; i < floats.length; i += 2) {
      shifts.add(new Vector2(floats[i], floats[i + 1]));
    }
    return shifts;
  }

  private ASprite[] splitTexture(int width, int height, Texture texture, List<Vector2> shifts) {
    ArrayList<ASprite> sprites = new ArrayList<>();

    for (int i = 0; i < shifts.size(); i++) {
      sprites.add(
          new Sprite(
              shifts.get(i),
              new RegionDrawable(
                  new TextureRegion(texture, 0, height * i, width, height)
              )
          )
      );
    }

    return sprites.toArray(new ASprite[sprites.size()]);
  }

  public void queueAsset(String toAdd) {
    paths.add(toAdd);
  }

  public ASprite getSprite(String path) {
    return loadedSprites.get(path);
  }

  public AAnimation getAnimation(String path) {
    return loadedAnimations.get(path);
  }

  private Vector2 parseGraphicsDescriptor(String path) {
    try {
      String graphicsFile = path.substring(path.length() - 4);
      BufferedReader reader = new BufferedReader(new FileReader(graphicsFile));
      return new Vector2(Float.parseFloat(reader.readLine()),
                         Float.parseFloat(reader.readLine()));
    } catch (Exception e) {
      System.out.println("No descriptor for png '" + path + "' found. Using shift [0, 0]");
    }
    return new Vector2();
  }
}
