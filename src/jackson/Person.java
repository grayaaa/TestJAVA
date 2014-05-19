package jackson;

import java.util.List;

public class Person {

	public Person() {
	}

	private String name;
	private String sex;
	private double age;
	private List<User> listUser;

	List<Person> childs;

	public Person(String name, double age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", sex=" + sex + ", age=" + age + ", listUser=" + listUser + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getAge() {
		return age;
	}

	public void setAge(double age) {
		this.age = age;
	}

	public List<User> getListUser() {
		return listUser;
	}

	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}

	public List<Person> getChilds() {
		return childs;
	}

	public void setChilds(List<Person> childs) {
		this.childs = childs;
	}

}
