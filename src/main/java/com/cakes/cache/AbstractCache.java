package com.cakes.cache;

import com.cakes.utils.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 缓存抽象基类.
 *
 * @author jianghk
 */
public abstract class AbstractCache<K, V> implements ICache<K, V> {
    private boolean ifPrint = true;

    private ScheduledExecutorService printCacheInfoSchedule = Executors.newScheduledThreadPool(1);

    @Override
    public <R> R get(K key, Function<V, R> mapFunction) {
        if (Objects.isNull(key)) {
            throw new IllegalArgumentException("Key不能为空");
        }
        return mapFunction.apply(realGet(key));
    }

    @Override
    public <R> List<R> batchGet(List<K> keys, Function<V, R> mapFunction) {
        if (CollectionUtils.isNotEmpty(keys)) {
            // 获取本地实例缓存的元素
            Map<K,V> cachedValues = new HashMap<>(keys.size());
            for (K key : keys) {
                V val = realGet(key);
                if(Objects.nonNull(val)){
                    cachedValues.put(key,val);
                }
            }

            // 获取远端元素放入本地缓存
            Set<K> cachedKeys  = cachedValues.keySet();
            List<K> unCachedKeys = keys.stream().filter(key->!cachedKeys.contains(key)).collect(Collectors.toList());
            Map<K,V> remoteValues = getFromRemote(unCachedKeys);
            cachedValues.putAll(remoteValues);
            putToCache(remoteValues);

            return keys.stream().map(key->cachedValues.get(key)).map(mapFunction).collect(Collectors.toList());
        } else {
            return new ArrayList<>(0);
        }
    }


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
     * 将远端查询的元素放入缓存中.
     *
     * @param resultMap resultMap
     */
    protected abstract void putToCache(Map<K, V> resultMap);

    /**
     * 从远端获取元素.
     *
     * @param keys keys
     * @return values
     */
    protected abstract Map<K, V> getFromRemote(List<K> keys);

    /**
     * 是否开启缓存状态打印
     *
     * @param print
     *
     *
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
