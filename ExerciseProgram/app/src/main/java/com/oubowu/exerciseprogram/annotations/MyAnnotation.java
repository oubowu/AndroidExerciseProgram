package com.oubowu.exerciseprogram.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @Retention(RetentionPolicy.SOURCE)
 * 这个注解的意思是让MyAnnotation注解只在java源文件中存在，编译成.class文件后注解就不存在了
 * @Retention(RetentionPolicy.CLASS)
 * 这个注解的意思是让MyAnnotation注解在java源文件(.java文件)中存在，编译成.class文件后注解也还存在，
 * 被MyAnnotation注解类标识的类被类加载器加载到内存中后MyAnnotation注解就不存在了
 */
/*
 * 这里是在注解类MyAnnotation上使用另一个注解类，这里的Retention称为元注解。
 * Retention注解括号中的"RetentionPolicy.RUNTIME"意思是让MyAnnotation这个注解的生命周期一直程序运行时都存在
 */

/**
 * 这是一个自定义的注解(Annotation)类 在定义注解(Annotation)类时使用了另一个注解类Retention
 * 在注解类上使用另一个注解类，那么被使用的注解类就称为元注解
 */
@Retention(RetentionPolicy.RUNTIME)
// Retention注解决定MyAnnotation注解的生命周期
@Target({ ElementType.METHOD, ElementType.TYPE })
// Target注解决定MyAnnotation注解可以加在哪些成分上，如加在类身上，或者属性身上，或者方法身上等成分
public @interface MyAnnotation {

	/*
	 * 六、为注解增加属性
	 * 
	 * 注解可以看成是一种特殊的类，既然是类，那自然可以为类添加属性 6.1.添加属性
	 * 
	 * 语法：类型 属性名();
	 */
	/**
	 * 定义基本属性
	 */
	String color() default "blue";// 为属性指定缺省值
	// 其实从代码的写法上来看，注解更像是一种特殊的接口，注解的属性定义方式就和接口中定义方法的方式一样，而应用了注解的类可以认为是实现了这个特殊的接口

	/**
	 * 为注解添加value属性，这个value属性很特殊，如果一个注解中只有一个value属性要设置，
	 * 那么在设置注解的属性值时，可以省略属性名和等号不写， 直接写属性值，如@SuppressWarnings("deprecation")，
	 * 这里的MyAnnotation注解设置了两个String类型的属性，color和value，
	 * 因为color属性指定有缺省值，value属性又是属于特殊的属性，因此使用MyAnnotation注解时
	 * 可以这样使用MyAnnotation注解："@MyAnnotation(color="red",value="xdp")"
	 * 也可以这样使用："@MyAnnotation("孤傲苍狼")"，这样写就表示MyAnnotation注解只有一个value属性要设置，
	 * color属性采用缺省值 当一个注解只有一个value属性要设置时，是可以省略"value="的
	 */
	String value();// 定义一个名称为value的属性

	// 添加一个int类型数组的属性
	int[] arrayAttr() default { 1, 2, 4 };

	// 添加一个枚举类型的属性，并指定枚举属性的缺省值，缺省值只能从枚举类EumTrafficLamp中定义的枚举对象中取出任意一个作为缺省值
	EumTrafficLamp lamp() default EumTrafficLamp.RED;

	// 为注解添加一个注解类型的属性,并指定注解属性的缺省值
	MetaAnnotation annotationAttr() default @MetaAnnotation("赵日天")
	;

}
