package com.yuqianhao.fieldguard.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 检查被注解的字段是否是空的字符串，null值或者长度为零的字符串都视为空的字符串<br>
 * 如果尝试将这个注解用于非字符串类型的情况下， 将会自动调用被注解对象的{@link Object#toString()}<br>
 *
 * [例子]<br>
 *
 * * @FieldEmptyText()<br>
 * * String message;<br>
 * *<br>
 * *[1] message=null;<br>
 * *[2] message="";<br>
 * *[3] message="Hello World!";<br>
 * *情况1和2将会抛出异常，3将会通过
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldEmptyText {


    /**
     * 异常消息
     * @return 不满足条件时抛出的异常消息
     */
    String message() default "内容不能是空的。";

}
