package info.novatec.testit.webtester.junit5.extensions.browsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static utils.TestClassExecutor.execute;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;


public class ManagedBrowserExtensionIntegrationTest {

    @Test
    @DisplayName("@Managed on static browser field injects new browser")
    void idealStaticBrowserInjectionCase() throws Exception {
        execute(StaticBrowser.class);
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class StaticBrowser {

        @Managed
        static Browser browser;

        @Test
        void staticBrowserInjection() {
            assertThat(browser).isNotNull();
        }

    }

    @Test
    @DisplayName("@Managed on static browser fields injects new instance for each field")
    void idealStaticMultiBrowserInjectionCase() throws Exception {
        execute(MultipleStaticBrowsers.class);
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
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

    @Test
    @DisplayName("@Managed on instance browser field injects new browser")
    void idealInstanceBrowserInjectionCase() throws Exception {
        execute(InstanceBrowser.class);
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class InstanceBrowser {

        @Managed
        Browser browser;

        @Test
        void staticBrowserInjection() {
            assertThat(browser).isNotNull();
        }

    }

    @Test
    @DisplayName("@Managed on instance browser fields injects new instance for each field")
    void idealInstanceMultiBrowserInjectionCase() throws Exception {
        execute(MultipleInstanceBrowsers.class);
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
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

    @Test
    @DisplayName("@Managed works on mixed instance and static browsers")
    void mixedInstanceAndStaticBrowsers() throws Exception {
        execute(MixedBrowsers.class);
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
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

    @Test
    @DisplayName("@Managed needs a browser factory to be configured")
    void browserFactoryNeedsToBeConfigured() throws Exception {
        Assertions.assertThrows(NoBrowserFactoryException.class, () -> {
            execute(NoBrowserFactory.class);
        });
    }

    @ExtendWith(ManagedBrowserExtension.class)
    private static class NoBrowserFactory {

        @Managed
        Browser instanceBrowser;

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    @Test
    @DisplayName("browser factory can be defined on browser field")
    void browserFactoryCanBeDefinedOnBrowserField() throws Exception {
        execute(BrowserFactoryOnField.class);
    }

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

    @Test
    @DisplayName("browser factory on field overrides class configuration")
    void browserFactoryOnFieldOverridesClassConfiguration() throws Exception {
        execute(BrowserFactoryOnFieldOverride.class);
        assertThat(SpecialBrowserFactory.used).isTrue();
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
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

    @Test
    @DisplayName("extension does not fail in case it has nothing to do")
    void extensionDoesNotFailIfNothingToDo() throws Exception {
        execute(NoBrowsers.class);
    }

    @ExtendWith(ManagedBrowserExtension.class)
    private static class NoBrowsers {

        @Test
        void triggerClassExecution() {
            // does nothing
        }

    }

    // TODO Bugs

    @Test
    @DisplayName("@CreateBrowsersUsing annotation is inherited if not overridden")
    void classLevelDeclarationOfBrowserFactoryIsInherited() throws Exception {
        execute(ClassLevelBrowserFactoriesOneLevel.class);
        execute(ClassLevelBrowserFactoriesTwoLevels.class);
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class ClassLevelBrowserFactoriesOneLevel {

        @Managed
        static Browser browser;

        @Test
        void baseClassTest() {
            assertThat(browser).isNotNull();
        }

        @Nested
        class NestedTestClass {

            @Test
            void nestedClassTest() {
                assertThat(browser).isNotNull();
            }

        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(ManagedBrowserExtension.class)
    private static class ClassLevelBrowserFactoriesTwoLevels {

        @Managed
        static Browser browser;

        @Test
        void baseClassTest() {
            assertThat(browser).isNotNull();
        }

        @Nested
        class NestedTestClass {

            @Test
            void nestedClassTest() {
                assertThat(browser).isNotNull();
            }

            @Nested
            class DeepNestedTestClass {

                @Test
                void deepNestedClassTest() {
                    assertThat(browser).isNotNull();
                }

            }

        }

    }

    @Test
    @DisplayName("class level browsers (static) are created once")
    void classLevelBrowsersAreOnlyInitializedOnce() throws Exception {
        execute(OnlyOneStaticBrowserCreated.class);
    }

    @ExtendWith(ManagedBrowserExtension.class)
    private static class OnlyOneStaticBrowserCreated {

        private static final AtomicInteger BROWSERS_CREATED = new AtomicInteger(0);

        @Managed
        @CreateUsing(OnlyOneStaticBrowserCreatedBrowserFactory.class)
        static Browser browser;

        @Test
        void baseClassTest() {
            assertThat(browser).isNotNull();
        }

        @Nested
        class NestedTestClass {

            @Test
            void nestedClassTest() {
                assertThat(browser).isNotNull();
            }

        }

        @AfterAll
        static void onlyOneBrowserCreated() {
            assertThat(BROWSERS_CREATED.get()).isEqualTo(1);
        }

        public static class OnlyOneStaticBrowserCreatedBrowserFactory extends TestBrowserFactory {

            @Override
            public Browser createBrowser() {
                OnlyOneStaticBrowserCreated.BROWSERS_CREATED.incrementAndGet();
                return super.createBrowser();
            }

        }

    }

}
