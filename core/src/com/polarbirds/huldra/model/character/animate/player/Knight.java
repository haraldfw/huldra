package com.polarbirds.huldra.model.character.animate.player;

import com.polarbirds.huldra.model.character.Team;
import com.polarbirds.huldra.model.character.stat.StatClass;
import com.polarbirds.huldra.model.character.stat.StatModifier;
import com.polarbirds.huldra.model.character.stat.StatType;
import com.polarbirds.huldra.model.world.physics.Vector2;
import com.polarbirds.huldra.screen.game.GameScreen;

/**
 * Created by Harald on 8.5.15.
 */
public class Knight extends PlayerCharacter {

  private final StatModifier[] baseStats = new StatModifier[]{
      new StatModifier(StatType.JUMP_STRENGTH, StatClass.BASE, 1),
      new StatModifier(StatType.MOVE_STRENGTH, StatClass.BASE, 25f),
      new StatModifier(StatType.DMG_PHYSICAL, StatClass.BASE, 1)
  };

  public Knight(Team team) {
    super(team);
  }

  @Override
  public StatModifier[] getBaseStats() {
    return baseStats;
  }

  @Override
  public void init(Vector2 pos, GameScreen gameScreen) {
    super.init(pos, gameScreen);
  }
}