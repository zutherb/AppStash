import com.mongodb.*;
import org.springframework.web.client.RestTemplate;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zutherb
 */
public class Agent {

    static RestTemplate restTemplate = new RestTemplate();

    public static void premain(String agentArguments, Instrumentation instrumentation) {


    }

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    MongoClient mongo = new MongoClient("localhost");
                    mongo.getDatabaseNames();
                    DB analytics = mongo.getDB("analytics");
                    DBCollection memory = analytics.getCollection("memory");

                    Iterator<MemoryPoolMXBean> iterator = ManagementFactory.getMemoryPoolMXBeans().iterator();
                    while (iterator.hasNext()) {
                        MemoryPoolMXBean memoryPoolMXBean = iterator.next();
                        MemoryUsage usage = memoryPoolMXBean.getUsage();

                        DBObject dbObject = new BasicDBObject();
                        dbObject.put("name", memoryPoolMXBean.getName());
                        dbObject.put("type", memoryPoolMXBean.getType().name());
                        dbObject.put("committed", usage.getCommitted());
                        dbObject.put("init", usage.getInit());
                        dbObject.put("max", usage.getMax());
                        dbObject.put("used", usage.getUsed());
                        dbObject.put("timestamp", new Date().getTime());
                        restTemplate.put("http://localhost:9200/analytics/memory/{id}", dbObject.toString(), UUID.randomUUID().toString());
                        memory.save(dbObject);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        System.out.println("foo");
    }
}
