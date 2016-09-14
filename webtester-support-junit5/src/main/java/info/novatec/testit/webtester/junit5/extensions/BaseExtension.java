package info.novatec.testit.webtester.junit5.extensions;

import java.lang.reflect.Field;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.function.Consumer;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import lombok.AccessLevel;
import lombok.Getter;

import info.novatec.testit.webtester.junit5.internal.DefaultTestClassModelFactory;
import info.novatec.testit.webtester.junit5.internal.ReflectionUtils;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;
import info.novatec.testit.webtester.junit5.internal.TestClassModelFactory;


/**
 * This is the base class for all of WebTester's JUnit extensions.
 * It provides easy access to the {@link TestClassModel} and some reflection based operations.
 *
 * @since 2.1
 */
@Getter(AccessLevel.PROTECTED)
public class BaseExtension {

    protected static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("testit-webtester");
    protected static final String EXTENSION_MODEL_KEY = "extension-model";

    private final ReflectionUtils reflectionUtils;
    private final TestClassModelFactory testClassModelFactory;

    protected BaseExtension() {
        this(new ReflectionUtils(), new DefaultTestClassModelFactory());
    }

    protected BaseExtension(ReflectionUtils reflectionUtils, TestClassModelFactory testClassModelFactory) {
        this.reflectionUtils = reflectionUtils;
        this.testClassModelFactory = testClassModelFactory;
    }

    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    protected <T extends ExtensionContext> void executeHandlingUndeclaredThrowables(T context, Consumer<T> contextConsumer)
        throws Exception {
        try {
            contextConsumer.accept(context);
        } catch (UndeclaredThrowableException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw ( Exception ) cause;
            } else if (cause instanceof Error) {
                throw ( Error ) cause;
            }
            throw e;
        }
    }

    protected final TestClassModel getModel(ExtensionContext context) {
        Store store = context.getStore(NAMESPACE);
        TestClassModel model = store.get(EXTENSION_MODEL_KEY, TestClassModel.class);
        if (model == null) {
            Class<?> testClass = getTestClassFrom(context);
            model = testClassModelFactory.create(testClass);
            store.put(EXTENSION_MODEL_KEY, model);
        }
        return model;
    }

    private Class<?> getTestClassFrom(ExtensionContext context) {
        return context.getTestClass().orElseThrow(NoTestClassException::new);
    }

    protected final <T> T getValue(Field field, Object instance) {
        return reflectionUtils.getValue(field, instance);
    }

    protected final void setValue(Field field, Object instance, Object value) {
        reflectionUtils.setValue(field, instance, value);
    }

}
