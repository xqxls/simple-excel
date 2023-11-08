package com.xqxls.simpleexcel.util;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @USER: com.xqxls
 * @DATE: 2023/4/25
 */
public class ClassUtils {

    /**
     * 查询某个实例该目标接口的泛型
     *
     * @param handle
     * @return
     */
    @SneakyThrows
    public static Class findModel(Class<?> handleClass, Class targetInterface) {
        Type[] genericInterfaces = handleClass.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                Type rawType = parameterizedType.getRawType();
                if (rawType.equals(targetInterface)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    return Class.forName(actualTypeArguments[0].getTypeName());
                }
            }
        }
        return null;
    }


    /**
     * 查询某个class是否实现了目前类
     *
     * @param obj
     * @param targetClass
     * @return
     */
    public static boolean checkClassInterface(Class obj, Class targetClass) {
        if (obj == targetClass) {
            return true;
        }

        if (obj.isAssignableFrom(targetClass)) {
            return true;
        }

        return false;
    }

    /**
     * 获取一个类上所有的字段，包含父类
     *
     * @param clazz
     * @return
     */
    public static Field[] getAllFields(Class<?> clazz) {
        // Create a list to hold all fields
        List<Field> fields = new ArrayList<Field>();

        // Add all fields declared in this class
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        // Recursively add all fields declared in superclasses
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            fields.addAll(Arrays.asList(getAllFields(superClass)));
        }

        // Convert the list to an array and return it
        return fields.toArray(new Field[fields.size()]);
    }


    /**
     * 查询该类是否包含某个接口
     *
     * @param clazz
     * @param targetInterface
     * @return
     */
    public static boolean hasInterface(Class<?> clazz, Class<?> targetInterface) {
        // 递归向上查找父类，直到找到 Object 类为止
        while (clazz != null && clazz != Object.class) {
            // 获取该类实现的所有接口
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                // 判断该接口是否是目标接口或者是目标接口的子接口
                if (anInterface == targetInterface || targetInterface.isAssignableFrom(anInterface)) {
                    return true;
                }
            }
            // 继续向上查找父类
            clazz = clazz.getSuperclass();
        }
        return false;
    }


    /**
     * 获取该字段的第一个泛型
     *
     * @param field
     * @return
     */
    public static Class<?> findFirstGeneric(Field field) {

        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();

            if (typeArguments.length == 1) {
                Type typeArgument = typeArguments[0];
                if (typeArgument instanceof Class<?>) {
                    return (Class<?>) typeArgument;
                }
            }
        }

        return null;
    }

}
