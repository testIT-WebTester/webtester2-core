package info.novatec.testit.webtester.junit5.internal;

import static java.util.stream.Collectors.toList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.browsers.NonUniqueBrowserNameException;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;
import info.novatec.testit.webtester.junit5.extensions.configuration.StaticConfigurationValueFieldsNotSupportedException;
import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;
import info.novatec.testit.webtester.junit5.extensions.pages.StaticPageFieldsNotSupportedException;
import info.novatec.testit.webtester.pages.Page;


class TestClassAnalyzer {

    private final Class<?> testClass;
    private final ReflectionUtils reflectionUtils;

    TestClassAnalyzer(Class<?> testClass, ReflectionUtils reflectionUtils) {
        this.testClass = testClass;
        this.reflectionUtils = reflectionUtils;
    }

    TestClassModel analyze() {
        List<Field> browserFields = getBrowserFields();
        Map<String, Field> namedBrowserFieldsMap = getNamedBrowserFieldsMap(browserFields);
        List<Field> pageFields = getPageFields();
        List<Field> configurationValueFields = getConfigurationValueFields();
        return TestClassModel.builder()
            .browserFields(browserFields)
            .namedBrowserFields(namedBrowserFieldsMap)
            .pageFields(pageFields)
            .configurationValueFields(configurationValueFields)
            .build();
    }

    private List<Field> getBrowserFields() {
        return reflectionUtils.allFieldsOfClassLineage(testClass)
            .filter(field -> Browser.class.isAssignableFrom(field.getType()))
            .filter(field -> field.isAnnotationPresent(Managed.class))
            .collect(toList());
    }

    private Map<String, Field> getNamedBrowserFieldsMap(List<Field> browserFields) {
        Set<String> uniqueNames = new HashSet<>();
        Map<String, Field> nameToFieldMap = new HashMap<>();
        browserFields.forEach(field -> {
            String browserName = field.getAnnotation(Managed.class).value();
            if (uniqueNames.contains(browserName)) {
                throw new NonUniqueBrowserNameException(browserName);
            }
            uniqueNames.add(browserName);
            nameToFieldMap.put(browserName, field);
        });
        return nameToFieldMap;
    }

    private List<Field> getPageFields() {
        return reflectionUtils.allFieldsOfClassLineage(testClass)
            .filter(field -> Page.class.isAssignableFrom(field.getType()))
            .filter(field -> field.isAnnotationPresent(Initialized.class))
            .peek(this::assertNonStaticPageField)
            .collect(toList());
    }

    private List<Field> getConfigurationValueFields() {
        return reflectionUtils.allFieldsOfClassLineage(testClass)
            .filter(field -> field.isAnnotationPresent(ConfigurationValue.class))
            .peek(this::assertNonStaticConfigurationValueField)
            .collect(toList());
    }

    private void assertNonStaticPageField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            throw new StaticPageFieldsNotSupportedException(field);
        }
    }

    private void assertNonStaticConfigurationValueField(Field field) {
        if (Modifier.isStatic(field.getModifiers())) {
            throw new StaticConfigurationValueFieldsNotSupportedException(field);
        }
    }

}
