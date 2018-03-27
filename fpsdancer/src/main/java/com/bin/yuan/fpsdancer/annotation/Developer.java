package com.bin.yuan.fpsdancer.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yuanbin on 2018/3/26.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Developer {

    /***
     * 开发者id
     * @return
     */
    String id();

    /***
     * 开发者
     * @return
     */
    String name();

    /***
     * 部门id
     * @return
     */
    String departmentId() default "0001";

    /***
     * 部门名称
     * @return
     */
    String departmentName() default "研发部门";

    /***
     * 描述
     * @return
     */
    String description() default "";


    /***
     *  json格式数据
     * @return
     */
    String formatStr() default "{}";

    /***
     * 是否用formatStr
     * @return
     */
    boolean useFormatStr() default false;
}
