package info.novatec.testit.webtester.junit5.extensions.pages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserException;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.internal.DefaultTestClassModelFactory;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;
import info.novatec.testit.webtester.junit5.internal.TestClassModelFactory;
import info.novatec.testit.webtester.pages.Page;


class PageInitializerExtensionTest {

    TestClassModelFactory modelFactory = new DefaultTestClassModelFactory();
    PageInitializerExtension cut = new PageInitializerExtension();

    @Test
    @DisplayName("@Initialized needs a managed browser")
    void needsManagedBrowser() {
        assertThrows(NoManagedBrowserException.class, () -> {
            TestClassModel model = modelFactory.create(NoBrowserClass.class);
            NoBrowserClass instance = new NoBrowserClass();
            cut.createPagesForAnnotatedFields(model, instance);
        });
    }

    private static class NoBrowserClass {

        @Initialized
        Page page;

    }

    @Test
    @DisplayName("@Initialized needs a managed browser with matching name")
    void needsManagedBrowserWithMatchingName() {
        NoManagedBrowserForNameException exception = assertThrows(NoManagedBrowserForNameException.class, () -> {
            TestClassModel model = modelFactory.create(WrongBrowserNameClass.class);
            WrongBrowserNameClass instance = new WrongBrowserNameClass();
            cut.createPagesForAnnotatedFields(model, instance);
        });
        assertThat(exception.getName()).isEqualTo("wrong-name");
    }

    private static class WrongBrowserNameClass {

        @Managed("browser")
        Browser browser = new TestBrowserFactory().createBrowser();
        @Initialized(source = "wrong-name")
        Page page;

    }

    @Test
    @DisplayName("@Initialized throws exception if used on static field")
    void throwsExceptionIfUsedOnStaticField() throws Exception {
        StaticPageFieldsNotSupportedException exception = assertThrows(StaticPageFieldsNotSupportedException.class, () -> {
            TestClassModel model = modelFactory.create(StaticFieldClass.class);
            StaticFieldClass instance = new StaticFieldClass();
            cut.createPagesForAnnotatedFields(model, instance);
        });
        assertThat(exception.getField()).isEqualTo(StaticFieldClass.class.getDeclaredField("page"));
    }

    private static class StaticFieldClass {

        @Managed
        static Browser browser = new TestBrowserFactory().createBrowser();
        @Initialized
        static Page page;

    }

}
