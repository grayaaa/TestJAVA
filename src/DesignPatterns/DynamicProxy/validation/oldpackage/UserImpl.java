package DesignPatterns.DynamicProxy.validation.oldpackage;

/**
 * Implementation of the User interface.
 */
public class UserImpl implements User {

	/**
	 * The username of this user.
	 */
	private String username = null;

	/**
	 * The password of this user.
	 */
	private String password = null;

	/**
	 * Gets the username of the User.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username of the User.
	 * 
	 * @throws ValidationException
	 *             indicates that validation of the proposed username variable
	 *             failed. Contains information about what went wrong.
	 */
	public void setUsername(String username) throws ValidationException {
		/*
		 * call the validator. Alternatively, we could make the validator call
		 * directly and remove the base class.
		 */
		BusinessObjectValidationService.validate(this, "setUsername", new Object[] { username });
		this.username = username;
	}

	/**
	 * Gets the password of the User.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password of the User.
	 * 
	 * @throws ValidationException
	 *             indicates that validation of the proposed password variable
	 *             failed. Contains information about what went wrong.
	 */
	public void setPassword(String password) throws ValidationException {
		/*
		 * call the validator. Alternatively, we could make the validator call
		 * directly and remove the base class.
		 */
		BusinessObjectValidationService.validate(this, "setPassword", new Object[] { password });
		this.password = password;
	}

	/**
	 * String representing the username and password of this User.
	 */
	public String toString() {
		return "username: " + username + ", password: " + password;
	}
}
