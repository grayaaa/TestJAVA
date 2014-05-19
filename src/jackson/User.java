package jackson;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	public User() {
	}

	public User(Double id, String name, Integer age, String sex, Boolean type) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.type = type;
	}

	public User(Double id, String name, Integer age, String sex) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public User(Double id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", type=" + type + "]";
	}

	private transient Double id;

	@JsonProperty(value = "name")
	private String name;

	private Integer age;

	private String sex;

	private Boolean type;

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Boolean getType() {
		return type;
	}

	public void setType(Boolean type) {
		this.type = type;
	}

}
