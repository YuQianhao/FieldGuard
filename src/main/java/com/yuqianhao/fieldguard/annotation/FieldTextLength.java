package com.yuqianhao.fieldguard.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字符串文本长度区间检查<br>
 * 当字符串不满足于条件{@link #min()}和{@link #max()}时，将会通过异常将${@link #message()}消息抛出。<br>
 * 如果尝试将这个注解用于非字符串类型的情况下， 将会自动调用被注解对象的{@link Object#toString()}<br>
 *<br>
 * [例如]<br>
 *<br>
 * * FieldTextLength(min=6,max=16,message="数据长度不符合规则")<br>
 * * String text;<br>
 * * <br>
 * * [1] String text="123";<br>
 * * [2] String text2="12345678901234567";<br>
 * * [3] String text3="1234567";<br>
 * * 情况1和2将会抛出异常，情况3将会通过
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldTextLength {

    /**
     * 字符串最小匹配的长度
     * 当字符串的长度小于这个值时，将会触发异常消息{@link #message()}
     * @return 字符串最小长度，默认为0
     */
    int min() default 0;

    /**
     * 字符串最大匹配的长度
     * 当字符串超过这个长度时，将会触发异常消息{@link #message()}
     * @return 字符串最大的长度，默认为{@link Integer#MAX_VALUE}
     */
    int max() default Integer.MAX_VALUE;

    /**
     * 异常消息
     * @return 不满足条件时抛出的异常消息
     */
    String message();

}
