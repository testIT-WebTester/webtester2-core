package info.novatec.testit.webtester.junit5.extensions.browsers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import utils.TestBrowserFactory;
import utils.TestClassExecutor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.exceptions.NoBrowserFactoryException;
import info.novatec.testit.webtester.junit5.extensions.analysis.TestClassAnalysisExtension;


public class ManagedBrowserExtensionIntegrationTest {

    @Test
    @DisplayName("@Managed on static browser field injects new browser")
    void idealStaticBrowserInjectionCase() throws Exception {
        TestClassExecutor.execute(StaticBrowser.class);
    }

    @Test
    @DisplayName("@Managed on static browser fields injects new instance for each field")
    void idealStaticMultiBrowserInjectionCase() throws Exception {
        TestClassExecutor.execute(MultipleStaticBrowsers.class);
    }

    @Test
    @DisplayName("@Managed on instance browser field injects new browser")
    void idealInstanceBrowserInjectionCase() throws Exception {
        TestClassExecutor.execute(InstanceBrowser.class);
    }

    @Test
    @DisplayName("@Managed on instance browser fields injects new instance for each field")
    void idealInstanceMultiBrowserInjectionCase() throws Exception {
        TestClassExecutor.execute(MultipleInstanceBrowsers.class);
    }

    @Test
    @DisplayName("@Managed works on mixed instance and static browsers")
    void mixedInstanceAndStaticBrowsers() throws Exception {
        TestClassExecutor.execute(MixedBrowsers.class);
    }

    @Test
    @DisplayName("@Managed needs a browser factory to be configured")
    void browserFactoryNeedsToBeConfigured() throws Exception {
        Assertions.assertThrows(NoBrowserFactoryException.class, () -> {
            TestClassExecutor.execute(NoBrowserFactory.class);
        });
    }

    @Test
    @DisplayName("browser factory can be defined on browser field")
    void browserFactoryCanBeDefinedOnBrowserField() throws Exception {
        TestClassExecutor.execute(BrowserFactoryOnField.class);
    }

    @Test
    @DisplayName("browser factory on field overrides class configuration")
    void browserFactoryOnFieldOverridesClassConfiguration() throws Exception {
        TestClassExecutor.execute(BrowserFactoryOnFieldOverride.class);
        assertThat(SpecialBrowserFactory.used).isTrue();
    }

    @Test
    @DisplayName("extension does not fail in case it has nothing to do")
    void extensionDoesNotFailIfNothingToDo() throws Exception {
        TestClassExecutor.execute(NoBrowsers.class);
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class StaticBrowser {

        @Managed
        static Browser browser;

        @Test
        void staticBrowserInjection() {
            assertThat(browser).isNotNull();
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class MultipleStaticBrowsers {

        @Managed("browser-1")
        static Browser browser1;
        @Managed("browser-2")
        static Browser browser2;

        @Test
        void staticBrowserInjection() {
            assertThat(browser1).isNotNull();
            assertThat(browser2).isNotNull();
            assertThat(browser1).isNotSameAs(browser2);
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class InstanceBrowser {

        @Managed
        Browser browser;

        @Test
        void staticBrowserInjection() {
            assertThat(browser).isNotNull();
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class MultipleInstanceBrowsers {

        @Managed("browser-1")
        Browser browser1;
        @Managed("browser-2")
        Browser browser2;

        @Test
        void staticBrowserInjection() {
            assertThat(browser1).isNotNull();
            assertThat(browser2).isNotNull();
            assertThat(browser1).isNotSameAs(browser2);
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class MixedBrowsers {

        @Managed("static-browser")
        static Browser staticBrowser;
        @Managed("instance-browser")
        Browser instanceBrowser;

        @Test
        void openGoogle() {
            assertThat(staticBrowser).isNotNull();
            assertThat(instanceBrowser).isNotNull();
            assertThat(staticBrowser).isNotSameAs(instanceBrowser);
        }

    }

    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class BrowserFactoryOnField {

        @Managed
        @CreateUsing(TestBrowserFactory.class)
        Browser browser;

        @Test
        void staticBrowserInjection() {
            assertThat(browser).isNotNull();
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class BrowserFactoryOnFieldOverride {

        @Managed
        @CreateUsing(SpecialBrowserFactory.class)
        Browser browser;

        @Test
        void staticBrowserInjection() {
            assertThat(browser).isNotNull();
        }

    }

    public static class SpecialBrowserFactory extends TestBrowserFactory {
        static boolean used;

        @Override
        public Browser createBrowser() {
            used = true;
            return super.createBrowser();
        }
    }

    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class NoBrowserFactory {

        @Managed
        Browser instanceBrowser;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class NoBrowsers {

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

}
