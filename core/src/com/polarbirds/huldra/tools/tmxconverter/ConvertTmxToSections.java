package com.polarbirds.huldra.tools.tmxconverter;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

/**
 * Created by Harald on 23.5.15.
 */
final class ConvertTmxToSections {

  private ConvertTmxToSections() {
  }

  public static void main(String[] args) {

    JFileChooser chooser = new JFileChooser();
    int result;

    chooser = new JFileChooser();
    chooser.setCurrentDirectory(new File("."));
    chooser.setDialogTitle("Choose directory with textures and sections directories");
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    chooser.setAcceptAllFileFilterUsed(false);

    if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
      System.out.println("User cancelled in choose tmx-dir step. Exiting...");
      return;
    }

    File[] files =
        (chooser.getSelectedFiles().length == 0 ? chooser.getCurrentDirectory()
                                                : chooser.getSelectedFiles()[0]).listFiles();
    if (files.length != 2) {
      System.out.println("Invalid folder configuration. Exiting...");
      return;
    }
    File[] sectionFiles = files[0].listFiles();
    File[] textureFiles = files[1].listFiles();

    ArrayList<Byte[]> textures = new ArrayList<>();
    for (File file : textureFiles) {

    }

    ArrayList<TiledMapTileLayer> layers = new ArrayList<>();
    for (File file : sectionFiles) {
      TmxMapLoader loader = new TmxMapLoader();
      TiledMap map = loader.load(file.getAbsolutePath());
      layers.add((TiledMapTileLayer) map.getLayers().get(0));
    }

    chooser.setDialogTitle("Choose an output directory");
    if (chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
      System.out.println("User cancelled in choose output-dir step. Exiting...");
      return;
    }

    String outputDir =
        chooser.getSelectedFiles().length == 0 ? chooser.getCurrentDirectory().getAbsolutePath()
                                               : chooser.getSelectedFiles()[0].getAbsolutePath();

    Converter converter =
        new Converter((TiledMapTileLayer[]) layers.toArray(), (Byte[][]) textures.toArray(),
                      outputDir);
  }


}
