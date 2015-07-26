package com.polarbirds.huldra.model.utility;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.polarbirds.huldra.HuldraGame;
import com.polarbirds.huldra.model.animation.AAnimation;
import com.polarbirds.huldra.model.animation.AdvancedAnimation;
import com.polarbirds.huldra.model.animation.ManualAnimation;
import com.polarbirds.huldra.model.animation.SimpleAnimation;
import com.polarbirds.huldra.model.animation.StaticAnimation;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Harald on 10.7.15.
 */
public class SpriteLoader extends ALoader implements Disposable {

    public boolean isDone = false;
    private double progress = 0;
    private Set<String> paths = new TreeSet<>();
    private Map<String, ASprite> loadedSprites;
    private Map<String, AAnimation> loadedAnimations;

    @Override
    public void run() {
        startThread();

        loadedSprites = new HashMap<>();
        loadedAnimations = new HashMap<>();

        double progressIncrement = 1.0 / paths.size();
        for (String path : paths) {
            if (path.contains(".anim")) {
                loadAnimation(path);
            } else {
                loadSprite(path);
            }
            progress += progressIncrement;
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
        putAnimation(path, new StaticAnimation(new Sprite(texture, shifts.get(0))));
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
                new RegionSprite(
                    new TextureRegion(texture, 0, height * i, width, height),
                    shifts.get(i)
                ));
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
            ((Disposable) s).dispose();
        }
    }

    private enum loadEnum {
        KNIGHT(new String[]{
            "graphics/player/knight/walk.anim",
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
