# 字段警卫

字段警卫使用注解的方式修饰类的字段，能够代替开发人员编写大量的字段检查代码，字段警卫通过反射注解的方式来检查类实例对象的字段是否符合注解的规则。

```java
public class TestClass{
    
    public String password;
    
}
```

通常，我们在使用这个对象实例的时候，需要根据要求检查可空状态和长度状态。

```java
public void method(TestClass test){
    if(test.password==null && test.password.isEmpty()){
        throw new Exception("TestClass对象的password字段不可空。");
    }
    if(test.password.length<6 || test.password.length>16){
        throw new Exception("TestClass对象的password字段长度不正确。");
    }
}
```

而当遇到多处使用这个对象时，都需要编写这些检查代码来检查参数的合法性，工作量将会变得庞大而且重复。字段警卫通过注解的方式，显示的标记在了类的字段上，通过启动字段警卫来自动检查所有字段的合法性。

```java
public class TestClass{
    
    @FieldTextLength(min=6,max=16,message="TestClass对象的password字段长度不正确。")
    @FieldNotNull(message="TestClass对象的password字段不可空。")
    public String password;
    
}
```

这样，检查工作将会变得轻松。

```java
public void method(TestClass test){
    FieldGuard.filter(test);
}
```

当我们需要应对大量的字段和重复的检查工作时，这个字段警卫将会非常有效。

### 1、引用

```xml
<dependency>
  <groupId>com.yuqianhao</groupId>
  <artifactId>FieldGuard</artifactId>
  <version>1.0.4</version>
</dependency>
```

### 2、预设字段警卫注解

字段警卫预设了几个注解，同时开发者可以自定义警卫注解和警卫处理器，我们先来看看预设的注解。

| 注解                      | 说明                                                         |
| ------------------------- | ------------------------------------------------------------ |
| ```FieldEmptyText```      | 检查被注解的字段是否是空的字符串，null值或者长度为零的字符串都视为空的字符串 |
| ```FieldExceptionClass``` | 改变抛出的异常类型                                           |
| ```FieldNotNull```        | 字段是否允许为NULL的检查注解                                 |
| ```FieldNumberRange```    | 检查数字是否在区间                                           |
| ```FieldTextLength```     | 字符串文本长度区间检查                                       |
| ```FieldTextPattern```    | 使用正则表达式来检查文本                                     |

#### （1）、FieldEmptyText

这个注解用于检查被注解的字段是否是空的字符串，null值或者长度为零的字符串都视为空的字符串，如果尝试将它用于非字符串类型的情况下， 将会自动调用被注解对象的```toString()```方法。

该注解只接受一个```message```参数用来获取不满足条件时抛出的异常内容。

```java
@FieldEmptyText(message="Content cannot be empty.")
String message;

[1] message=null;
[2] message="";
[3] message="Hello World!";
```

代码块[1]和[2]都将会触发异常，而[3]将会通过检查。

#### （2）、FieldNotNull

这个注解用于检查字段是否为null。

它只接受一个```message```参数用来获取字段为空是的异常内容。

```java
@FieldNotNull("Object cannot be empty.")
String field;

[1] field == null
[2] field = new Type();
```

代码块[1]将会触发异常，代码块[2]将会通过检查。

#### （3）、FieldNumberRange

这个注解用于检查数字是否在区间。

它接受```min```，```max```和```message```三个参数。```min```表示数字区间最小值，在条件处检查时会包含这个值，```max```表示数字区间最大值，在条件检查时不会包含这个值，```message```参数用来获取不满足其条件的异常内容。

在对非Double或者double使用这个注解时，都会被强制转换为double或者Double，如果转换失败，将会抛出```message```持有的错误信息。

```text
min ≤ [数字] ＜ max
```

```java
@FieldNumberRange(min=0,max=100,message="The number is not in the range.")
double number;

[1] number=101;
[2] number=99;
```

代码块[1]中的数字符合区间要求，代码块[2]的数字不符合区间要求

#### （4）、FieldTextLength

这个注解用来检查字符串文本长度区间。

它接受```min```，```max```和```message```三个参数，```min```表示字符串的最小长度，在条件处检查时会包含这个值，```max```表示字符串的最大长度，在条件检查时不会包含这个值，```message```参数用来获取不满足其条件的异常内容。

如果尝试将这个注解用于非字符串类型的情况下， 将会自动调用被注解对象的```toString()```方法。

```java
@FieldTextLength(min=6,max=16,message="The data length does not conform to the rule.")
String text;

[1] String text="123";
[2] String text2="12345678901234567";
[3] String text3="1234567";
```

代码块[1]和代码块[2]将会抛出异常，代码块[3]将会通过

#### （5）、FieldTextPattern

这个注解用于使用正则表达式来检查文本。

它接受```pattern```和```message```两个参数，前者```pattern```表示要匹配检查的正则表达式，```message```参数用来获取不满足其条件的异常内容。

如果尝试将这个注解用于非字符串类型的情况下， 将会自动调用被注解对象的```toString()```方法。

```java
@FieldTextPattern(pattern = "abc" , message = "Text check failed.")
String name;

[1] name = "123";
[2] name = "abc";
```

代码块[1]将会抛出异常，代码块[2]会检查通过

#### （6）、FieldExceptionClass

这个注解用来改变字段警卫默认抛出的异常类型，字段警卫在触发错误时会使用```FieldGuardException```来作为异常类对外抛出，在某些情况下开发者需要自定义异常类来使用，这个注解可以改变跑出的异常类型，但是自定义的异常类型需要继承```FieldGuardException```异常类，否则字段警卫将会继续抛出```FieldGuardException```而忽略开发者自定义的异常类型。

这个注解只接受一个```exceptionClass```参数，字段注解通过这个参数来获取抛出的异常类。

### 3、开发者自定义警卫注解

在预设的警卫注解不满足开发需求时，开发者可以自定义注解，对于注解的定义格式没有要求，但是开发者需要为每一个定义好的警卫注解添加处理器，这个处理器需要实现```IFieldGuardHandler```接口的```onHandlerField```方法，并在程序开始时使用```FieldGuard```的```addHandler```或者```removeHandler```来添加和删除注解处理器。

```java
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
```

在开发者实现```onHandlerField```处理过程时，检查到字段不符合注解要求，只能使用接口提供的默认方法```throwException```来抛出异常，这个方法会自动为开发者匹配合适的异常类型。

### 4、使用

当我们已经准备好了警卫注解和注解处理器，警卫注解提供了一个类```FieldGuard```来开启这些功能。它提供了```filter```方法，这个方法只接受一个对象，当对某个对象调用这个方法时，警卫注解将对它展开检查。除此之外它还提供了```addHandler```和```removeHandler```方法来增加或者减少开发者自定义的警卫处理器。

一个字段可是使用多个警卫处理器。

```java
@FieldExceptionClass(exceptionClass=ExceptionTest.class)
@FieldNotNull("Object cannot be empty.")
@FieldTextLength(min=6,max=16,message="The data length does not conform to the rule.")
String text;
```

