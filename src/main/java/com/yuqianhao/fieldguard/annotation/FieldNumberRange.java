package com.yuqianhao.fieldguard.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查数字是否在区间，条件格式如下<br>
 * {@link #min()} <= [数字] < {@link #max()}<br>
 * 如果不在这个区间内，将会抛出信息为{@link #message()}的异常<br>
 * 在对非Double或者double使用这个注解时，都会被强制转换为double或者Double，如果转换失败，将会抛出{@link #message()}<br>
 *<br>
 * [例子]<br>
 *<br>
 * * @FieldNumberRange(min=0,max=100,message="数字区间不正确")<br>
 * * double number;<br>
 * * <br>
 * * [1] number=101;<br>
 * * [2] number=99;<br>
 * * <br>
 * * 情况1中的数字符合区间要求，情况2的数字不符合区间要求
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldNumberRange {

    /**
     * 数字区间最小值，包含这个值
     * @return 数字区间的最小值，默认为0
     */
    double min() default 0.0;


    /**
     * 数字区间最大值，不包含这个值
     * @return 数字区间的最大值，默认为0
     */
    double max() default Double.MAX_VALUE;


    /**
     * 异常消息
     * @return 不满足条件时抛出的异常消息
     */
    String message() default "数据不在区间范围内。";

}
