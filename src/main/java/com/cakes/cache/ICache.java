package com.cakes.cache;

import java.util.List;
import java.util.function.Function;

/**
 * @author jianghk
 */
public interface ICache<K, V> {
    /**
     * @return
     */
    <R> R get(K key, Function<V,R> mapFunction);

    /**
     * @return
     */
    <R> List<R> batchGet(List<K> keys,Function<V,R> mapFunction);
}
