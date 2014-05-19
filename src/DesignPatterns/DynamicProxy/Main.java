package DesignPatterns.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Main {
	public static void main(String[] args) throws Throwable {
		RealSubject rs = new RealSubject(); // 在这里指定被代理类
		InvocationHandler ds = new DynamicSubject(rs); // 初始化代理类
		Class cls = rs.getClass();

		// 以下是分解步骤
		// Class c = Proxy.getProxyClass(cls.getClassLoader(),
		// cls.getInterfaces());
		// Constructor ct = c.getConstructor(new Class[] {
		// InvocationHandler.class });
		// Subject subject = (Subject) ct.newInstance(new Object[] { ds });

		// 以下是一次性生成
		// Subject subject = (Subject)
		// Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(),
		// ds);

		Subject subject = getProxy(RealSubject.class, ds);

		subject.request();
	}

	private static Subject getProxy(Class<? extends Subject> class1, InvocationHandler invocation) {

		// 注释class1=Subject.class
		// Subject subject = (Subject)
		// Proxy.newProxyInstance(class1.getClassLoader(), new Class[] { class1
		// }, invocation);

		Subject subject = (Subject) Proxy.newProxyInstance(class1.getClassLoader(), class1.getInterfaces(), invocation);

		return subject;

	}
}
