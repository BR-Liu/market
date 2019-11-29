package com.brliu.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CollectionBeanConverter
 * @Description 帮助普通或集合Bean类型互转，由于依赖BeanUtils，故为浅拷贝，使用时需注意。
 * @Author gosling
 * @Version 1.0
 */
public class BeanConverter {

    /**
     * @description 针对单个Bean的转换方法
     * @author gosling
     */
    public static <S, T> T convert(S source, Class<T> clazz) {
        T target = null;
        try {
            target = clazz.newInstance();
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return target;
    }

    /**
     * @description 针对List中Bean的转换方法
     * @author gosling
     */
    public static <S, T> List<T> convert(List<S> sourceList, Class<T> clazz) {
        List targetList = new ArrayList<T>();
        sourceList.forEach(
                userEntity -> {
                    try {
                        T target = clazz.newInstance();
                        BeanUtils.copyProperties(userEntity, target);
                        targetList.add(target);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        return targetList;
    }
}
