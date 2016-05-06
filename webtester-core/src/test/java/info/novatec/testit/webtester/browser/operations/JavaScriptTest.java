package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(MockitoJUnitRunner.class)
public class JavaScriptTest {

    static final String JAVA_SCRIPT = "alert('Hello World!')";

    @Test
    public void javaScriptIsExecutedIfWebDriverSupportsIt() {
        WebDriver webDriver = javaScriptExecutingWebDriver();
        javaScriptFor(webDriver).execute(JAVA_SCRIPT);
        verify(( JavascriptExecutor ) webDriver).executeScript(JAVA_SCRIPT);
    }

    @Test
    public void justParametersWithoutReturn() {
        WebDriver webDriver = javaScriptExecutingWebDriver();
        javaScriptFor(webDriver).execute(JAVA_SCRIPT, "param1", "param2");
        verify(( JavascriptExecutor ) webDriver).executeScript(JAVA_SCRIPT, "param1", "param2");
    }

    @Test
    public void justParametersWithoutReturnReturnsBrowser() {
        Browser browser = browserFor(javaScriptExecutingWebDriver());
        Browser returnBrowser = new JavaScript(browser).execute(JAVA_SCRIPT, "param");
        assertThat(returnBrowser).isSameAs(browser);
    }

    @Test
    public void justParametersWithReturn() {
        WebDriver webDriver = javaScriptExecutingWebDriver();
        doReturn("returnValue").when(( JavascriptExecutor ) webDriver).executeScript(JAVA_SCRIPT, "param");
        Object returnValue = javaScriptFor(webDriver).executeWithReturn(JAVA_SCRIPT, "param");
        assertThat(returnValue).isEqualTo("returnValue");
    }

    @Test
    public void pageFragmentAndParametersWithoutReturn() {

        WebDriver webDriver = javaScriptExecutingWebDriver();
        PageFragment fragment = pageFragment();

        javaScriptFor(webDriver).execute(JAVA_SCRIPT, fragment, "param1", "param2");

        WebElement webElement = fragment.webElement();
        verify(( JavascriptExecutor ) webDriver).executeScript(JAVA_SCRIPT, webElement, "param1", "param2");

    }

    @Test
    public void pageFragmentAndParametersWithoutReturnReturnsBrowser() {
        Browser browser = browserFor(javaScriptExecutingWebDriver());
        Browser returnBrowser = new JavaScript(browser).execute(JAVA_SCRIPT, pageFragment(), "param");
        assertThat(returnBrowser).isSameAs(browser);
    }

    @Test
    public void pageFragmentAndParametersWithReturn() {

        WebDriver webDriver = javaScriptExecutingWebDriver();
        PageFragment fragment = pageFragment();

        WebElement webElement = fragment.webElement();
        doReturn("returnValue").when(( JavascriptExecutor ) webDriver).executeScript(JAVA_SCRIPT, webElement, "param");

        Object returnValue = javaScriptFor(webDriver).executeWithReturn(JAVA_SCRIPT, fragment, "param");
        assertThat(returnValue).isEqualTo("returnValue");

    }

    @Test(expected = UnsupportedOperationException.class)
    public void executingJavaScriptThrowsExceptionIfBrowserDoesNotSupportIt() {
        WebDriver webDriver = nonJavaScriptExecutingWebDriver();
        javaScriptFor(webDriver).execute(JAVA_SCRIPT);
    }

    /* utilities */

    JavaScript javaScriptFor(WebDriver webDriver) {
        return new JavaScript(browserFor(webDriver));
    }

    Browser browserFor(WebDriver webDriver) {
        Browser browser = mock(Browser.class);
        doReturn(webDriver).when(browser).webDriver();
        return browser;
    }

    WebDriver javaScriptExecutingWebDriver() {
        return mock(WebDriver.class, withSettings().extraInterfaces(JavascriptExecutor.class));
    }

    WebDriver nonJavaScriptExecutingWebDriver() {
        return mock(WebDriver.class);
    }

    PageFragment pageFragment() {
        PageFragment fragment = mock(PageFragment.class);
        WebElement webElement = mock(WebElement.class);
        doReturn(webElement).when(fragment).webElement();
        return fragment;
    }

}
