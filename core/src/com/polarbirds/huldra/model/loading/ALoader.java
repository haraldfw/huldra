package com.polarbirds.huldra.model.loading;

/**
 * Created by Harald Wilhelmsen on 11/7/2015.
 */
public abstract class ALoader implements Runnable {

  public int loaded = 0;
  public int max = 0;
  public boolean done = false;

  public void startThread() {
    Thread t = new Thread(this);
    t.start();
  }

  @Override
  public abstract void run();
}
