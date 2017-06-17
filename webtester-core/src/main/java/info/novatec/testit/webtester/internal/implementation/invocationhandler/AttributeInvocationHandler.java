package info.novatec.testit.webtester.internal.implementation.invocationhandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.function.Predicate;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Attribute;


@SuppressWarnings("unchecked")
public class AttributeInvocationHandler implements InvocationHandler {

    private static final Predicate<String> NOT_EMPTY_STRING = value -> !value.isEmpty();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        PageFragment fragment = ( PageFragment ) proxy;
        Attribute annotation = method.getAnnotation(Attribute.class);

        Class<?> methodReturnType = method.getReturnType();

        boolean isOptional = is(methodReturnType, Optional.class);

        Class<?> targetType = isOptional ? getGenericReturnType(method) : methodReturnType;
        String attributeValue = getAttributeValue(fragment, annotation);
        Optional<?> returnValue = convertToTargetType(targetType, attributeValue);

        if (isOptional) {
            return returnValue;
        }
        return returnValue.orElse(null);

    }

    private String getAttributeValue(PageFragment fragment, Attribute annotation) {
        String attributeName = annotation.value();
        return fragment.webElement().getAttribute(attributeName);
    }

    private Class<?> getGenericReturnType(Method method) {
        ParameterizedType genericType = ( ParameterizedType ) method.getGenericReturnType();
        return ( Class<? extends PageFragment> ) genericType.getActualTypeArguments()[0];
    }

    private Optional<?> convertToTargetType(Class<?> targetType, String attributeValue) {

        Optional<String> attribute = Optional.ofNullable(attributeValue);

        if (is(targetType, String.class)) {
            return attribute;
        } else if (is(targetType, Boolean.class)) {
            return attribute.filter(NOT_EMPTY_STRING).map(Boolean::valueOf);
        } else if (is(targetType, Integer.class)) {
            return attribute.filter(NOT_EMPTY_STRING).map(Integer::valueOf);
        } else if (is(targetType, Long.class)) {
            return attribute.filter(NOT_EMPTY_STRING).map(Long::valueOf);
        } else if (is(targetType, Float.class)) {
            return attribute.filter(NOT_EMPTY_STRING).map(Float::valueOf);
        } else if (is(targetType, Double.class)) {
            return attribute.filter(NOT_EMPTY_STRING).map(Double::valueOf);
        }

        throw new IllegalArgumentException("unmapped return type: " + targetType);

    }

    private boolean is(Class<?> returnType, Class<?> type) {
        return type.isAssignableFrom(returnType);
    }

}
