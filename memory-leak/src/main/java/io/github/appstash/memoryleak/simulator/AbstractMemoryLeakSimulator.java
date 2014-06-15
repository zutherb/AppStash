package io.github.appstash.memoryleak.simulator;

/**
 * @author zutherb
 */
public abstract class AbstractMemoryLeakSimulator implements Runnable {

    private boolean notCrashed = true;

    abstract protected void doLeakSimulation();

    @Override
    public void run() {
        while (notCrashed) {
            try {
                doLeakSimulation();
            } catch (OutOfMemoryError error) {
                notCrashed = false;
                error.printStackTrace();
                System.exit(1);
            }
        }
    }

    public boolean isNotCrashed() {
        return notCrashed;
    }
}
