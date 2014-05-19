package json;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Person {

	public Person() {
	}

	@SerializedName("Person_Name")
	private String name;
	@SerializedName("Person_Sex")
	private String sex;
	@SerializedName("Person_Age")
	private double age;

	@SerializedName("Person_ListUser")
	private List<User> listUser;

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

}
