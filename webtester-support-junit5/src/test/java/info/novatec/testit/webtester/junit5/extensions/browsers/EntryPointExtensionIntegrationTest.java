package info.novatec.testit.webtester.junit5.extensions.browsers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import utils.TestBrowserFactory;
import utils.TestClassExecutor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.UnknownConfigurationKeyException;


public class EntryPointExtensionIntegrationTest {

    @Test
    @DisplayName("@EntryPoint with static URL opens page before each test")
    void staticUrl() throws Exception {
        TestClassExecutor.execute(StaticEntryPoint.class);
    }

    private static class StaticEntryPoint extends AbstractTestClass {

        @Managed
        @EntryPoint("http://www.example.com")
        Browser browser;

        @Test
        void test() {
            verify(browser.webDriver()).get("http://www.example.com");
        }

    }

    @Test
    @DisplayName("@EntryPoint URL can be a variable")
    void variableUrl() throws Exception {
        TestClassExecutor.execute(VariableEntryPoint.class);
    }

    private static class VariableEntryPoint extends AbstractTestClass {

        @Managed
        @EntryPoint("${my-variable-entry-point}")
        static Browser browser;

        @BeforeAll
        static void setConfigurationVariable() {
            browser.configuration().setProperty("my-variable-entry-point", "http://www.example.com");
        }

        @Test
        void test() {
            verify(browser.webDriver()).get("http://www.example.com");
        }

    }

    @Test
    @DisplayName("@EntryPoint URL can be partially static and variable")
    void partiallyVariableUrl() throws Exception {
        TestClassExecutor.execute(PartiallyVariableEntryPoint.class);
    }

    private static class PartiallyVariableEntryPoint extends AbstractTestClass {

        @Managed
        @EntryPoint("http://${properties.host}/index.html")
        static Browser browser;

        @BeforeAll
        static void setConfigurationVariable() {
            browser.configuration().setProperty("properties.host", "localhost");
        }

        @Test
        void test() {
            verify(browser.webDriver()).get("http://localhost/index.html");
        }

    }

    @Test
    @DisplayName("@EntryPoint URL can have multiple variables")
    void multipleVariablesInUrl() throws Exception {
        TestClassExecutor.execute(MultipleVariablesEntryPoint.class);
    }

    private static class MultipleVariablesEntryPoint extends AbstractTestClass {

        @Managed
        @EntryPoint("http://${properties.host}:${properties.port}/index.html")
        static Browser browser;

        @BeforeAll
        static void setConfigurationVariable() {
            browser.configuration().setProperty("properties.host", "localhost");
            browser.configuration().setProperty("properties.port", 8080);
        }

        @Test
        void test() {
            verify(browser.webDriver()).get("http://localhost:8080/index.html");
        }

    }

    @Test
    @DisplayName("unknown variable will throw exception")
    void unknownVariable() throws Exception {
        UnknownConfigurationKeyException exception = expectThrows(UnknownConfigurationKeyException.class, () -> {
            TestClassExecutor.execute(UnknownVariable.class);
        });
        assertThat(exception.getKey()).isEqualTo("unknown-variable");
    }

    private static class UnknownVariable extends AbstractTestClass {

        @Managed
        @EntryPoint("${unknown-variable}")
        Browser browser;

        @Test
        void triggerTestExecution() {
        }

    }

    @Test
    @DisplayName("extension does not fail in case it has nothing to do")
    void extensionDoesNotFailIfNothingToDo() throws Exception {
        TestClassExecutor.execute(NoOpClass.class);
    }

    @ExtendWith(EntryPointExtension.class)
    private static class NoOpClass {

        @Test
        void triggerTestExecution() {
        }

    }

    @CreateBrowsersUsing(TestBrowserFactory.class)
    @ExtendWith(ManagedBrowserExtension.class)
    @ExtendWith(EntryPointExtension.class)
    private static abstract class AbstractTestClass {

    }

}
