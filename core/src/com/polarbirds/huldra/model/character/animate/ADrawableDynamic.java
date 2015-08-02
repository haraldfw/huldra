package com.polarbirds.huldra.model.character.animate;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.stat.IHasBaseStats;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.world.model.Level;
import com.polarbirds.huldra.model.world.physics.DynamicBody;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.model.world.physics.shape.RectShape;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class ADrawableDynamic implements IHasBaseStats {

  public DynamicBody body;
  public Team team;

  public ADrawableDynamic(Level level, Vector2 pos, float width, float height, float inverseMass, Team team) {
    this.body = new DynamicBody(pos, new RectShape(width, height), inverseMass, level);
    this.team = team;
  }

  protected static Map<CharacterState, AAnimation> getAnimations(
      String directory, Map<String, AAnimation> spriteLoaderMap) {
    Map<CharacterState, AAnimation> animationMap = new HashMap<>();

    for (Map.Entry<String, AAnimation> entry : spriteLoaderMap.entrySet()) {
      String key = entry.getKey();
      System.out.println(key);
      if (key.contains(directory)) {
        animationMap.put(
            getFromString(
                key.substring(
                    key.lastIndexOf("/")
                )
            ),
            entry.getValue());
      }
    }
    return animationMap;
  }

  private static CharacterState getFromString(String string) {
    switch (string) {
      case "walk":
        return CharacterState.WALKING;
      case "dance":
        return CharacterState.DANCING;
      case "jump":
        return CharacterState.FALLING;
      case "slash":
        return CharacterState.ATTACKING;
      default:
        return CharacterState.IDLE;
    }
  }

  public void update(float delta) {
    body.integrate(delta);
    getCurrentAnimation().update(this, delta);
  }

  public void draw(Batch batch, float parentAlpha) {
    getCurrentAnimation().draw(this, batch, body.pos);

  }

  protected abstract AAnimation getCurrentAnimation();
}
