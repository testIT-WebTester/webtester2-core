package info.novatec.testit.webtester.junit5.extensions.browsers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.internal.DefaultTestClassModelFactory;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;
import info.novatec.testit.webtester.junit5.internal.TestClassModelFactory;


class ManagedBrowserExtensionTest {

    TestClassModelFactory modelFactory = new DefaultTestClassModelFactory();
    ManagedBrowserExtension cut = new ManagedBrowserExtension();

    @Test
    @DisplayName("@Managed needs a browser factory to be configured")
    void browserFactoryNeedsToBeConfigured() {
        Assertions.assertThrows(NoBrowserFactoryException.class, () -> {
            TestClassModel model = modelFactory.create(NoBrowserFactory.class);
            NoBrowserFactory instance = new NoBrowserFactory();
            cut.initializeAndInjectInstanceBrowsers(model, instance);
        });
    }

    private static class NoBrowserFactory {

        @Managed
        Browser instanceBrowser;

    }

    @Test
    @DisplayName("browser factory on field overrides class configuration")
    void browserFactoryOnFieldOverridesClassConfiguration() {
        TestClassModel model = modelFactory.create(BrowserFactoryOnFieldOverride.class);
        BrowserFactoryOnFieldOverride instance = new BrowserFactoryOnFieldOverride();
        cut.initializeAndInjectInstanceBrowsers(model, instance);
        assertThat(SpecialBrowserFactory.used).isTrue();
    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    private static class BrowserFactoryOnFieldOverride {

        @Managed
        @CreateUsing(SpecialBrowserFactory.class)
        Browser browser;

    }

    public static class SpecialBrowserFactory extends TestBrowserFactory {
        static boolean used;

        @Override
        public Browser createBrowser() {
            used = true;
            return super.createBrowser();
        }
    }

}
