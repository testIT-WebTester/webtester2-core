package info.novatec.testit.webtester.junit.runner.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;
import info.novatec.testit.webtester.junit.annotations.Primary;
import info.novatec.testit.webtester.junit.exceptions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit.exceptions.NoPrimaryBrowserException;
import info.novatec.testit.webtester.junit.exceptions.NoStaticPrimaryBrowserException;
import info.novatec.testit.webtester.junit.exceptions.NoUniquePrimaryBrowserException;
import info.novatec.testit.webtester.junit.utils.ReflectionUtils;


public class TestClassPlausibilityChecker {

    private final Class<?> testClass;
    private final ConfigurationValueInjector injector;

    private Set<Field> allFields;

    public TestClassPlausibilityChecker(Class<?> testClass) {
        this.testClass = testClass;
        this.injector = new ConfigurationValueInjector();
    }

    public void assertPlausibilityOfTestClass() {

        this.allFields = new ReflectionUtils().getAllFieldsOfClassHierarchy(testClass);

        assertThatNoMoreThenOnePrimaryBrowserIsDeclared();

        List<Field> configurationValueFields = getConfigurationValueFields();
        // TODO start page ticket - get all fields
        if (!configurationValueFields.isEmpty()) {
            Field primaryBrowserField = getRequiredPrimaryBrowserInstance();
            assertPlausibilityOfConfigurationValueFields(configurationValueFields, primaryBrowserField);
            // TODO start page ticket - assert plausibility
        }

    }

    private void assertThatNoMoreThenOnePrimaryBrowserIsDeclared() {
        List<Field> managedBrowserFields = getManagedBrowserFields();
        List<Field> primaryBrowserFields = getPrimaryBrowserFields(managedBrowserFields);
        if (primaryBrowserFields.size() > 1) {
            throw new NoUniquePrimaryBrowserException();
        }
    }

    private void assertPlausibilityOfConfigurationValueFields(List<Field> configurationValueFields,
        Field primaryBrowserField) {
        for (Field field : configurationValueFields) {
            injector.assertCanInjectValue(field);
            assertStaticPrimaryBrowserForStaticConfigurationValueField(field, primaryBrowserField);
        }
    }

    /* details */

    private Field getRequiredPrimaryBrowserInstance() {

        List<Field> managedBrowserFields = getManagedBrowserFields();
        if (managedBrowserFields.isEmpty()) {
            throw new NoManagedBrowserException();
        }

        Field primaryBrowserField;
        if (managedBrowserFields.size() == 1) {
            primaryBrowserField = managedBrowserFields.get(0);
        } else {
            List<Field> primaryBrowserFields = getPrimaryBrowserFields(managedBrowserFields);
            if (primaryBrowserFields.isEmpty()) {
                throw new NoPrimaryBrowserException();
            } else {
                primaryBrowserField = primaryBrowserFields.get(0);
            }
        }

        return primaryBrowserField;

    }

    private List<Field> getManagedBrowserFields() {
        return allFields.stream().filter(field -> isBrowser(field) && isManaged(field)).collect(Collectors.toList());
    }

    private boolean isBrowser(Field field) {
        return Browser.class.isAssignableFrom(field.getType());
    }

    private boolean isManaged(Field field) {
        return field.isAnnotationPresent(Resource.class);
    }

    private List<Field> getPrimaryBrowserFields(List<Field> managedBrowserFields) {
        return managedBrowserFields.stream()
            .filter(field -> field.isAnnotationPresent(Primary.class))
            .collect(Collectors.toList());
    }

    private List<Field> getConfigurationValueFields() {
        return allFields.stream()
            .filter(field -> field.isAnnotationPresent(ConfigurationValue.class))
            .collect(Collectors.toList());
    }

    private void assertStaticPrimaryBrowserForStaticConfigurationValueField(Field field, Field primaryBrowserField) {
        if (isStatic(field) && !isStatic(primaryBrowserField)) {
            throw new NoStaticPrimaryBrowserException(
                "the static configuration value field '" + field.getName() + "' needs a static primary browser!");
        }
    }

    private boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

}
