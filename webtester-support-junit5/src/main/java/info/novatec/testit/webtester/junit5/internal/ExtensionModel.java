package info.novatec.testit.webtester.junit5.internal;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;
import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;
import info.novatec.testit.webtester.pages.Page;


/**
 * This model stores information about the test class and is used by WebTester's JUnit extensions to exchange data.
 *
 * @since 2.1
 */
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("PMD.UnusedPrivateField")
public class ExtensionModel {

    private static ReflectionUtils reflectionUtils = new ReflectionUtils();

    private List<Field> browserFields;
    private Map<String, Field> namedBrowserFields;
    private List<Field> pageFields;
    private List<Field> configurationValueFields;

    public static ExtensionModel fromTestClass(Class<?> testClass) {

        List<Field> browserFields = getBrowserFields(testClass);

        ExtensionModel model = new ExtensionModel();
        model.setBrowserFields(browserFields);
        model.setNamedBrowserFields(getNamedBrowserFieldsMap(browserFields));
        model.setPageFields(getPageFields(testClass));
        model.setConfigurationValueFields(getConfigurationValueFields(testClass));
        return model;

    }

    private static List<Field> getBrowserFields(Class<?> testClass) {
        return reflectionUtils.allFieldsOfClassHierarchy(testClass)
            .filter(field -> Browser.class.isAssignableFrom(field.getType()))
            .filter(field -> field.isAnnotationPresent(Managed.class))
            .collect(toList());
    }

    private static Map<String, Field> getNamedBrowserFieldsMap(List<Field> browserFields) {
        Set<String> uniqueNames = new HashSet<>();
        Map<String, Field> nameToFieldMap = new HashMap<>();
        browserFields.forEach(field -> {
            String browserName = field.getAnnotation(Managed.class).value();
            if (uniqueNames.contains(browserName)) {
                throw new NonUniqueBrowserNameException();
            }
            uniqueNames.add(browserName);
            nameToFieldMap.put(browserName, field);
        });
        return nameToFieldMap;
    }

    private static List<Field> getPageFields(Class<?> testClass) {
        return reflectionUtils.allFieldsOfClassHierarchy(testClass)
            .filter(field -> Page.class.isAssignableFrom(field.getType()))
            .filter(field -> field.isAnnotationPresent(Initialized.class))
            .peek(ExtensionModel::assertNonStaticPageField)
            .collect(toList());
    }

    private static List<Field> getConfigurationValueFields(Class<?> testClass) {
        return reflectionUtils.allFieldsOfClassHierarchy(testClass)
            .filter(field -> field.isAnnotationPresent(ConfigurationValue.class))
            .peek(ExtensionModel::assertNonStaticConfigurationValueField)
            .collect(toList());
    }

    private static void assertNonStaticPageField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            throw new StaticPageFieldsNotSupportedException(field);
        }
    }

    private static void assertNonStaticConfigurationValueField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            throw new StaticConfigurationValueFieldsNotSupportedException(field);
        }
    }

    public static class NonUniqueBrowserNameException extends RuntimeException {
        public NonUniqueBrowserNameException() {
            super("every browser needs a unique name!");
        }
    }

    public static class StaticPageFieldsNotSupportedException extends RuntimeException {
        public StaticPageFieldsNotSupportedException(Field field) {
            super("@" + Initialized.class.getSimpleName() + " page fields must not be static: " + field);
        }
    }

    public static class StaticConfigurationValueFieldsNotSupportedException extends RuntimeException {
        public StaticConfigurationValueFieldsNotSupportedException(Field field) {
            super("@" + ConfigurationValue.class.getSimpleName() + " fields must not be static: " + field);
        }
    }

}
