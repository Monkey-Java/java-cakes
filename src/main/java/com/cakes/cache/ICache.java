package com.cakes.cache;

import java.util.List;
/**
 * @author jianghk
 */
public interface ICache<K, V> {
    /**
     * @return
     */
    V get(K key);

    /**
     * @return
     */
    List<V> batchGet(List<K> keys);
}
