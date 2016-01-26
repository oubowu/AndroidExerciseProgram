package com.oubowu.exerciseprogram.annotations;

//用反射测试进行测试AnnotationUse的定义上是否有@MyAnnotation

// 如果一个注解中有一个名称为value的属性，且你只想设置value属性(即其他属性都采用默认值或者你只有一个value属性)，那么可以省略掉“value=”部分
// 如果数组属性只有一个值，这时候属性值部分可以省略大括号，如：@MyAnnotation(arrayAttr=2)，这就表示数组属性只有一个值，值为2

/**
 * 这里是将新创建好的注解类MyAnnotation标记到AnnotaionTest类上， 并应用了注解类MyAnnotation中定义各种不同类型的的属性
 */
@MyAnnotation(color = "red", value = "赵日天", arrayAttr = { 3, 5,
		6 }, lamp = EumTrafficLamp.PINK, annotationAttr = @MetaAnnotation("叶良辰") )
public class AnnotationUse {

	public static void main(String[] args) {
		// 这里是检查Annotation类是否有注解，这里需要使用反射才能完成对Annotation类的检查
		if (AnnotationUse.class.isAnnotationPresent(MyAnnotation.class)) {
			/*
			 * MyAnnotation是一个类，这个类的实例对象annotation是通过反射得到的，这个实例对象是如何创建的呢？
			 * 一旦在某个类上使用了@MyAnnotation，那么这个MyAnnotation类的实例对象annotation就会被创建出来了
			 * 假设很多人考驾照，教练在有些学员身上贴一些绿牌子、黄牌子，贴绿牌子的表示送礼送得比较多的，
			 * 贴黄牌子的学员表示送礼送得比较少的，不贴牌子的学员表示没有送过礼的，通过这个牌子就可以标识出不同的学员
			 * 教官在考核时一看，哦，这个学员是有牌子的，是送过礼给他的，优先让有牌子的学员过，此时这个牌子就是一个注解
			 * 一个牌子就是一个注解的实例对象，实实在在存在的牌子就是一个实实在在的注解对象，把牌子拿下来(去掉注解)注解对象就不存在了
			 */
			MyAnnotation annotation = AnnotationUse.class.getAnnotation(MyAnnotation.class);
			// 打印MyAnnotation对象
			System.out.println("注解属性为：" + annotation.color());
			System.out.println("注解属性为：" + annotation.value());
			System.out.print("注解属性为：");
			for (int i = 0; i < annotation.arrayAttr().length; i++) {
				System.out.print(annotation.arrayAttr()[i] + ",");
			}
			System.out.println();
			System.out.println("注解属性为：" + annotation.lamp());

			System.out.println("注解属性为：" + annotation.annotationAttr().value());
			
			MetaAnnotation metaAnnotation = annotation.annotationAttr();
			System.out.println("注解属性为：" + metaAnnotation.value());

		}
	}

}

/**
 * 三、@Retention元注解 根据反射的测试的问题，引出@Retention元注解的讲解：其三种取值： RetentionPolicy.SOURCE、
 * RetentionPolicy.CLASS、 RetentionPolicy.RUNTIME
 * 分别对应：Java源文件(.java文件)---->.class文件---->内存中的字节码
 */

/**
 * 四、 Retention注解说明
 * 当在Java源程序上加了一个注解，这个Java源程序要由javac去编译，javac把java源文件编译成.class文件，
 * 在编译成class时可能会把Java源程序上的一些注解给去掉，
 * java编译器(javac)在处理java源程序时，可能会认为这个注解没有用了，于是就把这个注解去掉了，那么此时在编译好的class中就找不到注解了，
 * 这是编译器编译java源程序时对注解进行处理的第一种可能情况，假设java编译器在把java源程序编译成class时，没有把java源程序中的注解去掉，
 * 那么此时在编译好的class中就可以找到注解，当程序使用编译好的class文件时，需要用类加载器把class文件加载到内存中，
 * class文件中的东西不是字节码，
 * class文件里面的东西由类加载器加载到内存中去，类加载器在加载class文件时，会对class文件里面的东西进行处理，如安全检查，
 * 处理完以后得到的最终在内存中的二进制的东西才是字节码，类加载器在把class文件加载到内存中时也有转换，转换时是否把class文件中的注解保留下来，
 * 这也有说法，
 * 所以说一个注解的生命周期有三个阶段：java源文件是一个阶段，class文件是一个阶段，内存中的字节码是一个阶段,javac把java源文件编译成.
 * class文件时， 有可能去掉里面的注解，类加载器把.class文件加载到内存时也有可能去掉里面的注解，
 * 因此在自定义注解时就可以使用Retention注解指明自定义注解的生命周期，
 * 自定义注解的生命周期是在RetentionPolicy.SOURCE阶段(java源文件阶段)，还是在RetentionPolicy.CLASS阶段(
 * class文件阶段)， 或者是在RetentionPolicy.RUNTIME阶段(内存中的字节码运行时阶段)，
 * 根据JDK提供的API可以知道默认是在RetentionPolicy.CLASS阶段 (JDK的API写到：the retention policy
 * defaults to RetentionPolicy.CLASS.)
 */

/*
 * 4.1、@Deprecated
 * 
 * Java API中是这样定义的@Deprecated的 1 @Documented 2 @Retention(value=RUNTIME) 3
 * public @interface Deprecated
 */

/*
 * 4.2、@Override
 * 
 * Java API中是这样定义的@Override的 1 @Target(value=METHOD) 2 @Retention(value=SOURCE)
 * 3 public @interface Override
 * 
 * @Override是给javac(java编译器)看的，编译完以后就@Override注解就没有价值了，@Override注解在源代码中有用，编译成.
 * class文件后@Override注解就没有用了， 因此@Override的Retention的属性值是RetentionPolicy.SOURCE
 */

/*
 * 4.3、@SuppressWarnings
 * 
 * Java API中是这样定义的@SuppressWarnings的
 * 1 @Target(value={TYPE,FIELD,METHOD,PARAMETER,CONSTRUCTOR,LOCAL_VARIABLE})
 * 2 @Retention(value=SOURCE) 3 public @interface SuppressWarnings
 * 
 * @SuppressWarnings是给javac(java编译器)看的，编译器编译完java文件后，@SuppressWarnings注解就没有用了，
 * 所以@SuppressWarnings的Retention的属性值是RetentionPolicy.SOURCE
 */

/*
 * 五、@Target元注解
 * 
 * @Target元注解决定了一个注解可以标识到哪些成分上，如标识在在类身上，或者属性身上，或者方法身上等成分，@Target默认值为任何元素(成分)
 */
