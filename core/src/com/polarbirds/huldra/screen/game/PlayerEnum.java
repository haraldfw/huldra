package com.polarbirds.huldra.screen.game;

import com.polarbirds.huldra.model.entity.animation.AAnimation;
import com.polarbirds.huldra.model.entity.character.ADynamicCharacter;
import com.polarbirds.huldra.model.entity.character.Team;
import com.polarbirds.huldra.model.entity.character.player.Knight;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harald Wilhelmsen on 30/6/2015.
 */
public enum PlayerEnum {
  KNIGHT {
    private AAnimation[] animations;

    @Override
    public AAnimation[] getAssets() {
      return animations;
    }

    @Override
    public void loadAndParseFiles() {
      animations = parseAnimations(new String[]{
          "walk",
          "dance"
      });
    }

    @Override
    public void disengage() {
      animations = null;
    }

    @Override
    public ADynamicCharacter getInstance(Vector2 pos, GameScreen gameScreen) {
      return new Knight(pos, Team.PLAYER, gameScreen);
    }
  };

  public abstract ADynamicCharacter getInstance(Vector2 pos, GameScreen gameScreen);

  public abstract AAnimation[] getAssets();

  public abstract void loadAndParseFiles();

  public abstract void disengage();

  private AAnimation[] parseAnimations(String folder) {
    List<AAnimation> animations = new ArrayList<>();
    String[] files = {
        "dance",
        "idle",
        "jump",
        "slash",
        "walk"
    };




    return animations.toArray(new AAnimation[animations.size()]);
  }
}
