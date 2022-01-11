package com.yuqianhao.fieldguard.handler;

import com.yuqianhao.fieldguard.annotation.*;
import com.yuqianhao.fieldguard.exception.FieldGuardException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FieldGuardHandlerImpl {

    private static final Map<String,Pattern> STRING_PATTERN_MAP=new HashMap<>();

    private static final Map<Class<?>, IFieldGuardHandler> ANNOTATION_I_FIELD_GUARD_HANDLER_MAP = new HashMap<>();

    public static void addHandler(Class<?> annotationClass,IFieldGuardHandler handler){
        ANNOTATION_I_FIELD_GUARD_HANDLER_MAP.remove(annotationClass);
        ANNOTATION_I_FIELD_GUARD_HANDLER_MAP.put(annotationClass, handler);
    }

    public static void removeHandler(Class<?> annotationClass){
        ANNOTATION_I_FIELD_GUARD_HANDLER_MAP.remove(annotationClass);
    }

    public static void runGuard(Object object,Class<?> objectClass){
        Field[] fields=objectClass.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Annotation[] annotations=field.getAnnotations();
            Object value;
            try {
                value=field.get(object);
            }  catch (IllegalAccessException e) {
                throw new RuntimeException(e.getMessage());
            }
            for(Annotation annotation : annotations){
                handlerGuard(object,value,field,annotation);
            }
        }
        if(objectClass.getSuperclass() != null && !objectClass.getSuperclass().equals(Object.class)){
            runGuard(object,objectClass.getSuperclass());
        }
    }

    private static void handlerGuard(Object object,Object value,Field field,Annotation annotation){
        if(annotation instanceof FieldEmptyText){
            FieldEmptyText  fieldEmptyText= (FieldEmptyText) annotation;
            if(value==null || value.toString().length()!=0){
                throwException(field, fieldEmptyText.message());
            }
        }
        if(annotation instanceof FieldNotNull){
            FieldNotNull fieldNotNull= (FieldNotNull) annotation;
            if(value==null){
                throwException(field,fieldNotNull.message());
            }
        }
        if(annotation instanceof FieldNumberRange){
            FieldNumberRange fieldNumberRange= (FieldNumberRange) annotation;
            if(value==null){
                throwException(field, fieldNumberRange.message());
            }else{
                try{
                    double _doubleValue=Double.parseDouble(value.toString());
                    if(_doubleValue< fieldNumberRange.min() || _doubleValue > fieldNumberRange.max()){
                        throwException(field, fieldNumberRange.message());
                    }
                }catch (Exception e){
                    throwException(field, fieldNumberRange.message());
                }
            }
        }
        if(annotation instanceof FieldTextLength){
            FieldTextLength fieldTextLength= (FieldTextLength) annotation;
            if(value==null){
                throwException(field, fieldTextLength.message());
            }else{
                String textValue=value.toString();
                if(textValue.length()< fieldTextLength.min() || textValue.length() > fieldTextLength.max()){
                    throwException(field, fieldTextLength.message());
                }
            }
        }
        if(annotation instanceof FieldTextPattern){
            FieldTextPattern fieldTextPattern= (FieldTextPattern) annotation;
            if(value==null){
                throwException(field, fieldTextPattern.message());
            }else{
                String textValue=value.toString();
                if(!STRING_PATTERN_MAP.containsKey(fieldTextPattern.pattern())){
                    STRING_PATTERN_MAP.put(fieldTextPattern.pattern(),Pattern.compile(fieldTextPattern.pattern()));
                }
                Pattern pattern=STRING_PATTERN_MAP.get(fieldTextPattern.pattern());
                if(!pattern.matcher(textValue).matches()){
                    throwException(field, fieldTextPattern.message());
                }
            }
        }
        if(ANNOTATION_I_FIELD_GUARD_HANDLER_MAP.containsKey(annotation.getClass())){
            ANNOTATION_I_FIELD_GUARD_HANDLER_MAP.get(annotation.getClass()).onHandlerField(object,value,field,annotation);
        }
    }

    protected static void throwException(Field field,String message){
        FieldExceptionClass fieldExceptionClass=field.getAnnotation(FieldExceptionClass.class);
        if(fieldExceptionClass != null){
            try {
                throw fieldExceptionClass.exceptionClass().getConstructor(String.class).newInstance(message);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e.getMessage());
            }
        }else{
            throw new FieldGuardException(message);
        }
    }

}
