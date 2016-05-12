package info.novatec.testit.webtester.browser.operations;

import org.openqa.selenium.JavascriptExecutor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This browser operation offers methods related to the execution of JavaScript code.
 *
 * @see #execute(String, Object...)
 * @see #execute(String, PageFragment, Object...)
 * @since 2.0
 */
public class JavaScript extends BaseBrowserOperation {

    /**
     * Creates a new {@link JavaScript} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public JavaScript(Browser browser) {
        super(browser);
    }

    /**
     * Executes the given JavaScript code for the given {@link PageFragment}
     * (available in script as arguments[0]) with the given parameters
     * (accessible as arguments[1] - arguments[n]).
     *
     * @param script the JavaScript code to be executed on the current page
     * @param fragment the target {@link PageFragment}
     * @param parameters any of Boolean, Long, String, List, WebElement or null.
     * @return the original browser of this operation
     * @see JavascriptExecutor#executeScript(String, Object...)
     * @since 2.0
     */
    public Browser execute(String script, PageFragment fragment, Object... parameters) {
        executeWithReturn(script, fragment, parameters);
        return browser();
    }

    /**
     * Executes the given JavaScript code for the given {@link PageFragment} (available in script as arguments[0])
     * with the given parameters (accessible as arguments[1] - arguments[n]).
     * <p>
     * The JavaScripts return value is returned as described in {@link JavascriptExecutor#executeScript(String, Object...)}.
     *
     * @param script the JavaScript code to be executed on the current page
     * @param fragment the target {@link PageFragment}
     * @param parameters any of Boolean, Long, String, List, WebElement or null.
     * @return the return value of the JavaScript
     * @see JavascriptExecutor#executeScript(String, Object...)
     * @since 2.0
     */
    public <T> T executeWithReturn(String script, PageFragment fragment, Object... parameters) {
        Object[] parameterArray = new Object[parameters.length + 1];
        parameterArray[0] = fragment.webElement();
        System.arraycopy(parameters, 0, parameterArray, 1, parameters.length);
        return executeWithReturn(script, parameterArray);
    }

    /**
     * Executes the given JavaScript code with the given parameters (accessible as arguments[0] - arguments[n]).
     *
     * @param script the JavaScript code to be executed on the current page
     * @param parameters any of Boolean, Long, String, List, WebElement or null.
     * @return the original browser of this operation
     * @see JavascriptExecutor#executeScript(String, Object...)
     * @since 2.0
     */
    public Browser execute(String script, Object... parameters) {
        executeWithReturn(script, parameters);
        return browser();
    }

    /**
     * Executes the given JavaScript code with the given parameters (accessible as arguments[0] - arguments[n]).
     * <p>
     * The JavaScripts return value is returned as described in {@link JavascriptExecutor#executeScript(String, Object...)}.
     *
     * @param script the JavaScript code to be executed on the current page
     * @param parameters any of Boolean, Long, String, List, WebElement or null.
     * @return the return value of the JavaScript
     * @see JavascriptExecutor#executeScript(String, Object...)
     * @since 2.0
     */
    public <T> T executeWithReturn(String script, Object... parameters) {
        if (!(webDriver() instanceof JavascriptExecutor)) {
            throw new UnsupportedOperationException("WebDriver does not support JavaScript execution!");
        }
        JavascriptExecutor javascriptExecutor = ( JavascriptExecutor ) webDriver();
        return (T) javascriptExecutor.executeScript(script, parameters);
    }

}
