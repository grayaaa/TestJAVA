package DesignPatterns.DynamicProxy.validation.oldpackage;

/**
 * Simple User interface which exposes username and password properties.
 */
public interface User {

	/**
	 * Gets the username of the User.
	 */
	String getUsername();

	/**
	 * Sets the username of the User.
	 * 
	 * @throws ValidationException
	 *             indicates that validation of the proposed username variable
	 *             failed. Contains information about what went wrong.
	 */
	void setUsername(String username) throws ValidationException;

	/**
	 * Gets the password of the User.
	 */
	String getPassword();

	/**
	 * Sets the password of the User.
	 * 
	 * @throws ValidationException
	 *             indicates that validation of the proposed password variable
	 *             failed. Contains information about what went wrong.
	 */
	void setPassword(String password) throws ValidationException;
}
