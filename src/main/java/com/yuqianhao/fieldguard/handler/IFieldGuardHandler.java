package com.yuqianhao.fieldguard.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 字段过滤器的接口
 */
public interface IFieldGuardHandler {

    /**
     * 字段处理方法，字段警卫会将要处理的字段传递给这个方法
     * @param object        要处理的对象
     * @param value         要处理的字段
     * @param field         要处理的字段描述信息
     * @param annotation    要处理的注解
     */
    void onHandlerField(Object object, Object value, Field field, Annotation annotation);

    /**
     * 抛出字段检查的异常
     * @param field     要抛出异常的字段描述
     * @param message   异常的错误信息
     */
    default void throwException(Field field,String message){
        FieldGuardHandlerImpl.throwException(field,message);
    }

}
