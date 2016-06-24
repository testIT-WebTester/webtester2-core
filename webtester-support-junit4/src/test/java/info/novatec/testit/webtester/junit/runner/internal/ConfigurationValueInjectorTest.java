package info.novatec.testit.webtester.junit.runner.internal;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;

import org.apache.commons.lang.IllegalClassException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.builders.DefaultConfigurationBuilder;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;


@RunWith(Enclosed.class)
public class ConfigurationValueInjectorTest {

    static Configuration configuration = new DefaultConfigurationBuilder().build();

    public static abstract class AbstractConfigurationValueInjectorTest {
        ConfigurationValueInjector cut = new ConfigurationValueInjector();
    }

    public static class CanInjectValue extends AbstractConfigurationValueInjectorTest {

        @Test
        public void canInjectStringValues() throws NoSuchFieldException {
            assertThat(cut.canInjectValue(field("stringValue"))).isTrue();
        }

        @Test
        public void canInjectIntegerValues() throws NoSuchFieldException {
            assertThat(cut.canInjectValue(field("integerValue"))).isTrue();
        }

        @Test
        public void canInjectLongValues() throws NoSuchFieldException {
            assertThat(cut.canInjectValue(field("longValue"))).isTrue();
        }

        @Test
        public void canInjectFloatValues() throws NoSuchFieldException {
            assertThat(cut.canInjectValue(field("floatValue"))).isTrue();
        }

        @Test
        public void canInjectDoubleValues() throws NoSuchFieldException {
            assertThat(cut.canInjectValue(field("doubleValue"))).isTrue();
        }

        @Test
        public void canInjectBooleanValues() throws NoSuchFieldException {
            assertThat(cut.canInjectValue(field("booleanValue"))).isTrue();
        }

        @Test
        public void cantInjectObjectValues() throws NoSuchFieldException {
            assertThat(cut.canInjectValue(field("objectValue"))).isFalse();
        }

        Field field(String fieldName) throws NoSuchFieldException {
            return CanInjectClass.class.getDeclaredField(fieldName);
        }

        public static class CanInjectClass {
            String stringValue;
            Integer integerValue;
            Long longValue;
            Float floatValue;
            Double doubleValue;
            Boolean booleanValue;
            Object objectValue;
        }

    }

    @RunWith(Enclosed.class)
    public static class InjectStatics {

        public static class StaticFieldInjection {

            @BeforeClass
            public static void injectStaticValues() {
                new ConfigurationValueInjector().injectStatics(configuration, TestClassForStaticFields.class);
            }

            @Test
            public void stringValuesCanBeInjected() {
                assertThat(TestClassForStaticFields.stringValue).isEqualTo("foo bar");
            }

            @Test
            public void integerValuesCanBeInjected() {
                assertThat(TestClassForStaticFields.integerValue).isEqualTo(1);
            }

            @Test
            public void longValuesCanBeInjected() {
                assertThat(TestClassForStaticFields.longValue).isEqualTo(2L);
            }

            @Test
            public void floatValuesCanBeInjected() {
                assertThat(TestClassForStaticFields.floatValue).isEqualTo(1.0f);
            }

            @Test
            public void doubleValuesCanBeInjected() {
                assertThat(TestClassForStaticFields.doubleValue).isEqualTo(2.0d);
            }

            @Test
            public void booleanValuesCanBeInjected() {
                assertThat(TestClassForStaticFields.booleanValue).isEqualTo(true);
            }

            public static class TestClassForStaticFields {
                @ConfigurationValue("test.string")
                static String stringValue;
                @ConfigurationValue("test.integer")
                static Integer integerValue;
                @ConfigurationValue("test.long")
                static Long longValue;
                @ConfigurationValue("test.float")
                static Float floatValue;
                @ConfigurationValue("test.double")
                static Double doubleValue;
                @ConfigurationValue("test.boolean")
                static Boolean booleanValue;
            }

        }

        public static class UnmappedFieldTypes extends AbstractConfigurationValueInjectorTest {

