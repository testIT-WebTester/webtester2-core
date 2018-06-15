package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.NoManagedBrowserForNameException;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.junit5.internal.DefaultTestClassModelFactory;
import info.novatec.testit.webtester.junit5.internal.TestClassModel;
import info.novatec.testit.webtester.junit5.internal.TestClassModelFactory;


class RegisteredEventListenerExtensionTest {

    TestClassModelFactory modelFactory = new DefaultTestClassModelFactory();
    RegisteredEventListenerExtension cut = new RegisteredEventListenerExtension();

    @Test
    @DisplayName("@Registered with static field will throw exception")
    void registerStaticEventListenerField() {
        assertThrows(StaticEventListenerFieldsNotSupportedException.class, () -> {
            TestClassModel model = modelFactory.create(RegisteredStaticField.class);
            RegisteredStaticField instance = new RegisteredStaticField();
            cut.initializeEventListener(model, instance);
        });
    }

    private static class RegisteredStaticField {

        @Managed("myBrowser")
        Browser browser = new TestBrowserFactory().createBrowser();

        @Registered(targets = "myBrowser")
        private static CustomEventListener eventListener;

    }

    @Test
    @DisplayName("@Registered has no clear assignment to multi browser fields")
    void noClearBrowserAssignment() {
        assertThrows(NoManagedBrowserForNameException.class, () -> {
            TestClassModel model = modelFactory.create(NoClearBrowserAssignment.class);
            NoClearBrowserAssignment instance = new NoClearBrowserAssignment();
            cut.initializeEventListener(model, instance);
        });
    }

    private static class NoClearBrowserAssignment {

        @Managed("browser-1")
        Browser browser1 = new TestBrowserFactory().createBrowser();

        @Managed("browser-2")
        Browser browser2 = new TestBrowserFactory().createBrowser();

        @Registered
        private CustomEventListener eventListener;

    }

}
