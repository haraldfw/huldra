package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.polarbirds.huldra.model.character.animate.player.APlayerCharacter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Harald on 10.7.15.
 */
public class SpriteLoader extends ALoader {

  public final Map<String, ASprite> loadedSprites = new HashMap<>();
  public final Map<String, AAnimation> loadedAnimations = new HashMap<>();
  private final Map<IHasSingleFrame, TextureData> dataMap = new HashMap<>();
  private final Map<String, TextureData> dataArrayMap = new HashMap<>();
  private final List<FileHandle> fileHandles = new ArrayList<>();

  private final String[] enemyNames;
  private final String[] playerNames;

  public SpriteLoader(String[] enemyNames, String[] playerNames) {
    this.enemyNames = enemyNames;
    this.playerNames = playerNames;
  }

  public SpriteLoader(String[] enemyNames, APlayerCharacter[] players) {
    this.enemyNames = enemyNames;
    String[] playerNames = new String[players.length];
    for (int i = 0; i < players.length; i++) {
      playerNames[i] = players[i].getCharacterName();
    }
    this.playerNames = playerNames;
  }

  @Override
  public void run() {
    max = enemyNames.length + playerNames.length;

    for (String enemyName : enemyNames) {
      generatePaths(Gdx.files.internal("graphics/enemy/" + enemyName));

    }
    for (String playerName : playerNames) {
      generatePaths(Gdx.files.internal("graphics/player/" + playerName));
    }

    max = fileHandles.size();

    for (FileHandle fileHandle : fileHandles) {
      if (fileHandle.extension().contains("json")) {
        loadAnimationData(new FileHandle(fileHandle.pathWithoutExtension() + ".png"));
      } else {
        loadSpriteData(fileHandle);
      }
      loaded++;
    }

    done = true;
  }

  private void generatePaths(FileHandle dir) {
    List<FileHandle> dirContents = Arrays.asList(dir.list());
    for (FileHandle fileHandle : dirContents) {
      if (fileHandle.extension().contains("png") &&
          !fileListContainsPath(dirContents, fileHandle.pathWithoutExtension() + ".json")) {
        // A json-file does not exist for this PNG file, which means it is not an animation
        fileHandles.add(fileHandle);
      } else if (fileHandle.extension().contains("json")) {
        fileHandles.add(fileHandle);
      }
    }
  }

  private boolean fileListContainsPath(Iterable<FileHandle> list, String checkFor) {
    for (FileHandle fileHandle : list) {
      if (fileHandle.path().equals(checkFor)) {
        return true;
      }
    }
    return false;
  }

  private void loadSpriteData(FileHandle fileHandle) {
    Sprite s = new Sprite(parseGraphicsDescriptor(fileHandle));
    loadedSprites.put(fileHandle.nameWithoutExtension(), s);
    dataMap.put(s, TextureData.Factory.loadFromFile(fileHandle, false));
  }

  private void loadAnimationData(FileHandle fileHandle) {
    dataArrayMap.put(fileHandle.path(),
                     TextureData.Factory.loadFromFile(fileHandle, false));
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
    FileHandle f = Gdx.files.internal(name.substring(0, name.length() - 3) + "json");
    JsonValue animJson = new JsonReader().parse(f);
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

  public ASprite getSprite(String path) {
    return loadedSprites.get(path);
  }

  public AAnimation getAnimation(String path) {
    return loadedAnimations.get(path);
  }

  private Vector2 parseGraphicsDescriptor(FileHandle fileHandle) {
    try {
      String graphicsFile = fileHandle.pathWithoutExtension();
      BufferedReader reader = new BufferedReader(new FileReader(graphicsFile));
      return new Vector2(Float.parseFloat(reader.readLine()),
                         Float.parseFloat(reader.readLine()));
    } catch (Exception e) {
      System.out.println("No descriptor for png '" + fileHandle + "' found. Using shift [0, 0]");
    }
    return new Vector2();
  }

  private void addFile(FileHandle toAdd) {
    if (!fileListContainsPath(fileHandles, toAdd.path())) {
      fileHandles.add(toAdd);
    }
  }
}
