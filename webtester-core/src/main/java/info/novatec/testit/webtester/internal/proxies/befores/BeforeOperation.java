package info.novatec.testit.webtester.internal.proxies.befores;

import java.lang.reflect.Method;


public interface BeforeOperation {

    boolean shouldBeInvokedFor(Method method);

    void invoke(Object proxy, Method method, Object[] args);

}
