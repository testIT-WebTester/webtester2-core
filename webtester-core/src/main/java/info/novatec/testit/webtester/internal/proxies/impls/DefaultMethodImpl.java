package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DefaultMethodImpl implements Implementation {

    private static Constructor<MethodHandles.Lookup> methodHandlesConstructor;
    static {
        try {
            methodHandlesConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
            if (!methodHandlesConstructor.isAccessible()) {
                methodHandlesConstructor.setAccessible(true);
            }
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return method.isDefault();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.debug("invoking default method '{}' with args: {}", method, args);
        Class<?> declaringClass = method.getDeclaringClass();
        return methodHandlesConstructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
            .unreflectSpecial(method, declaringClass)
            .bindTo(proxy)
            .invokeWithArguments(args);
    }

}
