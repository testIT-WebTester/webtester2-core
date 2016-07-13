package info.novatec.testit.webtester.junit5.extensions;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;

import info.novatec.testit.webtester.junit5.internal.ExtensionModel;


/**
 * This is the base class for all of WebTester's JUnit extensions.
 * It provides easy access to the {@link ExtensionModel} and some reflection based operations.
 *
 * @since 2.1
 */
public class BaseExtension {

    protected static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("testit-webtester");

    protected BaseExtension() {
        // can only be used by subclasses
    }

    protected void setModel(ExtensionContext context, ExtensionModel model) {
        context.getStore(NAMESPACE).put("extension-model", model);
    }

    protected ExtensionModel getModel(ExtensionContext context) {
        return context.getStore(NAMESPACE).get("extension-model", ExtensionModel.class);
    }

    protected Stream<Field> allFieldsOfClassHierarchy(Class<?> testClass) {
        return getClassHierarchy(testClass).stream()
            .sequential()
            .flatMap(aClass -> Arrays.stream(aClass.getDeclaredFields()))
            .peek(field -> field.setAccessible(true));

    }

    private Deque<Class<?>> getClassHierarchy(Class<?> testClass) {
        Deque<Class<?>> classes = new LinkedList<>();
        Class<?> currentClass = testClass;
        while (currentClass != null) {
            classes.push(currentClass);
            currentClass = currentClass.getSuperclass();
        }
        return classes;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getValue(Field field, Object instance) {
        try {
            field.setAccessible(true);
            return ( T ) field.get(instance);
        } catch (IllegalAccessException e) {
            // should not happen because field is made accessible
            throw new UndeclaredThrowableException(e);
        }
    }

    protected void setValue(Field field, Object instance, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            throw new UndeclaredThrowableException(e);
        }
    }

}
