package com.cakes.utils;

import java.util.Collection;
import java.util.Objects;

/**
 * 集合操作工具类.
 *
 * @author jianghk
 */
public final class CollectionUtils {
    private CollectionUtils() {

    }

    /**
     * 判断集合是否不为空.
     *
     * @param collection collection
     * @returnt true or false
     */
    public static boolean isNotEmpty(Collection collection) {
        if (Objects.nonNull(collection) && collection.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断集合是否为空.
     *
     * @param collection collection
     * @returnt true or false
     */
    public static boolean isEmpty(Collection collection) {
        return !isNotEmpty(collection);
    }
}
