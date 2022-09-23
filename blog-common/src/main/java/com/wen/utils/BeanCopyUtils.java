package com.wen.utils;


import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    /**
     * 单个对象bean拷贝
     *
     * @param source 要拷贝的对象
     * @param clazz 字节码对象（传入的参数）
     * */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        // 创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    /**
     * 集合对象bean拷贝
     *
     * @param list 要拷贝的对象
     * @param clazz 字节码对象（传入的参数）
     * */
    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        List<V> collect = list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
        return collect;
    }
}
