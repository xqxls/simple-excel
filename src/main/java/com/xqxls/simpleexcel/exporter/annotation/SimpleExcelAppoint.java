package com.xqxls.simpleexcel.exporter.annotation;

import java.lang.annotation.*;

/**
 * @USER: xqxls
 * @DATE: 2023/4/23
 */
@Target({ElementType.FIELD,})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SimpleExcelAppoint {

    /**
     * 实际上会被转换成group
     *
     * tableId命名符合当前std定义
     *
     * @return
     */
    String tableId() default "";


    /**
     * 分组
     *
     * @return
     */
    String group() default "";

    /**
     * 字段名称
     *
     * @return
     */
    String fieldName() default "";


}
