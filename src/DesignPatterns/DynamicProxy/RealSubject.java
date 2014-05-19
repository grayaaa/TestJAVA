package DesignPatterns.DynamicProxy;

//具体角色RealSubject：实现了Subject接口的request()方法。
public class RealSubject implements Subject {
	public RealSubject() {
	}

	@Override
	public void request() {
		System.out.println("From real subject");

	}

}
