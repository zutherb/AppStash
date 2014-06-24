package io.github.appstash.memoryleak.simulation;

/**
 * @author zutherb
 */
public abstract class AbstractMemoryLeakSimulator implements Runnable {

    private boolean notCrashed = true;

    abstract protected void doLeakSimulation();

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            while (notCrashed) {
                doLeakSimulation();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (OutOfMemoryError error) {
            notCrashed = false;
            error.printStackTrace();
            System.exit(1);
        }
    }

    public boolean isNotCrashed() {
        return notCrashed;
    }
}
