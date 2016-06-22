package info.novatec.testit.webtester.junit.runner.internal;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang.IllegalClassException;
import org.junit.Test;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.builders.DefaultConfigurationBuilder;
import info.novatec.testit.webtester.junit.annotations.ConfigurationValue;


public class ConfigurationValueInjectorTest {

    Configuration configuration = new DefaultConfigurationBuilder().build();

    @Test
    public void testInstanceFieldInjection() {

        TestClassForInstance testClass = new TestClassForInstance();
        ConfigurationValueInjector.inject(configuration, testClass);

        assertThat(testClass.stringValue).isEqualTo("foo bar");
        assertThat(testClass.integerValue).isEqualTo(1);
        assertThat(testClass.longValue).isEqualTo(2L);
        assertThat(testClass.floatValue).isEqualTo(1.0f);
        assertThat(testClass.doubleValue).isEqualTo(2.0d);
        assertThat(testClass.booleanValue).isEqualTo(true);

    }

    @Test
    public void testStaticFieldInjection() {

        ConfigurationValueInjector.injectStatics(configuration, TestClassForStatics.class);

        assertThat(TestClassForStatics.stringValue).isEqualTo("foo bar");
        assertThat(TestClassForStatics.integerValue).isEqualTo(1);
        assertThat(TestClassForStatics.longValue).isEqualTo(2L);
        assertThat(TestClassForStatics.floatValue).isEqualTo(1.0f);
        assertThat(TestClassForStatics.doubleValue).isEqualTo(2.0d);
        assertThat(TestClassForStatics.booleanValue).isEqualTo(true);

    }

    @Test(expected = IllegalClassException.class)
    public void testThatUnmappedFieldTypesLeadToException() {

        TestClassForException testClass = new TestClassForException();
        ConfigurationValueInjector.inject(configuration, testClass);

    }

    public static class TestClassForInstance {

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

    public static class TestClassForStatics {

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

    public static class TestClassForException {

        @ConfigurationValue("unknownClass")
        Object stringValue;

    }

}
