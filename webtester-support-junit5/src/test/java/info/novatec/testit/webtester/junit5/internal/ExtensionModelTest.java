package info.novatec.testit.webtester.junit5.internal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;
import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;
import info.novatec.testit.webtester.junit5.internal.ExtensionModel.NonUniqueBrowserNameException;
import info.novatec.testit.webtester.junit5.internal.ExtensionModel.StaticConfigurationValueFieldsNotSupportedException;
import info.novatec.testit.webtester.junit5.internal.ExtensionModel.StaticPageFieldsNotSupportedException;
import info.novatec.testit.webtester.pages.Page;


public class ExtensionModelTest {

    @Test
    @DisplayName("fromTestClass(Class) builds model for 'correct' test class")
    void modelCanBeBuildForCorrectTestClass() {

        ExtensionModel model = ExtensionModel.fromTestClass(HappyTestClass.class);

        assertThat(model.getBrowserFields()).hasSize(2);
        assertThat(model.getNamedBrowserFields()).hasSize(2);
        assertThat(model.getNamedBrowserFields()).containsKeys("browser-1", "browser-2");
        assertThat(model.getPageFields()).hasSize(2);
        assertThat(model.getConfigurationValueFields()).hasSize(2);

    }

    @Test
    @DisplayName("multiple managed browsers must have names")
    void multipleManagedBrowsersMustHaveNames() {
        assertThrows(NonUniqueBrowserNameException.class, () -> {
            ExtensionModel.fromTestClass(MultiBrowserWithoutNameTestClass.class);
        });
    }

    @Test
    @DisplayName("multiple managed browsers must have UNIQUE names")
    void multipleManagedBrowsersMustHaveUniqueNames() {
        assertThrows(NonUniqueBrowserNameException.class, () -> {
            ExtensionModel.fromTestClass(MultiBrowserWithSameNameTestClass.class);
        });
    }

    @Test
    @DisplayName("static page fields are not supported")
    void staticPageFieldsAreNotSupported() {
        assertThrows(StaticPageFieldsNotSupportedException.class, () -> {
            ExtensionModel.fromTestClass(StaticPageTestClass.class);
        });
    }

    @Test
    @DisplayName("static configuration value fields are not supported")
    void staticConfigurationValueFieldsAreNotSupported() {
        assertThrows(StaticConfigurationValueFieldsNotSupportedException.class, () -> {
            ExtensionModel.fromTestClass(StaticConfigurationValueTestClass.class);
        });
    }

    public static class HappyTestClass {

        @Managed("browser-1")
        static Browser browser1;
        @Managed("browser-2")
        Browser browser2;

        @Initialized
        Page page1;
        @Initialized
        Page page2;

        @ConfigurationValue("key1")
        String value1;
        @ConfigurationValue("key2")
        String value2;

    }

    public static class MultiBrowserWithoutNameTestClass {

        @Managed
        static Browser browser1;
        @Managed
        Browser browser2;

    }

    public static class MultiBrowserWithSameNameTestClass {

        @Managed("name")
        static Browser browser1;
        @Managed("name")
        Browser browser2;

    }

    public static class StaticPageTestClass {

        @Initialized
        static Page page;

    }

    public static class StaticConfigurationValueTestClass {

        @ConfigurationValue("key1")
        static String value1;

    }

}
