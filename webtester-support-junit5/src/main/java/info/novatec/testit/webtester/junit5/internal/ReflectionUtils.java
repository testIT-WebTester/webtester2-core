package info.novatec.testit.webtester.junit5.internal;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


public class ReflectionUtils {

    public Stream<Field> allFieldsOfClassLineage(Class<?> testClass) {
        return getClassLineage(testClass).stream()
            .sequential()
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredFields()))
            .peek(field -> field.setAccessible(true));

    }

    /**
     * Returns the given classes lineage (up to the {@link Object} class) as a list. The list is sorted in a way that the
     * root class (last ancestor of {@code testClass} is it's first element.
     *
     * @param testClass the class who's lineage should be returned
     * @return the list of classes
     * @since 2.1
     */
    public List<Class<?>> getClassLineage(Class<?> testClass) {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> currentClass = testClass;
        while (currentClass != null) {
            classes.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }
        Collections.reverse(classes);
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
