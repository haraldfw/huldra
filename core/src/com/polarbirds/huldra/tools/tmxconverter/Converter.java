package com.polarbirds.huldra.tools.tmxconverter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Harald on 23.5.15.
 */
public class Converter {

  DirectoryChooser.LayerWithInfo[] layers;
  byte[][] textures;
  String outputDir;

  public Converter(DirectoryChooser.LayerWithInfo[] layers, byte[][] textures,
                   String outputDir) {
    this.layers = layers;
    this.textures = textures;
    this.outputDir = outputDir;
  }

  void convert() {
    for (DirectoryChooser.LayerWithInfo layerInfo : layers) {
      PrintWriter writer = null;

      try {
        writer = new PrintWriter(outputDir + layerInfo.filename);

        int width = layerInfo.layer.getWidth();
        int height = layerInfo.layer.getHeight();

        for (int y = 0; y < height; y++) {
          StringBuffer buffer = new StringBuffer(width);
          for (int x = 0; x < width; x++) {
            buffer.append(getTile(
                layerInfo.layer.getCell(x, y).getTile().getTextureRegion().getTexture()
                    .getTextureData()
                    .consumePixmap().getPixels().array()));
          }
          writer.println(buffer);
        }

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } finally {
        if (writer != null) {
          writer.close();
        }
      }
    }
  }

  private String getTile(byte[] tileBytes) {
    return "i";
  }

  public static void main(String[] args) {
    DirectoryChooser chooser = new DirectoryChooser();
    Converter converter =
        new Converter(chooser.getLayers(), chooser.getTextures(), chooser.getOutputDirectory());
  }
}
