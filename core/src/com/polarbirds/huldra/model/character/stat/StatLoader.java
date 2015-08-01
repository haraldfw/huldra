package com.polarbirds.huldra.model.character.stat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.polarbirds.huldra.model.utility.ALoader;

import java.util.HashMap;

/**
 * Created by Harald Wilhelmsen on 31/7/2015.
 */
public class StatLoader extends ALoader {

  private final String[] pathEndings;

  private final HashMap<String, StatModifier[]> statMap = new HashMap<>();

  public StatLoader(String[] pathEndings) {
    this.pathEndings = pathEndings;
    max = pathEndings.length;
  }

  @Override
  public void run() {
    for (String path : pathEndings) {
      JsonValue json = new JsonReader().parse(Gdx.files.internal("stats/" + path + ".json"));

      loaded++;
    }
    done = true;
  }

  public LoadedStatHandler getHandler() {
    return new LoadedStatHandler(statMap);
  }
}
