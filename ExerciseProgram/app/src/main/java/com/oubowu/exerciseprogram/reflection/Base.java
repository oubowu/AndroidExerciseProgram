package com.oubowu.exerciseprogram.reflection;

public class Base {

	protected void printClassName() {
		System.out.println("类名为："+getClass().getSimpleName()+"\n");
	}
}
