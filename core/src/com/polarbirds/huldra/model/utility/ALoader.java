package com.polarbirds.huldra.model.utility;

/**
 * Created by Harald Wilhelmsen on 11/7/2015.
 */
public abstract class ALoader implements Runnable {

    public void startThread() {
        Thread t = new Thread(this);
        t.start();
    }

    public abstract double getProgress();
}
