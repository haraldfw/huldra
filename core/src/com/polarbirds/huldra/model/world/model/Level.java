package com.polarbirds.huldra.model.world.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.polarbirds.huldra.model.entity.ADrawableDynamic;
import com.polarbirds.huldra.model.entity.character.player.APlayerCharacter;
import com.polarbirds.huldra.model.world.physics.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class for this game's World. HuldraWorld contains a 2d array of Tile objects and an ArrayList
 * of Interactables. Created by Harald on 30.4.15.
 */
public final class Level {

  private final List<ADrawableDynamic> dynamics;
  public APlayerCharacter[] players;
  public Tile[][] tiles;
  public Vector2 spawn;
  public int difficulty;

  public Level() {
    dynamics = new ArrayList<>();
  }

  public void setNew(Tile[][] tiles, Vector2 spawn, int difficulty) {
    this.tiles = tiles;
    this.spawn = spawn;
    this.difficulty = difficulty;
  }

  public void setPlayers(APlayerCharacter[] players) {
    if (this.players != null) {
      for (ADrawableDynamic a : this.players) {
        dynamics.remove(a);
      }
    }

    Collections.addAll(dynamics, players);

    this.players = players;
  }

  public void draw(Batch batch) {
    batch.begin();
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[0].length; y++) {
        Texture t = tiles[x][y].texture;
        if (t != null) {
          batch.draw(t, x, y, 1, 1);
        }
      }
    }
    batch.end();
  }

  public void debugDraw(ShapeRenderer sr) {
    for (int x = 0; x < tiles.length; x++) {
      for (int y = 0; y < tiles[0].length; y++) {
        switch (tiles[x][y].tileType) {
          case PLATFORM:
            sr.line(x, y + 1, x + 1, y + 1);
            break;
          case TOP_LADDER_PLATFORM:
            sr.line(x, y + 1, x + 1, y + 1);
          case LADDER:
            sr.line(x + 1 / 5f, y, x + 1 / 5f, y + 1);
            sr.line(x + 4 / 5f, y, x + 4 / 5f, y + 1);
            sr.line(x + 1 / 5f, y + 1 / 3f, x + 4 / 5f, y + 1 / 3f);
            sr.line(x + 1 / 5f, y + 2 / 3f, x + 4 / 5f, y + 2 / 3f);
            sr.line(x + 1 / 5f, y + 1, x + 4 / 5f, y + 1);
            break;
          case EMPTY:
            break;
          case SOLID:
            sr.rect(x, y, 1, 1);
            sr.line(x, y, x + 1, y + 1);
            break;
        }
      }
    }

    for (ADrawableDynamic dyn : dynamics) {
      dyn.body.debugDraw(sr);
    }
  }

  public void addDrawableDynamic(ADrawableDynamic body) {
    dynamics.add(body);
  }

  public void integrate(float delta) {
    for (ADrawableDynamic dyn : dynamics) {
      dyn.update(delta);
    }
  }
}
