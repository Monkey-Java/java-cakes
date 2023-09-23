package com.cakes.cache;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 缓存抽象基类.
 *
 * @author jianghk
 */
public abstract class AbstractCache<K, V> implements ICache<K, V> {
    boolean ifPrint = true;

    private ScheduledExecutorService printCacheInfoSchedule = Executors.newScheduledThreadPool(1);

    /**
     * 初始化缓存组件.
     */
    protected abstract void initCache();

    private void printCacheStatus() {
        if (ifPrint) {
            print();
        }
    }

    /**
     * 打印缓存信息.
     */
    protected abstract void print();

    /**
     * 获取缓存元素
     *
     * @param key key
     * @return value
     */
    protected abstract V realGet(K key);

    /**
     * 批量获取缓存元素
     *
     * @param keys keys
     * @return values
     */
    protected abstract List<V> realBatchGet(List<K> keys);

    /**
     * 是否开启缓存状态打印
     *
     * @param print
     * @return
     */
    void ifPrintStatus(boolean print) {
        synchronized (this) {
            if (print) {
                printCacheInfoSchedule.execute(this::printCacheStatus);
            } else {
                printCacheInfoSchedule.shutdown();
            }
        }
    }

    @PostConstruct
    private void init() {
        initCache();
        ifPrintStatus(true);
    }
}
