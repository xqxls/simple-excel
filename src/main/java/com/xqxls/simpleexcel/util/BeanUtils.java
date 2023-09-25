package com.xqxls.simpleexcel.util;

import net.sf.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @USER: xqxls
 * @DATE: 2023/6/8
 */
public class BeanUtils {

    public static Map converMap(Object obj) {
        if (obj == null) {
            return new HashMap<>();
        }

        if (Map.class.isAssignableFrom(obj.getClass())) {
            return (Map) obj;
        }

        return BeanMap.create(obj);
    }
}
