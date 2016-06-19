package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.MockFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(Enclosed.class)
public class JavaScriptExecutorTest {

    @RunWith(MockitoJUnitRunner.class)
    public static class JavaScriptEnabledDriver {

        @Mock
        Browser browser;
        @InjectMocks
        JavaScriptExecutor cut;

        @Mock(extraInterfaces = WebDriver.class)
        JavascriptExecutor webDriver;

        @Before
        public void stubBrowsersWebDriver() {
            doReturn(webDriver).when(browser).webDriver();
        }

        @Test
        public void scriptsCanBeExecuted() {
            cut.execute("alert('Hello World!')");
            verify(webDriver).executeScript("alert('Hello World!')");
        }

        @Test
        public void scriptsWithParametersCanBeExecuted() {
            cut.execute("alert('arguments[0]')", "Hello World!");
            verify(webDriver).executeScript("alert('arguments[0]')", "Hello World!");
        }

        @Test
        public void scriptsWithReturnCanBeExecuted() {
            doReturn(true).when(webDriver).executeScript("return true");
            Boolean returnValue = cut.executeWithReturn("return true");
            assertThat(returnValue).isEqualTo(true);
        }

        @Test
        public void scriptsWithReturnAndParametersCanBeExecuted() {
            doReturn(true).when(webDriver).executeScript("return !arguments[0]", false);
            Boolean returnValue = cut.executeWithReturn("return !arguments[0]", false);
            assertThat(returnValue).isEqualTo(true);
        }

        @Test
        public void scriptsWithPageFragmentsUseCorrectWebElement() {
            PageFragment fragment = MockFactory.fragment().build();
            cut.execute("arguments[0].value = 'arguments[1]')", fragment, "new value");
            verify(webDriver).executeScript("arguments[0].value = 'arguments[1]')", fragment.webElement(), "new value");
        }

        @Test
        public void scriptsWithReturnAndPageFragmentsUseCorrectWebElement() {

            PageFragment fragment = MockFactory.fragment().build();
            WebElement webElement = fragment.webElement();
            doReturn("old value").when(webDriver).executeScript("return arguments[0].arguments[1])", webElement, "value");

            String returnValue = cut.executeWithReturn("return arguments[0].arguments[1])", fragment, "value");
            assertThat(returnValue).isEqualTo("old value");

        }

    }

    @RunWith(MockitoJUnitRunner.class)
    public static class NonJavaScriptEnabledDriver {

        @Mock
        Browser browser;
        @InjectMocks
        JavaScriptExecutor cut;

        @Before
        public void setUpBrowser() {
            WebDriver nonJavaScriptWebDriver = mock(WebDriver.class);
            doReturn(nonJavaScriptWebDriver).when(browser).webDriver();
        }

        @Test(expected = UnsupportedOperationException.class)
        public void cantExecuteScript() {
            cut.execute("alert('Hello World!')");
        }

        @Test(expected = UnsupportedOperationException.class)
        public void cantExecuteScriptWithReturn() {
            cut.executeWithReturn("return true");
        }

    }

}
