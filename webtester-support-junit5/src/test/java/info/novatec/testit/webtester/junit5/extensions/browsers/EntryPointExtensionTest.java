package info.novatec.testit.webtester.junit5.extensions.browsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import utils.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.UnknownConfigurationKeyException;


class EntryPointExtensionTest {

    EntryPointExtension cut = new EntryPointExtension();

    @Test
    @DisplayName("unknown variable will throw exception")
    void unknownVariable() throws Exception {
        UnknownVariable instance = new UnknownVariable();
        Field field = UnknownVariable.class.getDeclaredField("browser");

        UnknownConfigurationKeyException exception = assertThrows(UnknownConfigurationKeyException.class, () -> {
            cut.openEntryPoint(field, instance);
        });
        assertThat(exception.getKey()).isEqualTo("unknown-variable");
    }

    private static class UnknownVariable {

        @EntryPoint("${unknown-variable}")
        Browser browser = new TestBrowserFactory().createBrowser();

    }

}
