package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ToStringImpl implements Implementation {

    private static final String TO_STRING = "toString";

    private final Class<?> type;

    public ToStringImpl(Class<?> type) {
        this.type = type;
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return TO_STRING.equals(method.getName());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return type.getName() + "$$Proxy" + hashCode();
    }

}
