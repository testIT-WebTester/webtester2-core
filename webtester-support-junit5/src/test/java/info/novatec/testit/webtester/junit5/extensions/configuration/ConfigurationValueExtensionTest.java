package info.novatec.testit.webtester.junit5.extensions.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Data;
import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.UnknownConfigurationKeyException;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller.ConfigurationUnmarshaller;
import info.novatec.testit.webtester.junit5.internal.DefaultTestClassModelFactory;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;
import info.novatec.testit.webtester.junit5.internal.TestClassModelFactory;


class ConfigurationValueExtensionTest {

    TestClassModelFactory modelFactory = new DefaultTestClassModelFactory();
    ConfigurationValueExtension cut = new ConfigurationValueExtension();

    @Test
    @DisplayName("@ConfigurationValue needs a managed browser")
    void needsManagedBrowser() {
        assertThrows(NoManagedBrowserException.class, () -> {
            TestClassModel model = modelFactory.create(NoBrowserClass.class);
            NoBrowserClass instance = new NoBrowserClass();
            cut.injectValuesIntoAnnotatedFields(model, instance);
        });
    }

    private static class NoBrowserClass {

        @ConfigurationValue("key")
        String value;

    }

    @Test
    @DisplayName("@ConfigurationValue needs a managed browser with matching name")
    void needsManagedBrowserWithMatchingName() {
        NoManagedBrowserForNameException exception = assertThrows(NoManagedBrowserForNameException.class, () -> {
            TestClassModel model = modelFactory.create(WrongBrowserNameClass.class);
            WrongBrowserNameClass instance = new WrongBrowserNameClass();
            cut.injectValuesIntoAnnotatedFields(model, instance);
        });
        assertThat(exception.getName()).isEqualTo("wrong-name");
    }

    private static class WrongBrowserNameClass {

        @Managed("browser")
        Browser browser;
        @ConfigurationValue(value = "key", source = "wrong-name")
        String value;

    }

    @Test
    @DisplayName("@ConfigurationValue on default type throws exception if there is no value for the key")
    void throwsExceptionIfNoValueForKey_DefaultType() {
        UnknownConfigurationKeyException exception = assertThrows(UnknownConfigurationKeyException.class, () -> {
            TestClassModel model = modelFactory.create(UnknownConfigurationKeyDefaultClass.class);
            UnknownConfigurationKeyDefaultClass instance = new UnknownConfigurationKeyDefaultClass();
            cut.injectValuesIntoAnnotatedFields(model, instance);
        });
        assertThat(exception.getKey()).isEqualTo("unknown");
    }

    private static class UnknownConfigurationKeyDefaultClass {

        @Managed
        Browser browser = new TestBrowserFactory().createBrowser();
        @ConfigurationValue("unknown")
        String value;

    }

    @Test
    @DisplayName("@ConfigurationValue on custom type throws exception if there is no value for the key")
    void throwsExceptionIfNoValueForKey_CustomType() {
        UnknownConfigurationKeyException exception = assertThrows(UnknownConfigurationKeyException.class, () -> {
            TestClassModel model = modelFactory.create(UnknownConfigurationKeyCustomClass.class);
            UnknownConfigurationKeyCustomClass instance = new UnknownConfigurationKeyCustomClass();
            cut.injectValuesIntoAnnotatedFields(model, instance);
        });
        assertThat(exception.getKey()).isEqualTo("unknown");
    }

    private static class UnknownConfigurationKeyCustomClass {

        @Managed
        Browser browser = new TestBrowserFactory().createBrowser();
        @ConfigurationValue(value = "unknown", using = MyTypeUnmarshaller.class)
        MyType value;

    }

    @Test
    @DisplayName("@ConfigurationValue throws exception if no unmarshaller is found")
    void throwsExceptionIfNoUnmarshallerFound() {
        NoConfigurationUnmarshallerFoundException exception =
            assertThrows(NoConfigurationUnmarshallerFoundException.class, () -> {
                TestClassModel model = modelFactory.create(NoConfigurationUnmarshallerFoundClass.class);
                NoConfigurationUnmarshallerFoundClass instance = new NoConfigurationUnmarshallerFoundClass();
                cut.injectValuesIntoAnnotatedFields(model, instance);
            });
        assertThat(exception.getType()).isEqualTo(MyType.class);
    }

    private static class NoConfigurationUnmarshallerFoundClass {

        @Managed
        Browser browser = new TestBrowserFactory().createBrowser();
        @ConfigurationValue("some-key")
        MyType value;

    }

    @Test
    @DisplayName("@ConfigurationValue throws exception if wrong unmarshaller is declared")
    void throwsExceptionIfWrongUnmarshallerDeclared() {
        UnmarshallerCantHandleTypeException exception = assertThrows(UnmarshallerCantHandleTypeException.class, () -> {
            TestClassModel model = modelFactory.create(UnmarshallerCantHandleTypeClass.class);
            UnmarshallerCantHandleTypeClass instance = new UnmarshallerCantHandleTypeClass();
            cut.injectValuesIntoAnnotatedFields(model, instance);
        });
        assertThat(exception.getUnmarshallerType()).isEqualTo(MyTypeUnmarshaller.class);
        assertThat(exception.getType()).isEqualTo(String.class);
    }

    private static class UnmarshallerCantHandleTypeClass {

        @Managed
        Browser browser = new TestBrowserFactory().createBrowser();
        @ConfigurationValue(value = "wrong-unmarshaller", using = MyTypeUnmarshaller.class)
        String value;

    }

    @Test
    @DisplayName("@ConfigurationValue throws exception if used on static field")
    void throwsExceptionIfUsedOnStaticField() throws Exception {
        StaticConfigurationValueFieldsNotSupportedException exception =
            assertThrows(StaticConfigurationValueFieldsNotSupportedException.class, () -> {
                TestClassModel model = modelFactory.create(StaticFieldClass.class);
                StaticFieldClass instance = new StaticFieldClass();
                cut.injectValuesIntoAnnotatedFields(model, instance);
            });
        assertThat(exception.getField()).isEqualTo(StaticFieldClass.class.getDeclaredField("value"));
    }

    private static class StaticFieldClass {

        @Managed
        static Browser browser;
        @ConfigurationValue("static")
        static String value;

    }

    @Data
    @AllArgsConstructor
    public static class MyType {
        private String value;
    }

    public static class MyTypeUnmarshaller implements ConfigurationUnmarshaller {

        @Override
        public boolean canHandle(Class type) {
            return MyType.class.equals(type);
        }

        @Override
        public Optional<MyType> unmarshal(Configuration configuration, String key) {
            return configuration.getStringProperty(key).map(MyType::new);
        }

    }

}
