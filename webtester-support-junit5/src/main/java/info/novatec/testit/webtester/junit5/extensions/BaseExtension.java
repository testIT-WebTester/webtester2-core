package info.novatec.testit.webtester.junit5.extensions;

import java.lang.reflect.Field;

import org.junit.jupiter.api.extension.ExtensionContext;

import lombok.AccessLevel;
import lombok.Getter;

import info.novatec.testit.webtester.junit5.internal.ReflectionUtils;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;


/**
 * This is the base class for all of WebTester's JUnit extensions.
 * It provides easy access to the {@link TestClassModel} and some reflection based operations.
 *
 * @since 2.1
 */
public class BaseExtension {

    protected static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create("testit-webtester");
    protected static final String EXTENSION_MODEL_KEY = "extension-model";

    @Getter(AccessLevel.PROTECTED)
    private final ReflectionUtils reflectionUtils;

    protected BaseExtension() {
        this(new ReflectionUtils());
    }

    protected BaseExtension(ReflectionUtils reflectionUtils) {
        // can only be used by subclasses
        this.reflectionUtils = reflectionUtils;
    }

    protected final void setModel(ExtensionContext context, TestClassModel model) {
        context.getStore(NAMESPACE).put(EXTENSION_MODEL_KEY, model);
    }

    protected final TestClassModel getModel(ExtensionContext context) {
        return context.getStore(NAMESPACE).get(EXTENSION_MODEL_KEY, TestClassModel.class);
    }

    protected final <T> T getValue(Field field, Object instance) {
        return reflectionUtils.getValue(field, instance);
    }

    protected final void setValue(Field field, Object instance, Object value) {
        reflectionUtils.setValue(field, instance, value);
    }

}
