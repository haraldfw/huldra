package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.drawing.singleframe.ASprite;
import com.polarbirds.huldra.model.drawing.singleframe.Sprite;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.drawing.multiframe.AdvancedAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.IHasSingleFrame;
import com.polarbirds.huldra.model.drawing.multiframe.ManualAnimation;
import com.polarbirds.huldra.model.drawing.drawable.RegionDrawable;
import com.polarbirds.huldra.model.drawing.multiframe.SimpleAnimation;
import com.polarbirds.huldra.model.drawing.singleframe.StaticAnimation;
import com.polarbirds.huldra.model.drawing.drawable.TextureDrawable;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by Harald on 10.7.15.
 */
public class SpriteLoader extends ALoader implements Disposable {

    public Map<String, ASprite> loadedSprites = new HashMap<>();
    public Map<String, AAnimation> loadedAnimations = new HashMap<>();

    private HashMap<IHasSingleFrame, TextureData> dataMap = new HashMap<>();
    private HashMap<String, TextureData> dataArrayMap = new HashMap<>();

    private Collection<String> paths = new TreeSet<>();

    @Override
    public void run() {
        max = paths.size();

        loadedSprites.clear();
        loadedAnimations.clear();

        for (String path : paths) {
            if (path.contains(".anim")) {
                loadAnimationData(path);
            } else {
                loadSpriteData(path);
            }
            loaded++;
        }

        paths.clear();
        done = true;
    }

    public void finish() {
        for (Map.Entry<String, ASprite> entry : loadedSprites.entrySet()) {
            entry.getValue().set(new TextureDrawable(new Texture(dataMap.get(entry.getValue()))));
        }

        for (Map.Entry<String, TextureData> entry : dataArrayMap.entrySet()) {
            splitAnimation(entry.getKey(), new Texture(entry.getValue()));
        }
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

    private void splitAnimation(String path, Texture texture) {
        int i = 0;
        while (true) {
            File animDescriptor = new File(
                path.substring(path.lastIndexOf("\\"), path.length() - 5) + i);
            if (animDescriptor.isFile() && animDescriptor.canWrite()
                && animDescriptor.length() > 0) {
                // File exists
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(animDescriptor));

                    int width = Integer.parseInt(reader.readLine());
                    int height = Integer.parseInt(reader.readLine());

                    ArrayList<Vector2> shifts =
                        parseShifts(texture.getWidth() / width * (texture.getHeight() / height),
                                    reader);

                    if (texture.getWidth() == width && texture.getHeight() == height) {
                        parseStaticAnimation(path, texture, shifts);
                    } else if (reader.readLine() == null) {
                        parseManualAnimation(path, width, height, texture, shifts);
                    } else {
                        float frameTime = Float.parseFloat(reader.readLine());
                        int frameTimeSpecialCases = Integer.parseInt(reader.readLine());
                        if (frameTimeSpecialCases == 0) {
                            parseSimpleAnimation(path, Float.parseFloat(reader.readLine()), width,
                                                 height,
                                                 texture, shifts);
                        } else {
                            parseAdvancedAnimation(
                                path, width, height, texture, shifts, frameTimeSpecialCases,
                                frameTime, reader);
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
        try {
            for (int i = 0; i < frames; i++) {
                shifts.add(new Vector2(
                    Float.parseFloat(reader.readLine()) / HuldraGame.PIXELS_PER_TILESIDE,
                    Float.parseFloat(reader.readLine()) / HuldraGame.PIXELS_PER_TILESIDE
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shifts;
    }

    private void parseStaticAnimation(String path, Texture texture, List<Vector2> shifts) {
        putAnimation(path,
                     new StaticAnimation(new Sprite(shifts.get(0), new TextureDrawable(texture))));
    }

    private void parseManualAnimation(String path, int width, int height, Texture texture,
                                      List<Vector2> shifts) {
        putAnimation(path, new ManualAnimation(splitTexture(width, height, texture, shifts)));
    }

    private void parseSimpleAnimation(String path, float frameTime, int width, int height,
                                      Texture texture, List<Vector2> shifts) {
        putAnimation(
            path, new SimpleAnimation(splitTexture(width, height, texture, shifts), frameTime));
    }

    private void parseAdvancedAnimation(String path, int width, int height, Texture texture,
                                        List<Vector2> shifts, int specialCases,
                                        float standardFrameTime,
                                        BufferedReader reader) {
        float[] frameTimes = new float[shifts.size()];
        Arrays.fill(frameTimes, standardFrameTime);
        try {
            for (int i = 0; i < specialCases; i++) {
                frameTimes[Integer.parseInt(reader.readLine())] =
                    Float.parseFloat(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        putAnimation(path,
                     new AdvancedAnimation(splitTexture(width, height, texture, shifts),
                                           frameTimes));
    }

    private ASprite[] splitTexture(int width, int height, Texture texture, List<Vector2> shifts) {
        ArrayList<ASprite> sprites = new ArrayList<>();

        for (int i = 0; i < shifts.size(); i++) {
            sprites.add(
                new Sprite(
                    shifts.get(i),
                    new RegionDrawable(
                        new TextureRegion(texture, 0, height * i, width, height)
                    )));
        }

        return sprites.toArray(new ASprite[sprites.size()]);
    }

    private void putAnimation(String path, AAnimation animation) {
        if (!loadedAnimations.containsKey(path)) {
            loadedAnimations.put(path, animation);
        }
    }

    public void queueAsset(String toAdd) {
        paths.add(toAdd);
    }

    public void queueAssets(String[] toAdd) {

        for (String path : paths) {
            paths.add(path);
        }
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

    @Override
    public void dispose() {
        for (Map.Entry s : loadedSprites.entrySet()) {
            if (s != null) {
                ((Disposable) s).dispose();
            }
        }
    }

    private class AnimationDescriptor {

        Vector2 frameDim;
        Vector2 shift;
        float[] frameTimes;

        public AnimationDescriptor(Vector2 frameDim,
                                   Vector2 shift, float[] frameTimes) {
            this.frameDim = frameDim;
            this.shift = shift;
            this.frameTimes = frameTimes;
        }
    }
}
