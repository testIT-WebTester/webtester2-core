package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.util.Optional;

import info.novatec.testit.webtester.internal.annotations.ReturnsName;


public class NameReturningImpl implements Implementation {

    private final Optional<String> name;

    public NameReturningImpl(Optional<String> name) {
        this.name = name;
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return method.isAnnotationPresent(ReturnsName.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return name;
    }

}
