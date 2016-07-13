package info.novatec.testit.webtester.junit5.extensions.configuration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import lombok.AllArgsConstructor;
import lombok.Data;
import utils.TestBrowserFactory;
import utils.TestClassExecutor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.analysis.TestClassAnalysisExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.browsers.ManagedBrowserExtension;
import info.novatec.testit.webtester.junit5.extensions.configuration.valuegetter.TypedConfigurationValueGetter;


public class ConfigurationValueExtensionTest {

    @Test
    @DisplayName("@ConfigurationValue can be injected from single browser")
    void configurationValueCanBeInjectedFromSingleBrowser() throws Exception {
        TestClassExecutor.execute(SingleBrowser.class);
    }

    @Test
    @DisplayName("@ConfigurationValue cen be used to get configuration values from different browsers")
    void configurationValueCanBeInjectedFromMultipleBrowsers() throws Exception {
        TestClassExecutor.execute(MultipleBrowsers.class);
    }

    @Test
    @DisplayName("@ConfigurationValue can use custom getter to resolve injectable value")
    void customConfigurationValueCanBeInjected() throws Exception {
        TestClassExecutor.execute(CustomConfigurationValueType.class);
    }

    @Test
    @DisplayName("@ConfigurationValue needs a managed browser")
    void needsManagedBrowser() throws Exception {
        assertThrows(NoManagedBrowserException.class, () -> {
            TestClassExecutor.execute(NoBrowserClass.class);
        });
    }

    @Test
    @DisplayName("@ConfigurationValue needs a managed browser with matching name")
    void needsManagedBrowserWithMatchingName() throws Exception {
        assertThrows(NoManagedBrowserForNameException.class, () -> {
            TestClassExecutor.execute(WrongBrowserNameClass.class);
        });
    }

    @Test
    @DisplayName("extension does not fail in case it has nothing to do")
    void extensionDoesNotFailIfNothingToDo() throws Exception {
        TestClassExecutor.execute(NoOpClass.class);
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

    private static class CustomConfigurationValueType extends BaseTestClass {

        @Managed
        static Browser browser;

        @ConfigurationValue(value = "key", using = CustomValueGetter.class)
        MyValueType value;

        @BeforeAll
        static void setConfiguration() {
            browser.configuration().setProperty("key", "custom-value");
        }

        @Test
        void customConfigurationValueCanBeInjected() {
            assertThat(value.getValue()).isEqualTo("custom-value");
        }

    }

    @Data
    @AllArgsConstructor
    public static class MyValueType {
        private String value;
    }

    public static class CustomValueGetter implements TypedConfigurationValueGetter {

        @Override
        public boolean canHandle(Class type) {
            return MyValueType.class.equals(type);
        }

        @Override
        public Optional<MyValueType> get(Configuration configuration, String key) {
            return configuration.getStringProperty(key).map(MyValueType::new);
        }
    }

    private static class NoBrowserClass extends BaseTestClass {

        @ConfigurationValue("key")
        String value;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

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

    private static class NoOpClass extends BaseTestClass {

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    @ExtendWith(ConfigurationValueExtension.class)
    private static class BaseTestClass {

    }

}
