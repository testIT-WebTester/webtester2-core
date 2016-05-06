package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HashCodeImpl implements Implementation {

    private static final String HASH_CODE = "hashCode";

    @Override
    public boolean isImplementationFor(Method method) {
        return HASH_CODE.equals(method.getName());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        return hashCode();
    }

}
