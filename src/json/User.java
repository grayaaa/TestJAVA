package json;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


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

	 @SerializedName("user_id_id")
	private transient Double id;

	@SerializedName("user_name_")
	private String name;

	// @SerializedName("user.age")
	private Integer age;

	// @SerializedName("user.sex")
	private String sex;

	@Expose
	// @SerializedName("user.type")
	private Boolean type;

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
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
