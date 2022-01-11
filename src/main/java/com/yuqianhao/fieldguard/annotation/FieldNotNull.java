package com.yuqianhao.fieldguard.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段是否允许为NULL的检查注解。<br>
 * 当字段被注解时，会检查字段的是否为空，如果字段是空的将会抛出{@link #message()}文本内容的异常。<br>
 *<br>
 * [例如]<br>
 *<br>
 * * @FieldNotNull("错误消息")<br>
 * * String field;<br>
 * * <br>
 * * [1] field == null<br>
 * * [2] field = new Type();<br>
 * * <br>
 * * 当情况1时，将会抛出这个异常消息<br>
 * * 情况2时，将通过验证
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldNotNull{

    /**
     * 字段为null时触发的异常信息
     * @return 异常信息
     */
    String message();

}
