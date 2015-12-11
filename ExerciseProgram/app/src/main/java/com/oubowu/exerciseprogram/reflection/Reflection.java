package test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Class<?> class1 = Class.forName("test.Sub");
		Base base = (Base) class1.newInstance();
		base.printClassName();

		Class<?> clz = Class.forName("test.Sub");
		Sub sub = (Sub) clz.newInstance();

		Field field = clz.getDeclaredField("str");
		field.setAccessible(true);
		System.out.println(field.get(sub)+"\n");

		Method method1 = clz.getDeclaredMethod("setStr", String.class);
		method1.setAccessible(true);
		method1.invoke(sub, "这是反射修改私有的字符串\n");

		Method method = clz.getDeclaredMethod("print");
		method.setAccessible(true);
		method.invoke(sub);

		Constructor<?>[] constructors = clz.getConstructors();
		for (int i = 0; i < constructors.length; i++) {
			System.out.println("构造函数：" + constructors[i]);
			System.out.println("构造函数参数：");
			for (int j = 0; j < constructors[i].getParameterTypes().length; j++) {
				System.out.print(constructors[i].getParameterTypes()[j] + " - ");
			}
			System.out.println("\n");
		}
	}

}
