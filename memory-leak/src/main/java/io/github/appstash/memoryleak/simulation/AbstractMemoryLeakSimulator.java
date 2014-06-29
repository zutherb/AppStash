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
            throw new OutOfMemoryError(error.getMessage());
        }
    }

    public boolean isNotCrashed() {
        return notCrashed;
    }
}