            @Test(expected = IllegalClassException.class)
            public void unmappedFieldTypeOfStaticFieldThrowsException() {
                cut.injectStatics(configuration, UnmappedStaticClass.class);
            }

            public static class UnmappedStaticClass {
                @ConfigurationValue("unknownClass")
                static Object stringValue;
            }

        }

        public static class NonInjectableFields extends AbstractConfigurationValueInjectorTest {

            @Test
            public void nonConfigurationStaticValueFieldsAreIgnored() {

                cut.injectStatics(configuration, NonInjectableStaticClass.class);

                assertThat(NonInjectableStaticClass.classString).isEqualTo("foo bar");
                assertThat(NonInjectableStaticClass.notInjectedClassValue).isNull();

            }

            public static class NonInjectableStaticClass {
                @ConfigurationValue("test.string")
                static String classString;
                static String notInjectedClassValue;
            }

        }

    }

    @RunWith(Enclosed.class)
    public static class Inject {

        public static class InstanceFieldInjection extends AbstractConfigurationValueInjectorTest {

            @Test
            public void stringValuesCanBeInjected() {
                TestClassForInstanceFields instance = new TestClassForInstanceFields();
                cut.inject(configuration, instance);
                assertThat(instance.stringValue).isEqualTo("foo bar");
            }

            @Test
            public void integerValuesCanBeInjected() {
                TestClassForInstanceFields instance = new TestClassForInstanceFields();
                cut.inject(configuration, instance);
                assertThat(instance.integerValue).isEqualTo(1);
            }

            @Test
            public void longValuesCanBeInjected() {
                TestClassForInstanceFields instance = new TestClassForInstanceFields();
                cut.inject(configuration, instance);
                assertThat(instance.longValue).isEqualTo(2L);
            }

            @Test
            public void floatValuesCanBeInjected() {
                TestClassForInstanceFields instance = new TestClassForInstanceFields();
                cut.inject(configuration, instance);
                assertThat(instance.floatValue).isEqualTo(1.0f);
            }

            @Test
            public void doubleValuesCanBeInjected() {
                TestClassForInstanceFields instance = new TestClassForInstanceFields();
                cut.inject(configuration, instance);
                assertThat(instance.doubleValue).isEqualTo(2.0d);
            }

            @Test
            public void booleanValuesCanBeInjected() {
                TestClassForInstanceFields instance = new TestClassForInstanceFields();
                cut.inject(configuration, instance);
                assertThat(instance.booleanValue).isEqualTo(true);
            }

            public static class TestClassForInstanceFields {
                @ConfigurationValue("test.string")
                String stringValue;
                @ConfigurationValue("test.integer")
                Integer integerValue;
                @ConfigurationValue("test.long")
                Long longValue;
                @ConfigurationValue("test.float")
                Float floatValue;
                @ConfigurationValue("test.double")
                Double doubleValue;
                @ConfigurationValue("test.boolean")
                Boolean booleanValue;
            }

        }

        public static class UnmappedFieldTypes extends AbstractConfigurationValueInjectorTest {

            @Test(expected = IllegalClassException.class)
            public void unmappedFieldTypeOfInstanceFieldThrowsException() {
                UnmappedInstanceClass instance = new UnmappedInstanceClass();
                cut.inject(configuration, instance);
            }

            public static class UnmappedInstanceClass {
                @ConfigurationValue("unknownClass")
                Object stringValue;
            }

        }

        public static class NonInjectableFields extends AbstractConfigurationValueInjectorTest {

            @Test
            public void nonConfigurationInstanceValueFieldsAreIgnored() {

                NonInjectableInstanceClass instance = new NonInjectableInstanceClass();
                cut.inject(configuration, instance);

                assertThat(instance.instanceString).isEqualTo("foo bar");
                assertThat(instance.notInjectedInstanceValue).isNull();

            }

            public static class NonInjectableInstanceClass {
                @ConfigurationValue("test.string")
                String instanceString;
                String notInjectedInstanceValue;
            }

        }

    }

}
