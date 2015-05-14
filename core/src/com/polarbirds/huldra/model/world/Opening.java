package com.polarbirds.huldra.model.world;

/**
 * Created by Harald Wilhelmsen on 13/5/2015.
 */
public class Opening {

  final OpeningType type;
  boolean open;

  public Opening(OpeningType type, boolean open) {
    this.type = type;
    this.open = open;
  }

  public void setOpen(boolean open) {
    this.open = open;
  }
}
