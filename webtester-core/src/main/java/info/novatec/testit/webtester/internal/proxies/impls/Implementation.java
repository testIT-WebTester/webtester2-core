package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public interface Implementation extends InvocationHandler {

    boolean isImplementationFor(Method method);

}
