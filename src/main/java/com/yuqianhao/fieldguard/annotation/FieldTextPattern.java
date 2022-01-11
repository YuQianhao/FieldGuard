package com.yuqianhao.fieldguard.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 使用正则表达式来检查文本<br>
 * 检查的文本将会使用正则表达式{@link #pattern()}来验证，如果不通过将会直接抛出{@link #message()}异常<br>
 * 如果尝试将这个主机用于非字符串类型的情况下， 将会自动调用被注解对象的{@link Object#toString()}<br>
 *<br>
 * [例子]<br>
 *<br>
 * * FieldTextPattern(pattern = "abc" , message = "文本检查不通过")<br>
 * * String name;<br>
 * * <br>
 * * [1] name = "123";<br>
 * * [2] name = "abc";<br>
 * * <br>
 * * 情况1将会抛出异常，情况2会检查通过
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FieldTextPattern {

    /**
     * 要检查的正则表达式
     * @return 正则表达式
     */
    String pattern();

    /**
     * 被检查的内容无法通过正则表达式的检查时，将会抛出这个异常
     * @return 抛出的错误信息
     */
    String message() default "内容不符合规则";

}
