package test;

import java.io.Serializable;

class User implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String name;
	String age;

	public User(String name, String age) {
		this.name = name;
		this.age = age;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + "]";
	}

}
