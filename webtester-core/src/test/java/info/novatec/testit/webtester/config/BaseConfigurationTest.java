package info.novatec.testit.webtester.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.config.exceptions.InvalidValueTypeException;
import info.novatec.testit.webtester.config.exceptions.SetNullValuesException;


@RunWith(Enclosed.class)
public class BaseConfigurationTest {

    private static class BaseTest {
        BaseConfiguration cut = new BaseConfiguration();
    }

    public static class SettingOfProperties extends BaseTest {

        /* setProperty(...) good-cases are part of the "GettingOfProperties" set
         * of tests */

        @Test(expected = SetNullValuesException.class)
        public void testThatSettingNullValueLeadsToException() {
            cut.setProperty("null-value", null);
        }

        @Test(expected = InvalidValueTypeException.class)
        public void testThatSettingInvalidTypeValueLeadsToException() {
            cut.setProperty("invalid-type", this);
        }

    }

    public static class GettingOfProperties extends BaseTest {

        @Test
        public void testSetAndGetString() {
            cut.setProperty("string", "42");
            Optional<String> stringProperty = cut.getStringProperty("string");
            assertThat(stringProperty).contains("42");
        }

        @Test
        public void testSetAndGetInteger() {
            cut.setProperty("integer", 42);
            Optional<Integer> integerProperty = cut.getIntegerProperty("integer");
            assertThat(integerProperty).contains(42);
        }

        @Test
        public void testSetAndGetLong() {
            cut.setProperty("long", 42L);
            Optional<Long> longProperty = cut.getLongProperty("long");
            assertThat(longProperty).contains(42L);
        }

        @Test
        public void testSetAndGetFloat() {
            cut.setProperty("float", 42.0f);
            Optional<Float> floatProperty = cut.getFloatProperty("float");
            assertThat(floatProperty).contains(42.0f);
        }

        @Test
        public void testSetAndGetDouble() {
            cut.setProperty("double", 42.0d);
            Optional<Double> doubleProperty = cut.getDoubleProperty("double");
            assertThat(doubleProperty).contains(42.0d);
        }

        @Test
        public void testSetAndGetBoolean() {
            cut.setProperty("boolean", true);
            Optional<Boolean> booleanProperty = cut.getBooleanProperty("boolean");
            assertThat(booleanProperty).contains(true);
        }

        @Test
        public void testSetAndGetProperty() {
            cut.setProperty("property", "value");
            Optional<Object> property = cut.getProperty("property");
            assertThat(property).contains("value");
        }

    }

    public static class GettingOfDefaultProperties extends BaseTest {

        @Test
        public void testGetStringWithDefault() {
            String stringProperty = cut.getStringProperty("string", "42");
            assertThat(stringProperty).isEqualTo("42");
        }

        @Test
        public void testGetIntegerWithDefault() {
            Integer integerProperty = cut.getIntegerProperty("integer", 42);
            assertThat(integerProperty).isEqualTo(42);
        }

        @Test
        public void testGetLongWithDefault() {
            Long longProperty = cut.getLongProperty("long", 42L);
            assertThat(longProperty).isEqualTo(42L);
        }

        @Test
        public void testGetFloatWithDefault() {
            Float floatProperty = cut.getFloatProperty("float", 42.0f);
            assertThat(floatProperty).isEqualTo(42.0f);
        }

        @Test
        public void testGetDoubleWithDefault() {
            Double doubleProperty = cut.getDoubleProperty("double", 42.0d);
            assertThat(doubleProperty).isEqualTo(42.0d);
        }

        @Test
        public void testGetBooleanWithDefault() {
            Boolean booleanProperty = cut.getBooleanProperty("boolean", true);
            assertThat(booleanProperty).isEqualTo(true);
        }

        @Test
        public void testGetPropertyWithDefault() {
            Object property = cut.getProperty("property", "value");
            assertThat(property).isEqualTo(( Object ) "value");
        }

    }

    public static class RemovalOfProperties extends BaseTest {

        @Test
        public void testRemoveProperty() {
            cut.setProperty("property", "value");
            cut.removeProperty("property");
            assertThat(cut.getProperty("property")).isEmpty();
        }

    }

    public static class GettingOfPropertyKeys extends BaseTest {

        @Test
        public void testGetPropertyKeys() {
            cut.setProperty("property-one", "value");
            cut.setProperty("property-two", "value");
            assertThat(cut.getKeys()).containsOnly("property-one", "property-two");
        }

        @Test(expected = UnsupportedOperationException.class)
        public void testThatKeysAreReturnedAsImmutableSet_Add() {
            cut.setProperty("property-one", "value");
            cut.setProperty("property-two", "value");
            cut.getKeys().add("property-three");
        }

        @Test(expected = UnsupportedOperationException.class)
        public void testThatKeysAreReturnedAsImmutableSet_Remove() {
            cut.setProperty("property-one", "value");
            cut.setProperty("property-two", "value");
            cut.getKeys().remove("property-three");
        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class BehaviorTests extends BaseTest {

        @Mock
        ConfigurationExporter firstExporter;
        @Mock
        ConfigurationExporter secondExporter;

        @Before
        public void addExporters() {
            cut.addExporters(firstExporter, secondExporter);
        }

        @Test
        public void testThatSettingPropertiesCallsExportersInOrder() {

            cut.setProperty("property", "foo");
            cut.setProperty("another-property", "bar");

            InOrder inOrder = inOrder(firstExporter, secondExporter);
            inOrder.verify(firstExporter).export("property", "foo");
            inOrder.verify(secondExporter).export("property", "foo");
            inOrder.verify(firstExporter).export("another-property", "bar");
            inOrder.verify(secondExporter).export("another-property", "bar");
            inOrder.verifyNoMoreInteractions();

        }

    }

}
