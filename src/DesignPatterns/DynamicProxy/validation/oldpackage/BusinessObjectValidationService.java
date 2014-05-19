package DesignPatterns.DynamicProxy.validation.oldpackage;

/**
 * Static utility class for validating method calls. In this very simple
 * implementation, we only check to see that all parameters are non-null values
 * - in a real application, this class could delegate validation to a real
 * validation engine, providing configurable rules for acceptable parameters.
 */
public class BusinessObjectValidationService {

	private BusinessObjectValidationService() {
	}

	/**
	 * Validates the parameters for the given method call on the given target.
	 * 
	 * @param target
	 *            the target of the operation. Typically, this is the business
	 *            object implementation.
	 * @param methodName
	 *            the name of the method that was called.
	 * @param parameters
	 *            the parameters on the method call.
	 * @throws ValidationException
	 *             indicates that validation failed. The exception will contain
	 *             information as to what went wrong.
	 */
	public static void validate(Object target, String methodName, Object[] parameters) throws ValidationException {

		/*
		 * The following is an extremely simplified validation check to make
		 * sure none of the parameters are null. If a null parameter is found,
		 * then a validation exception is thrown.
		 * 
		 * This is typically where we would call out to a validation engine
		 * which could find validation rule matches on the given target/method/
		 * parameter list, and run those validations.
		 * 
		 * Another interesting alternative for property changes might be to use
		 * the vetoable change support provided by the JavaBeans architecture to
		 * notify interested listeners that a property change is about to happen
		 * - allowing them to approve/veto the property change.
		 */

		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i] == null) {
					/*
					 * found a null parameter, construct a ValidationException
					 * with the correct id, and a useful text message.
					 */
					throw new ValidationException("null.parameter", "A null parameter was passed to the " + methodName
							+ "() method");
				}
			}
		}
	}
}
