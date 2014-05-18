/**
 * @author zutherb
 */
public class AppStashMemoryUsage extends java.lang.management.MemoryUsage {
    public AppStashMemoryUsage(java.lang.management.MemoryUsage usage) {
        super(usage.getInit(), usage.getUsed(), usage.getCommitted(), usage.getMax());
    }
}
