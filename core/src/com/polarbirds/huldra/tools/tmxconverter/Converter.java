package com.polarbirds.huldra.tools.tmxconverter;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.io.File;
import java.io.PrintWriter;

/**
 * Created by Harald on 23.5.15.
 */
public class Converter {

  Converter(TiledMapTileLayer[] maps, Byte[][] textures, String outputDir) {
    for (TiledMapTileLayer layer : maps) {
      new PrintWriter(outputDir + layer.)

      int width = layer.getWidth();
      int height = layer.getHeight();

      for (int y = 0; y < height; y++) {
        StringBuffer buffer = new StringBuffer(width);
        for (int x = 0; x < width; x++) {
          String s;
          layer.getCell(x, y).getTile().getTextureRegion().getTexture().getTextureData()
              .consumePixmap().getPixels().array();


        }
        writer.println(buffer);
      }
    }
  }
}
