package com.cakes.cache;

import com.cakes.log.SubSlf4jLogger;
import org.caffinitas.ohc.CacheSerializer;
import org.caffinitas.ohc.OHCache;
import org.caffinitas.ohc.OHCacheBuilder;
import org.caffinitas.ohc.OHCacheStats;

import java.nio.ByteBuffer;
import java.util.Map;

public abstract class OHCacheManager<K,V> extends AbstractCache<K,V> {
    private SubSlf4jLogger log = new SubSlf4jLogger(this.getClass());

    protected OHCache<K,V> localCache;

    private static final long defaultCapacity = 16;
    @Override
    protected void initCache() {
        OHCacheBuilder<K, V> builder = OHCacheBuilder.newBuilder();
        localCache = builder
                .keySerializer(getKeySerializer())
                .valueSerializer(getValSerializer())
                .capacity(getCapacity()*1024*1024).build();
    }

    protected  CacheSerializer<K>  getKeySerializer(){
        return new Serializer<>();
    }

    protected  CacheSerializer<V>  getValSerializer(){
        return new Serializer<>();
    }

    protected Long getCapacity(){
        return defaultCapacity;
    }

    @Override
    protected void print() {
        OHCacheStats cacheStats = localCache.stats();
        log.info("cacheName：{}, cacheStatus：{}，hitRate：{}，",this.getClass().getSimpleName(),cacheStats.toString(),null);
    }

    @Override
    protected V realGet(K key) {
        return localCache.get(key);
    }

    @Override
    protected void putToCache(Map<K, V> resultMap) {
        localCache.putAll(resultMap);
    }

    protected static class Serializer<T> implements CacheSerializer<T>{
        @Override
        public void serialize(T data, ByteBuffer buf) {

        }

        @Override
        public T deserialize(ByteBuffer buf) {
            return null;
        }

        @Override
        public int serializedSize(T data) {
            return 0;
        }
    }

    public static void main(String[] args) {
        Serializer<String> stringSerializer = new Serializer<>();
    }
}

