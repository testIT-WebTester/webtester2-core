package info.novatec.testit.webtester.junit5.extensions.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.TestClassExecutor.execute;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import lombok.AllArgsConstructor;
import lombok.Data;
import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.UnknownConfigurationKeyException;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.browsers.ManagedBrowserExtension;
import info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller.ConfigurationUnmarshaller;


public class ConfigurationValueExtensionIntegrationTest {

    @Test
    @DisplayName("@ConfigurationValue can be injected from single browser")
    void configurationValueCanBeInjectedFromSingleBrowser() throws Exception {
        execute(SingleBrowser.class);
    }

    private static class SingleBrowser extends BaseTestClass {

        @Managed
        static Browser browser;

        @ConfigurationValue("key")
        String value;

        @BeforeAll
        static void setConfiguration() {
            browser.configuration().setProperty("key", "value");
        }

        @Test
        void configurationValueCanBeInjectedFromSingleBrowser() {
            assertThat(value).isEqualTo("value");
        }

    }

    @Test
    @DisplayName("@ConfigurationValue can be used to unmarshal configuration values from different browsers")
    void configurationValueCanBeInjectedFromMultipleBrowsers() throws Exception {
        execute(MultipleBrowsers.class);
    }

    private static class MultipleBrowsers extends BaseTestClass {

        @Managed("primary")
        static Browser primaryBrowser;
        @Managed("secondary")
        static Browser secondaryBrowser;

        @ConfigurationValue(value = "key", source = "primary")
        String primaryValue;
        @ConfigurationValue(value = "key", source = "secondary")
        String secondaryValue;

        @BeforeAll
        static void setConfiguration() {
            primaryBrowser.configuration().setProperty("key", "primary-value");
            secondaryBrowser.configuration().setProperty("key", "secondary-value");
        }

        @Test
        void configurationValueCanBeInjectedFromMultipleBrowsers() {
            assertThat(primaryValue).isEqualTo("primary-value");
            assertThat(secondaryValue).isEqualTo("secondary-value");
        }

    }

    @Test
    @DisplayName("@ConfigurationValue can use custom unmarshaller to resolve injectable value")
    void customConfigurationValueCanBeInjected() throws Exception {
        execute(CustomConfigurationValueType.class);
    }

    private static class CustomConfigurationValueType extends BaseTestClass {

        @Managed
        static Browser browser;

        @ConfigurationValue(value = "key", using = MyTypeUnmarshaller.class)
        MyType value;

        @BeforeAll
        static void setConfiguration() {
            browser.configuration().setProperty("key", "custom-value");
        }

        @Test
        void customConfigurationValueCanBeInjected() {
            assertThat(value.getValue()).isEqualTo("custom-value");
        }

    }

    @Test
    @DisplayName("@ConfigurationValue needs a managed browser")
    void needsManagedBrowser() throws Exception {
        assertThrows(NoManagedBrowserException.class, () -> {
            execute(NoBrowserClass.class);
        });
    }

    private static class NoBrowserClass extends BaseTestClass {

        @ConfigurationValue("key")
        String value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("@ConfigurationValue needs a managed browser with matching name")
    void needsManagedBrowserWithMatchingName() throws Exception {
        NoManagedBrowserForNameException exception = assertThrows(NoManagedBrowserForNameException.class, () -> {
            execute(WrongBrowserNameClass.class);
        });
        assertThat(exception.getName()).isEqualTo("wrong-name");
    }

    private static class WrongBrowserNameClass extends BaseTestClass {

        @Managed("browser")
        Browser browser;
        @ConfigurationValue(value = "key", source = "wrong-name")
        String value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("@ConfigurationValue on default type throws exception if there is no value for the key")
    void throwsExceptionIfNoValueForKey_DefaultType() throws Exception {
        UnknownConfigurationKeyException exception = assertThrows(UnknownConfigurationKeyException.class, () -> {
            execute(UnknownConfigurationKeyDefaultClass.class);
        });
        assertThat(exception.getKey()).isEqualTo("unknown");
    }

    private static class UnknownConfigurationKeyDefaultClass extends BaseTestClass {

        @Managed
        Browser browser;
        @ConfigurationValue("unknown")
        String value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("@ConfigurationValue on custom type throws exception if there is no value for the key")
    void throwsExceptionIfNoValueForKey_CustomType() throws Exception {
        UnknownConfigurationKeyException exception = assertThrows(UnknownConfigurationKeyException.class, () -> {
            execute(UnknownConfigurationKeyCustomClass.class);
        });
        assertThat(exception.getKey()).isEqualTo("unknown");
    }

    private static class UnknownConfigurationKeyCustomClass extends BaseTestClass {

        @Managed
        Browser browser;
        @ConfigurationValue(value = "unknown", using = MyTypeUnmarshaller.class)
        MyType value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("@ConfigurationValue throws exception if no unmarshaller is found")
    void throwsExceptionIfNoUnmarshallerFound() throws Exception {
        NoConfigurationUnmarshallerFoundException exception =
            assertThrows(NoConfigurationUnmarshallerFoundException.class, () -> {
                execute(NoConfigurationUnmarshallerFoundClass.class);
            });
        assertThat(exception.getType()).isEqualTo(MyType.class);
    }

    private static class NoConfigurationUnmarshallerFoundClass extends BaseTestClass {

        @Managed
        Browser browser;
        @ConfigurationValue("some-key")
        MyType value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("@ConfigurationValue throws exception if wrong unmarshaller is declared")
    void throwsExceptionIfWrongUnmarshallerDeclared() throws Exception {
        UnmarshallerCantHandleTypeException exception = assertThrows(UnmarshallerCantHandleTypeException.class, () -> {
            execute(UnmarshallerCantHandleTypeClass.class);
        });
        assertThat(exception.getUnmarshallerType()).isEqualTo(MyTypeUnmarshaller.class);
        assertThat(exception.getType()).isEqualTo(String.class);
    }

    private static class UnmarshallerCantHandleTypeClass extends BaseTestClass {

        @Managed
        Browser browser;
        @ConfigurationValue(value = "wrong-unmarshaller", using = MyTypeUnmarshaller.class)
        String value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("@ConfigurationValue throws exception if used on static field")
    void throwsExceptionIfUsedOnStaticField() throws Exception {
        StaticConfigurationValueFieldsNotSupportedException exception =
            assertThrows(StaticConfigurationValueFieldsNotSupportedException.class, () -> {
                execute(StaticFieldClass.class);
            });
        assertThat(exception.getField()).isEqualTo(StaticFieldClass.class.getDeclaredField("value"));
    }

    private static class StaticFieldClass extends BaseTestClass {

        @Managed
        static Browser browser;
        @ConfigurationValue("static")
        static String value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("extension does not fail in case it has nothing to do")
    void extensionDoesNotFailIfNothingToDo() throws Exception {
        execute(NoOpClass.class);
    }

    private static class NoOpClass extends BaseTestClass {

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(ManagedBrowserExtension.class)
    @ExtendWith(ConfigurationValueExtension.class)
    private static class BaseTestClass {

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
