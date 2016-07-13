package info.novatec.testit.webtester.junit5.internal;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;


public class ReflectionUtils {

    public Stream<Field> allFieldsOfClassHierarchy(Class<?> testClass) {
        return getClassHierarchy(testClass).stream()
            .sequential()
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredFields()))
            .peek(field -> field.setAccessible(true));

    }

    public Deque<Class<?>> getClassHierarchy(Class<?> testClass) {
        Deque<Class<?>> classes = new LinkedList<>();
        Class<?> currentClass = testClass;
        while (currentClass != null) {
            classes.push(currentClass);
            currentClass = currentClass.getSuperclass();
        }
        return classes;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Field field, Object instance) {
        try {
            field.setAccessible(true);
            return ( T ) field.get(instance);
        } catch (IllegalAccessException e) {
            // should not happen because field is made accessible
            throw new UndeclaredThrowableException(e);
        }
    }

    public void setValue(Field field, Object instance, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            // should not happen because field is made accessible
            throw new UndeclaredThrowableException(e);
        }
    }

}
