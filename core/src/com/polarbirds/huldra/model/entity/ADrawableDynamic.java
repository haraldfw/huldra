package com.polarbirds.huldra.model.entity;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.polarbirds.huldra.model.drawing.AAnimation;
import com.polarbirds.huldra.model.entity.character.MoveState;
import com.polarbirds.huldra.model.entity.stat.IHasBaseStats;
import com.polarbirds.huldra.model.world.physics.DynamicBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harald Wilhelmsen on 16/6/2015.
 */
public abstract class ADrawableDynamic implements IHasBaseStats {

  public DynamicBody body;
  public Team team;

  public ADrawableDynamic(DynamicBody body, Team team) {
    this.body = body;
    this.team = team;
  }

  protected static Map<MoveState, AAnimation> getAnimations(
      CharSequence directory, Map<String, AAnimation> spriteLoaderMap) {
    Map<MoveState, AAnimation> animationMap = new HashMap<>();

    for (Map.Entry<String, AAnimation> entry : spriteLoaderMap.entrySet()) {
      String key = entry.getKey();
      System.out.println("Key: " + key);
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

  private static MoveState getFromString(String string) {
    switch (string) {
      case "walk":
        return MoveState.WALKING;
      case "dance":
        return MoveState.DANCING;
      case "jump":
        return MoveState.FALLING;
      default:
        return MoveState.IDLE;
    }
  }

  public void update(float delta) {
    body.integrate(delta);
    getCurrentAnimation().update(this, delta);
  }

  public void draw(Batch batch) {
    getCurrentAnimation().draw(this, batch, body.pos);
  }

  protected abstract AAnimation getCurrentAnimation();
}
