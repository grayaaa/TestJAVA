package DesignPatterns.DynamicProxy.validation.newpackage;

import java.lang.reflect.Proxy;

/**
 * Simple factory class for creating instances of the User interface.
 * In this way, we can shield client code from the implementation of this
 * interface, and provide any implementation we desire without changing
 * the client code.
 */
public class UserFactory {

    private UserFactory() {
    }

    /**
     * Creates a new User instance.
     */
    public static User create() {
        /* This is a very simple example of the factory create method for the
           User interface.  We could do many more interesting things here,
           like dynamically obtaining the implementation class for the
           User interface and instantiating one (instead of hardcoding
           it to UserImpl as we have done here.)  We could also do the
           same for the InvocationHandler - this way we could choose 
           the implementation of the InvocationHandler at runtime rather
           than always using the BusinessObjectInvocationHandler type.
           
           Also, in this example, the only interface that is implemented is
           the User interface (and all super-interfaces.)  We could
           specify a collection of interfaces to implement here as long as
           our InvocationHandler knew how to handle requests to these
           interfaces.
        */
        return(User)Proxy.newProxyInstance(User.class.getClassLoader(),
                                           new Class[] {User.class, Comparable.class},
                                           new BusinessObjectInvocationHandler(new UserImpl()));
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
            //user.setPassword("pw");

            /* set the password to null, this should trigger a validation
               failure.
            */
            user.setPassword(null);
            System.out.println(user);


        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
