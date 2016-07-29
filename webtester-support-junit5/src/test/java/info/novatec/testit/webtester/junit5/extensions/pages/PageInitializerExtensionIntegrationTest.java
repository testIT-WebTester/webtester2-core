package info.novatec.testit.webtester.junit5.extensions.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import integration.BaseIntegrationTest;
import utils.TestClassExecutor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.exceptions.NonManagedBrowserException;
import info.novatec.testit.webtester.junit5.exceptions.NonManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.pages.Page;


public class PageInitializerExtensionIntegrationTest {

    @Test
    @DisplayName("@Initialized injects new pages using browser")
    void idealPageInjectionCase() throws Exception {
        TestClassExecutor.execute(HappyClass.class);
    }

    @Test
    @DisplayName("@Initialized can be used to inject pages from different browsers")
    void idealMultiBrowserPageInjectionCase() throws Exception {
        TestClassExecutor.execute(MultiBrowserHappyClass.class);
    }

    @Test
    @DisplayName("@Initialized needs a managed browser")
    void needsManagedBrowser() throws Exception {
        assertThrows(NonManagedBrowserException.class, () -> {
            TestClassExecutor.execute(NoBrowserClass.class);
        });
    }

    @Test
    @DisplayName("@Initialized needs a managed browser with matching name")
    void needsManagedBrowserWithMatchingName() throws Exception {
        assertThrows(NonManagedBrowserForNameException.class, () -> {
            TestClassExecutor.execute(WrongBrowserNameClass.class);
        });
    }

    private static class HappyClass extends BaseIntegrationTest {

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

    private static class MultiBrowserHappyClass extends BaseIntegrationTest {

        @Managed("primary")
        Browser primaryBrowser;
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

    private static class NoBrowserClass extends BaseIntegrationTest {

        @Initialized
        Page page;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    private static class WrongBrowserNameClass extends BaseIntegrationTest {

        @Managed("browser")
        Browser browser;
        @Initialized(source = "wrong-name")
        Page page;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

}
