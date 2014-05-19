package DesignPatterns.DynamicProxy.validation.newpackage;

/**
 * Implementation of the User interface.
 */
public class UserImpl implements User {

    /**
     * The username of this User.
     */
    private String username = null;

    /**
     * The password of this User.
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
     */
    public void setUsername(String username) {
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
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * String representing the username and password of this User.
     */
    public String toString() {
        return "username: " + username + ", password: " + password;
    }
}
