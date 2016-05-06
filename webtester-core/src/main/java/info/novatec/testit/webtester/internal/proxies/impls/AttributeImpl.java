package info.novatec.testit.webtester.internal.proxies.impls;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.function.Supplier;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.annotations.Attribute;


public class AttributeImpl implements Implementation {

    private final Supplier<WebElement> webElementSupplier;

    public AttributeImpl(Supplier<WebElement> webElementSupplier) {
        this.webElementSupplier = webElementSupplier;
    }

    @Override
    public boolean isImplementationFor(Method method) {
        return method.isAnnotationPresent(Attribute.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Attribute annotation = method.getAnnotation(Attribute.class);
        Class<?> type = method.getReturnType();
        if (isOptional(type)) {
            return getOptionalAttributeValue(annotation, getGenericType(method));
        }
        return getAttributeValue(annotation, type);
    }

    public boolean isOptional(Class<?> type) {
        return Optional.class.isAssignableFrom(type);
    }

    protected static Class<?> getGenericType(Method method) {
        ParameterizedType genericType = ( ParameterizedType ) method.getGenericReturnType();
        return ( Class<?> ) genericType.getActualTypeArguments()[0];
    }

    public Object getAttributeValue(Attribute attributeAnnotation, Class<?> type) {
        return getOptionalAttributeValue(attributeAnnotation, type).orElse(null);
    }

    public Optional<?> getOptionalAttributeValue(Attribute attributeAnnotation, Class<?> attributeType) {
        Optional<String> attribute = getAttribute(attributeAnnotation);
        if (is(attributeType, String.class)) {
            return attribute;
        }
        if (is(attributeType, Boolean.class)) {
            return attribute.filter(this::nonEmptyString).map(Boolean::valueOf);
        }
        if (is(attributeType, Integer.class)) {
            return attribute.filter(this::nonEmptyString).map(Integer::valueOf);
        }
        if (is(attributeType, Long.class)) {
            return attribute.filter(this::nonEmptyString).map(Long::valueOf);
        }
        if (is(attributeType, Float.class)) {
            return attribute.filter(this::nonEmptyString).map(Float::valueOf);
        }
        if (is(attributeType, Double.class)) {
            return attribute.filter(this::nonEmptyString).map(Double::valueOf);
        }
        throw new IllegalArgumentException("unmapped return type: " + attributeType);
    }

    private Optional<String> getAttribute(Attribute annotation) {
        return Optional.ofNullable(webElementSupplier.get().getAttribute(annotation.value()));
    }

    private boolean is(Class<?> returnType, Class<?> type) {
        return type.isAssignableFrom(returnType);
    }

    private boolean nonEmptyString(String value) {
        return !value.isEmpty();
    }

}
