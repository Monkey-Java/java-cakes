package com.cakes.utils;

import com.alibaba.fastjson.JSONObject;
import com.cakes.dto.Comparison;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author hebiao
 * @date 2023/1/18 10:15
 * @description
 * @return
 **/
public class CompareObjUtil {
    /**
     * 比较两个对象是否存在不同字段属性
     *
     * @param o1
     * @param o2
     * @return
     * @throws Exception
     */
    private static List<Comparison> compareObj(Object o1, Object o2) throws Exception {
        List<Comparison> diffs = new ArrayList<>();
        //取出属性
        Field[] beforeFields = o1.getClass().getDeclaredFields();
        Field[] afterFields = o2.getClass().getDeclaredFields();
        Field.setAccessible(beforeFields, true);
        Field.setAccessible(afterFields, true);
        //遍历取出差异值
        if (beforeFields != null && beforeFields.length > 0) {
            for (int i = 0; i < beforeFields.length; i++) {
                Object o1Val = beforeFields[i].get(o1);
                Object o2Val = afterFields[i].get(o2);
                if ((o1Val != null && !"".equals(o1Val) && !o1Val.equals(o2Val)) || ((o1Val == null || "".equals(o1Val)) && o2Val != null)) {
                    Comparison comparison = new Comparison();
                    comparison.setField(beforeFields[i].getName());
                    comparison.setBefore(o1Val);
                    comparison.setAfter(o2Val);
                    comparison.setIsUpdate(true);
                    diffs.add(comparison);
                }
            }
        }
        return diffs;
    }

    public static <T> T compareObjToMap(Object o1, Object o2, Class<T> clazz) {

        if (Objects.isNull(o1)) {
            throw new IllegalArgumentException("比较对象不能为空");
        }
        if (Objects.isNull(o2)) {
            throw new IllegalArgumentException("被比较对象不能为空");
        }
        if (!o1.getClass().isAssignableFrom(o2.getClass())) {
            throw new IllegalArgumentException("两个对象不相同，无法比较");
        }

        List<Comparison> comparisons = new ArrayList<>();
        try {
            comparisons = compareObj(o1, o2);
        } catch (Exception e) {
            throw new RuntimeException("比较数据对象异常！");
        }
        if (comparisons != null && comparisons.size() != 0) {
            String jsonString = JSONObject.toJSONString(o1);
            Map map = JSONObject.parseObject(jsonString, Map.class);
            comparisons.forEach(e -> {
                if (map.containsKey(e.getField())) {
                    String field = e.getField();
                    String substring = field.substring(0, 1);
                    String replaceFiled = field.replaceFirst(substring, substring.toUpperCase());
                    String setField = "update" + replaceFiled;
                    map.put(setField, e.getBefore());
                    map.put(field, e.getAfter());
                }
            });
            T t = JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
            return t;
        }
        return null;
    }

}
