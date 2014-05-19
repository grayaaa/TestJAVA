package DesignPatterns.DynamicProxy.validation.oldpackage;

/**
 * Base exception type for signaling a failed validation attempt.
 */
public class ValidationException extends Exception {

	/**
	 * The id for this exception.
	 */
	private String id = null;

	/**
	 * Create a ValidationException with the given id and message.
	 */
	public ValidationException(String id, String message) {
		super(message);
		this.id = id;
	}

	/**
	 * Get the id for this exception.
	 */
	public String getId() {
		return id;
	}
}
