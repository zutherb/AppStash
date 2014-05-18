import com.mongodb.*;
import org.springframework.web.client.RestTemplate;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
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

                    for (MemoryPoolMXBean memoryPoolMXBean : ManagementFactory.getMemoryPoolMXBeans()) {
                        MemoryUsage usage = memoryPoolMXBean.getUsage();

                        DBObject dbObject = new BasicDBObject();
                        dbObject.put("name", memoryPoolMXBean.getName());
                        dbObject.put("type", memoryPoolMXBean.getType().toString());
                        dbObject.put("host", InetAddress.getLocalHost().getHostName());
                        dbObject.put("ip", InetAddress.getLocalHost().getHostAddress());
                        dbObject.put("committed", usage.getCommitted());
                        dbObject.put("init", usage.getInit());
                        dbObject.put("max", usage.getMax());
                        dbObject.put("used", usage.getUsed());

                        SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        formatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
                        dbObject.put("@timestamp", formatUTC.format(new Date()).replace(" ", "T"));
                        restTemplate.put("http://10.211.55.100:9200/analytics/memory/{id}", dbObject.toString(), UUID.randomUUID().toString());
                        memory.save(dbObject);
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
