package info.novatec.testit.webtester.junit5.extensions.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.TestClassExecutor.execute;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.extensions.browsers.ManagedBrowserExtension;
import info.novatec.testit.webtester.pages.Page;


public class PageInitializerExtensionIntTest {

    @Test
    @DisplayName("@Initialized injects new pages using browser")
    void idealPageInjectionCase() throws Exception {
        execute(PageInjection.class);
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

    @Test
    @DisplayName("@Initialized can be used to inject pages from different browsers")
    void idealMultiBrowserPageInjectionCase() throws Exception {
        execute(MultiBrowserHappyClass.class);
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

    @Test
    @DisplayName("@Initialized needs a managed browser")
    void needsManagedBrowser() throws Exception {
        assertThrows(NoManagedBrowserException.class, () -> {
            execute(NoBrowserClass.class);
        });
    }

    private static class NoBrowserClass extends BaseTestClass {

        @Initialized
        Page page;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("@Initialized needs a managed browser with matching name")
    void needsManagedBrowserWithMatchingName() throws Exception {
        NoManagedBrowserForNameException exception = assertThrows(NoManagedBrowserForNameException.class, () -> {
            execute(WrongBrowserNameClass.class);
        });
        assertThat(exception.getName()).isEqualTo("wrong-name");
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

    @Test
    @DisplayName("@Initialized throws exception if used on static field")
    void throwsExceptionIfUsedOnStaticField() throws Exception {
        StaticPageFieldsNotSupportedException exception =
            assertThrows(StaticPageFieldsNotSupportedException.class, () -> {
                execute(StaticFieldClass.class);
            });
        assertThat(exception.getField()).isEqualTo(StaticFieldClass.class.getDeclaredField("page"));
    }

    private static class StaticFieldClass extends BaseTestClass {

        @Managed
        static Browser browser;
        @Initialized
        static Page page;

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
    @ExtendWith(PageInitializerExtension.class)
    private static class BaseTestClass {

    }

}
