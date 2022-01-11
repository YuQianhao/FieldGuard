package com.yuqianhao.fieldguard;

import com.yuqianhao.fieldguard.handler.FieldGuardHandlerImpl;
import com.yuqianhao.fieldguard.handler.IFieldGuardHandler;

public class FieldGuard {

    public static void filter(Object object){
        FieldGuardHandlerImpl.runGuard(object,object.getClass());
    }

    public static void addHandler(Class<?> annotationClass, IFieldGuardHandler fieldGuardHandler){
        FieldGuardHandlerImpl.addHandler(annotationClass,fieldGuardHandler);
    }

    public static void removeHandler(Class<?> annotationClass){
        FieldGuardHandlerImpl.removeHandler(annotationClass);
    }

}
