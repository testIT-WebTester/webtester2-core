package info.novatec.testit.webtester.junit5.extensions.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import utils.TestBrowserFactory;
import utils.TestClassExecutor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.exceptions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.analysis.TestClassAnalysisExtension;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.browsers.ManagedBrowserExtension;
import info.novatec.testit.webtester.pages.Page;


public class PageInitializerExtensionIntegrationTest {

    @Test
    @DisplayName("@Initialized injects new pages using browser")
    void idealPageInjectionCase() throws Exception {
        TestClassExecutor.execute(PageInjection.class);
    }

    @Test
    @DisplayName("@Initialized can be used to inject pages from different browsers")
    void idealMultiBrowserPageInjectionCase() throws Exception {
        TestClassExecutor.execute(MultiBrowserHappyClass.class);
    }

    @Test
    @DisplayName("@Initialized needs a managed browser")
    void needsManagedBrowser() throws Exception {
        assertThrows(NoManagedBrowserException.class, () -> {
            TestClassExecutor.execute(NoBrowserClass.class);
        });
    }

    @Test
    @DisplayName("@Initialized needs a managed browser with matching name")
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

    private static class PageInjection extends BaseTestClass {

        @Managed
        Browser browser;

        @Initialized
        Page page;

        @Test
        void pageInjection() {
            assertThat(page).isNotNull();
            assertThat(page.browser()).isSameAs(browser);
        }

    }

    private static class MultiBrowserHappyClass extends BaseTestClass {

        @Managed("primary")
        static Browser primaryBrowser;
        @Managed("secondary")
        Browser secondaryBrowser;

        @Initialized(source = "primary")
        Page primaryPage;
        @Initialized(source = "secondary")
        Page secondaryPage;

        @Test
        void pageInjectionFromMultipleBrowsers() {
            assertThat(primaryPage).isNotNull();
            assertThat(secondaryPage).isNotNull();
            assertThat(primaryPage.browser()).isSameAs(primaryBrowser);
            assertThat(secondaryPage.browser()).isSameAs(secondaryBrowser);
        }

    }

    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(PageInitializerExtension.class)
    private static class NoBrowserClass {

        @Initialized
        Page page;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    private static class WrongBrowserNameClass extends BaseTestClass {

        @Managed("browser")
        Browser browser;
        @Initialized(source = "wrong-name")
        Page page;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(PageInitializerExtension.class)
    private static class NoOpClass {

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(TestClassAnalysisExtension.class)
    @ExtendWith(ManagedBrowserExtension.class)
    @ExtendWith(PageInitializerExtension.class)
    private static class BaseTestClass {

    }

}
