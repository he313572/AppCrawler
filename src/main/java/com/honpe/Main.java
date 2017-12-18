package com.honpe;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.println("程序启动!");
		String xmlPath = "ApplicationContext.xml";
		new ClassPathXmlApplicationContext(xmlPath);
	}
}
