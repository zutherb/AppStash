package io.github.appstash.memoryleak.simulation;

public abstract class AbstractMemoryLeakSimulator implements Runnable {

    private volatile boolean notCrashed = true;

    abstract protected void doLeakSimulation();

    @Override
    public void run() {
        try {
            while (notCrashed) {
                doLeakSimulation();
            }
        } catch (OutOfMemoryError error) {
            notCrashed = false;
            error.printStackTrace();
            Thread.currentThread().interrupt();
            System.exit(1);
        }
    }

    public boolean isNotCrashed() {
        return notCrashed;
    }
}