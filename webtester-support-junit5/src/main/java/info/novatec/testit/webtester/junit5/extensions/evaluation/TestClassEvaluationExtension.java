package info.novatec.testit.webtester.junit5.extensions.evaluation;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ContainerExtensionContext;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.BaseExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;
import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;
import info.novatec.testit.webtester.junit5.internal.ExtensionModel;
import info.novatec.testit.webtester.pages.Page;


public class TestClassEvaluationExtension extends BaseExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ContainerExtensionContext context) {

        Class<?> testClass =
            context.getTestClass().orElseThrow(() -> new IllegalStateException("No test class available!?"));

        ExtensionModel model = new ExtensionModel();

        List<Field> browserFields = allFieldsOfClassHierarchy(testClass)
            .filter(field -> Browser.class.isAssignableFrom(field.getType()))
            .filter(field -> field.isAnnotationPresent(Managed.class))
            .collect(toList());
        model.setBrowserFields(browserFields);

        List<Field> pageFields = allFieldsOfClassHierarchy(testClass)
            .filter(field -> Page.class.isAssignableFrom(field.getType()))
            .filter(field -> field.isAnnotationPresent(Initialized.class))
            .peek(this::assertNonStaticPageField)
            .collect(toList());
        model.setPageFields(pageFields);

        List<Field> configurationValueFields = allFieldsOfClassHierarchy(testClass)
            .filter(field -> field.isAnnotationPresent(ConfigurationValue.class))
            .peek(this::assertNonStaticConfigurationValueField)
            .collect(toList());
        model.setConfigurationValueFields(configurationValueFields);

        setModel(context, model);

    }

    public void assertNonStaticPageField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            throw new IllegalStateException(
                "@" + Initialized.class.getSimpleName() + " page fields must not be static: " + field);
        }
    }

    public void assertNonStaticConfigurationValueField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            throw new IllegalStateException(
                "@" + ConfigurationValue.class.getSimpleName() + " fields must not be static: " + field);
        }
    }

}
