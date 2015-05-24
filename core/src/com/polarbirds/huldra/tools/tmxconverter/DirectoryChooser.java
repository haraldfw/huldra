package com.polarbirds.huldra.tools.tmxconverter;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFileChooser;

/**
 * Created by Harald on 24.5.15.
 */
class DirectoryChooser extends JFileChooser {

  DirectoryChooser() {
    setCurrentDirectory(new File("."));
    setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    setAcceptAllFileFilterUsed(false);
  }

  private void openDialog(String title) {
    setDialogTitle(title);
    if (showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
      System.out.println("User cancelled. Exiting...");
      System.exit(0);
    }
  }

  String getOutputDirectory() {
    openDialog("Choose an output directory");
    return getPath();
  }

  LayerWithInfo[] getLayers() {
    openDialog("Select directory with tmx-files");

    File[] sectionFiles = getDirectoryContents();

    if (sectionFiles == null || sectionFiles.length == 0) {
      System.out.println("No sections found in selected directory. Exiting...");
      System.exit(1);
    }

    Collection<LayerWithInfo> layers = new ArrayList<>();

    for (File file : sectionFiles) {
      TmxMapLoader loader = new TmxMapLoader(new ExternalFileResolver());
      String fileString = file.toString();
      System.out.println("Loading file " + fileString);
      TiledMap map = loader.load(fileString);
      layers.add(new LayerWithInfo((TiledMapTileLayer) map.getLayers().get(0),
                                   fileString.substring(fileString.lastIndexOf("/")),
                                   map.getProperties()));
    }

    return (LayerWithInfo[]) layers.toArray();
  }

  byte[][] getTextures() {
    openDialog("Select directory with textures and sections directory");

    File[] textureFiles = getDirectoryContents();

    if (textureFiles == null || textureFiles.length == 0) {
      System.out.println("No textures found in selected location. Exiting...");
      System.exit(1);
    }

    Collection<byte[]> textures = new ArrayList<>();
    for (File file : textureFiles) {
      Texture texture = new Texture(new FileHandle(file));
      texture.getTextureData().prepare();
      Pixmap pixmap = texture.getTextureData().consumePixmap();
      textures.add(pixmap.getPixels().array());
    }

    return (byte[][]) textures.toArray();
  }

  private File getFile() {
    return getSelectedFile();
  }

  private String getPath() {
    return getFile().getAbsolutePath();
  }

  private File[] getDirectoryContents() {
    return getFile().listFiles();
  }

  class LayerWithInfo {

    TiledMapTileLayer layer;
    String filename;
    MapProperties properties;

    public LayerWithInfo(TiledMapTileLayer layer, String filename,
                         MapProperties properties) {
      this.layer = layer;
      this.filename = filename;
      this.properties = properties;
    }
  }
}
