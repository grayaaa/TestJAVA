package DesignPatterns.DynamicProxy.validation.newpackage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * An implementation of the InvocationHandler interface that first attempts
 * to validate the method call target/method/parameters.  If successful,
 * the method invocation is delegated to a specified delegate Object, otherwise
 * a ValidationException is thrown as a result of invoking the method on a 
 * Proxy object.
 */
public class BusinessObjectInvocationHandler implements InvocationHandler {

    /**
     * This is the object to which methods are delegated if they are not
     * handled directly by this invocation handler.  Typically, this is the
     * real implementation of the business object interface.
     */
    private Object delegate = null;

    /**
     * Create a new invocation handler for the given delegate.
     * @param delegate the object to which method calls are delegated if
     * they are not handled directly by this invocation handler.
     */
    public BusinessObjectInvocationHandler(Object delegate) {
        this.delegate = delegate;
    }

    /**
     * Processes a method call.
     * @param proxy the proxy instance upon which the method was called.
     * @param method the method that was invoked.
     * @param args the arguments to the method call.
     */
    public Object invoke(Object proxy, Method method, Object[] args) 
    throws Throwable {

        // call the validator:
        BusinessObjectValidationService.validate(proxy, method.getName(), args);

        // could perform any other method pre-processing routines here...

        /* validation succeeded, so invoke the method on the delegate.  We
           only catch the InvocationTargetException here so that we can
           unwrap it and throw the contained target exception.  If a checked 
           exception is thrown by this method that is not assignable to any of 
           the exception types declared in the throws clause of the interface 
           method, then an UndeclaredThrowableException containing the 
           exception that was thrown by this method will be thrown by the 
           method invocation on the proxy instance.
        */
        Object retVal = null;
        try {
            retVal = method.invoke(delegate, args);
        } catch (InvocationTargetException ite) {
            /* the method invocation threw an exception, so "unwrap" and
               throw.
            */
            throw ite.getTargetException();
        }

        // could do method post-processing routines here if necessary...

        return retVal;
    }
}
