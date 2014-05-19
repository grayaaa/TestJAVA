package DesignPatterns.DynamicProxy.validation.oldpackage;


/**
 * Simple factory class for creating instances of the User interface. In this
 * way, we can shield client code from the implementation of this interface, and
 * provide any implementation we desire without changing the client code.
 */
public class UserFactory {

	private UserFactory() {
	}

	/**
	 * Creates a new User instance.
	 */
	public static User create() {
		// create a new implementation:
		return new UserImpl();
	}

	/**
	 * Demo application.
	 */
	public static void main(String[] args) {
		try {
			User user = UserFactory.create();
			user.setUsername("fred");
			user.setPassword("pw");
			System.out.println(user);

			user = UserFactory.create();
			user.setUsername("barney");
			// user.setPassword("pw");

			/*
			 * set the password to null, this should trigger a validation
			 * failure.
			 */
			user.setPassword(null);
			System.out.println(user);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
