package com.yuqianhao.fieldguard.annotation;

import com.yuqianhao.fieldguard.exception.FieldGuardException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 改变抛出的异常类型<br>
 *<br>
 * [例子]<br>
 *<br>
 * [1]<br>
 * * @FieldNotNull()<br>
 * * String name;<br>
 *<br>
 * [2]<br>
 * * @FieldExceptionClass(exceptionClass=RuntimeException.class)<br>
 * * FieldNotNull()<br>
 * * String name2;<br>
 *<br>
 *  * 情况1发生异常时将抛出{@link FieldGuardException}<br>
 *  * 情况2发生异常时将抛出{@link RuntimeException}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldExceptionClass {

    /**
     * 用于抛出异常的类
     * @return 抛出异常时使用的类
     */
    Class<? extends FieldGuardException> exceptionClass() default FieldGuardException.class;

}
