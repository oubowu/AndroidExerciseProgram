package com.oubowu.exerciseprogram.annotations;

/**
 * 此类是用来演示注解(Annotation)的应用的，注解也是JDK1.5新增加的特性之一
 * JDK1.5内部提供的三种注解是：@SuppressWarnings(":deprecation")、@Deprecated、@Override 
 */
/**
 * 类名的命名是有讲究的，类名、属性名、变量名一般是名词，或者是形容词+名词，方法一般是动词，或者是动词+名词，
 * 以AnnotationTest作为类名和以TestAnnotation作为类名是有区别的，
 * 前者是注解的测试，符合名词的特征，后者是测试注解，听起来就是一个动作名称，是方法的命名特征
 */
public class AnnotationTest {

	@SuppressWarnings("deprecation")
	// 这里就是注解，称为压缩警告，这是JDK内部自带的一个注解，一个注解就是一个类，
	// 在这里使用了这个注解就是创建了SuppressWarnings类的一个实例对象
	public static void main(String[] args) {
		// 这里的runFinalizersOnExit()方法画了一条横线表示此方法已经过时了，不建议使用了
		System.runFinalizersOnExit(true);
	}

	@Deprecated
	// 这也是JDK内部自带的一个注解，意思就是说这个方法已经废弃了，不建议使用了
	public static void sayHello() {
		System.out.println("hi,孤傲苍狼");
	}

	@Override
	// 这也是JDK1.5之后内部提供的一个注解，意思就是要重写(覆盖)JDK内部的toString()方法
	public String toString() {
		return "孤傲苍狼";
	}

}

/**
 * 总结：注解(Annotation)相当于一种标记，在程序中加入注解就等于为程序打上某种标记，没有加，
 * 则等于没有任何标记，以后，javac编译器、开发工具和其他程序可以通过反射来了解你的类及各种
 * 元素上有无何种标记，看你的程序有什么标记，就去干相应的事，标记可以加在包、类，属性、方法 , 方法的参数以及局部变量上。
 */

// 注解就相当于一个你的源程序要调用一个类，在源程序中应用某个注解，得事先准备好这个注解类。就像你要调用某个类，得事先开发好这个类。
