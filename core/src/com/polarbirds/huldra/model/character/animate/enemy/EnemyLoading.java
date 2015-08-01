package com.polarbirds.huldra.model.character.animate.enemy;

import com.polarbirds.huldra.model.character.stat.StatLoader;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.utility.SpriteLoader;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald Wilhelmsen on 31/7/2015.
 */
public enum EnemyLoading {
  BAT {
    @Override
    public AEnemyCharacter getInstance(GameScreen gameScreen, StatModifier[] baseStats,
                                       Vector2 pos) {
      return null;
    }

    @Override
    public void queueAssets(SpriteLoader spriteLoader, StatLoader statLoader) {

    }
  },;

  public abstract AEnemyCharacter getInstance(GameScreen gameScreen, StatModifier[] baseStats,
                                              Vector2 pos);

  public abstract void queueAssets(SpriteLoader spriteLoader, StatLoader statLoader);

  public EnemyLoading getType(String string) {
    switch (string) {
      case "bat":
        return BAT;
    }
    return null;
  }
}
