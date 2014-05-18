import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zutherb
 */
public class Agent {


    public static void premain(String agentArguments, Instrumentation instrumentation) {


    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
        scheduler.scheduleAtFixedRate(new MemoryTask(), 0, 1, TimeUnit.SECONDS);
    }
}
