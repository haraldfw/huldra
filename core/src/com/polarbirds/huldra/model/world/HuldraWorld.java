package com.polarbirds.huldra.model.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.polarbirds.huldra.model.entity.inanimateobject.Interactable;
import com.smokebox.lib.utils.geom.Line;
import com.smokebox.lib.utils.geom.UnifiablePolyedge;

import java.util.ArrayList;
import java.util.Random;

/**
 * A class for this game's World. HuldraWorld contains a 2d array of Tile objects
 * and an ArrayList of Interactables.
 * Created by Harald on 30.4.15.
 */
public final class HuldraWorld {

  public final World box2dWorld;

  private Parallax parallax;

  private HuldraWorld(TileType[][] tiles, ArrayList<Interactable> interactables) {

    box2dWorld = new World(new Vector2(0, -9.81f), false);

    int[][] ints = new int[tiles.length][tiles[0].length];
    for(int x = 0; x < tiles.length; x++) {
      for(int y = 0; y < tiles[0].length; y++) {
        TileType tile = tiles[x][y];
        if(tile != null) {
          switch (tile) {
            default:
              break;
            case SOLID:
              ints[x][y] = 1;
              break;
          }
        }
      }
    }

    UnifiablePolyedge p = new UnifiablePolyedge(ints);
    p.unify();

    for(Line l : p.getEdges()) {
      EdgeShape edgeShape = new EdgeShape();
      edgeShape.set(l.x, l.y, l.x2, l.y2);

      FixtureDef fixtureDef = new FixtureDef();
      fixtureDef.shape = edgeShape;

      BodyDef bodyDef = new BodyDef();
      bodyDef.type = BodyDef.BodyType.StaticBody;
      Body body = box2dWorld.createBody(bodyDef);

      body.createFixture(fixtureDef);
    }
  }

  public void step(float delta) {
    box2dWorld.step(delta, 8, 8); // update box2d box2dWorld
  }

  public static Parallax getParallax(OrthographicCamera camera, WorldTypes type) {
    Parallax parallax = null;

    switch (type) {
      case FOREST:
        // parallax = new Parallax(..);
        break;
      case CAVES:
        // parallax = new Parallax(..);
        break;
      default: // case TEST_STAGE
        // parallax = new Parallax(..);
        break;
    }
    return parallax;
  }

  public enum WorldTypes {

    FOREST {
      @Override
      public HuldraWorld getNew(int amountOfSections, long seed, OrthographicCamera camera) {
        TileType[][] tiles = new TileType[8][8];
        for(int x = 0; x < tiles.length; x++)
          for(int y = 0; y < tiles[x].length; y++)
            tiles[x][y] = TileType.EMPTY;

        ArrayList<Interactable> interactables = new ArrayList<>();
        return new HuldraWorld(tiles, interactables);
      }
    },

    CAVES {
      @Override
      public HuldraWorld getNew(int amountOfSections, long seed, OrthographicCamera camera) {
        System.out.println("Creating Caves with " + amountOfSections + " sections");

        Random random = new Random(seed);

        Section[][] sections = new Section[amountOfSections *2][amountOfSections *2];
        // place spawn
        sections[amountOfSections][amountOfSections] =
            Section.getNew(sections, amountOfSections, amountOfSections, random);
        sections[amountOfSections][amountOfSections].sectionType = SectionType.START;

        System.out.println("Placed spawn");

        // place down section until requirement amountOfSections is met
        int sectionsPlaced = 1;
        while(sectionsPlaced < amountOfSections) {
          System.out.println("amountofsections " + sectionsPlaced + "/" + amountOfSections);

          ArrayList<Integer> openingXs = new ArrayList<>();
          ArrayList<Integer> openingYs = new ArrayList<>();

          for(int x = 0; x < sections.length; x++) {
            for(int y = 0; y < sections[0].length; y++) {
              if(sections[x][y] != null) {
                ArrayList<Opening> entries = sections[x][y].entries;
                ArrayList<Opening> exits = sections[x][y].exits;

                if(sections[x - 1][y] == null) { // && entries contains LEFT
                  openingXs.add(x - 1);
                  openingYs.add(y);
                }
                if(sections[x + 1][y] == null) {
                  openingXs.add(x + 1);
                  openingYs.add(y);
                }
                if(sections[x][y - 1] == null) {
                  openingXs.add(x);
                  openingYs.add(y - 1);
                }
                if(sections[x][y + 1] == null) {
                  openingXs.add(x);
                  openingYs.add(y + 1);
                }
              }
            } // y-loop
          } // x-loop

          // add sections in all openings
          System.out.println("openings " + openingXs.size());
          while(!openingXs.isEmpty()) {
            int openingRan = random.nextInt(openingXs.size());
            int x = openingXs.get(openingRan);
            int y = openingYs.get(openingRan);

            sections[x][y] = Section.getNew(sections, x, y, random);

            openingXs.remove(openingRan);
            openingYs.remove(openingRan);
            sectionsPlaced++;
          }
        }

        for (int x = 0; x < sections.length; x++) {
          for (int y = 0; y < sections[0].length; y++) {
            Section section = sections[x][y];
            if(section != null) section.finalizeSection();
          }
        }


        TileType[][] tiles = new TileType[sections.length*8][sections[0].length*8];
        for (int x = 0; x < sections.length; x++) {
          for (int y = 0; y < sections[0].length; y++) {
            Section section = sections[x][y];
            if(section != null) {
              int startx = x*8;
              int starty = y*8;
              for (int sx = 0; sx < section.tiles.length; sx++) {
                for (int sy = 0; sy < section.tiles[0].length; sy++) {
                  tiles[startx + sx][starty + sy] = section.tiles[sx][sy];
                }
              }
            }
          }
        }

        ArrayList<Interactable> interactables = new ArrayList<>();
        return new HuldraWorld(tiles, interactables);
      }
    },

    TEST_STAGE {
      @Override
      public HuldraWorld getNew(int amountOfSections, long seed, OrthographicCamera camera) {
        TileType[][] tiles = new TileType[][]{
            {TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID},
            {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
            {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
            {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
            {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
            {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
            {TileType.SOLID, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.SOLID},
            {TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID, TileType.SOLID}
        };
        ArrayList<Interactable> interactables = new ArrayList<>();
        return new HuldraWorld(tiles, interactables);
      }
    };

    public abstract HuldraWorld getNew(int amountOfSections, long seed, OrthographicCamera camera);
  }
}
