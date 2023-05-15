package com.cakes.utils;

import com.alibaba.fastjson.JSONObject;
import com.cakes.dto.Comparison;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * @param beforeObj
     * @param afterObj
     * @return
     * @throws Exception
     */
    private static List<Comparison> compareObj(Object beforeObj, Object afterObj) throws Exception {
        List<Comparison> diffs = new ArrayList<>();

        //取出属性
        Field[] beforeFields = beforeObj.getClass().getDeclaredFields();
        Field[] afterFields = afterObj.getClass().getDeclaredFields();
        Field.setAccessible(beforeFields, true);
        Field.setAccessible(afterFields, true);

        //遍历取出差异值
        if (beforeFields != null && beforeFields.length > 0) {
            for (int i = 0; i < beforeFields.length; i++) {
                Object beforeValue = beforeFields[i].get(beforeObj);
                Object afterValue = afterFields[i].get(afterObj);
                if ((beforeValue != null && !"".equals(beforeValue) && !beforeValue.equals(afterValue)) || ((beforeValue == null || "".equals(beforeValue)) && afterValue != null)) {
                    Comparison comparison = new Comparison();
                    comparison.setField(beforeFields[i].getName());
                    comparison.setBefore(beforeValue);
                    comparison.setAfter(afterValue);
                    comparison.setIsUpdate(true);
                    diffs.add(comparison);
                }
            }
        }
        return diffs;
    }

    public static <T> T compareObjToMap(Object beforeObj, Object afterObj, Class<T> tClass) {

        if (beforeObj == null) {
            throw new RuntimeException("原对象不能为空");
        }
        if (afterObj == null) {
            throw new RuntimeException("新对象不能为空");
        }
        if (!beforeObj.getClass().isAssignableFrom(afterObj.getClass())) {
            throw new RuntimeException("两个对象不相同，无法比较");
        }

        List<Comparison> comparisons = new ArrayList<>();
        try {
            comparisons = compareObj(beforeObj, afterObj);
        } catch (Exception e) {
            throw new RuntimeException("比较数据对象异常！");
        }
        if (comparisons != null && comparisons.size() != 0) {
            String jsonString = JSONObject.toJSONString(beforeObj);
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
            T t = JSONObject.parseObject(JSONObject.toJSONString(map), tClass);
            return t;
        }
        return null;
    }

}
