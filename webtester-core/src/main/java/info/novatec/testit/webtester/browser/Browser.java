package info.novatec.testit.webtester.browser;

import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.operations.AlertHandler;
import info.novatec.testit.webtester.browser.operations.CurrentWindow;
import info.novatec.testit.webtester.browser.operations.FocusSetter;
import info.novatec.testit.webtester.browser.operations.JavaScriptExecutor;
import info.novatec.testit.webtester.browser.operations.Navigator;
import info.novatec.testit.webtester.browser.operations.PageSourceSaver;
import info.novatec.testit.webtester.browser.operations.ScreenshotTaker;
import info.novatec.testit.webtester.browser.operations.UrlOpener;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.internal.OffersAdHocFinding;
import info.novatec.testit.webtester.internal.OffersPageCreation;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;


/**
 * Abstraction layer over Selenium's {@link WebDriver} API. Offers
 * simple methods for commonly used functions which in some cases are no simple
 * single method calls when Selenium is used directly.
 * <p>
 * These methods are packaged as logical unit as so called browser "operations":
 * <ul>
 * <li>{@link #open()} - open URLs and create page objects while doing so</li>
 * <li>{@link #currentWindow()} - manipulate the currently active window</li>
 * <li>{@link #navigate()} - navigate through the browser's history</li>
 * <li>{@link #focus()} - focus IFrames or other windows</li>
 * <li>{@link #alert()} - handle alert messages</li>
 * <li>{@link #screenshot()} - take screenshots</li>
 * <li>{@link #pageSource()} - save page source</li>
 * <li>{@link #javaScript()} - execute JavaScript code</li>
 * </ul>
 * <p>
 * Also offers methods for the creation of {@link PageFragment page fragments}
 * instances like {@link #create(Class)} for an optimal integration into the page
 * object pattern.
 * <p>
 * The browser also represents the single point of contact for most tests. So it
 * manages services instances like the  {@link Configuration configuration}.
 * Each browser has its own instances of these services.
 * This means that every browser can be configured separately from all the others.
 * Which is very imported in case tests are executed in parallel.
 * <p>
 * In general browsers should be constructed using a {@link BrowserFactory
 * factory} or at least by using a {@link BrowserBuilder builder}. For an
 * explanation of what the difference between these two classes are take a look
 * at their JavaDoc.
 *
 * @see WebDriver
 * @see BrowserBuilder
 * @see BrowserFactory
 * @see Configuration
 * @see UrlOpener
 * @see CurrentWindow
 * @see Navigator
 * @see FocusSetter
 * @see AlertHandler
 * @see ScreenshotTaker
 * @see PageSourceSaver
 * @see JavaScriptExecutor
 * @since 2.0
 */
public interface Browser extends OffersPageCreation, OffersAdHocFinding {

    /**
     * Returns the title of the currently displayed page. If no title is set an empty String is returned!
     *
     * @return the title of the current page
     * @since 2.0
     */
    String currentPageTitle();

    /**
     * Returns the URL of the currently displayed page as a String.
     *
     * @return the current URL
     * @since 2.0
     */
    String currentUrl();

    /**
     * Closes the {@link Browser} and all its windows.
     * <p>
     * <b>Attention:</b> after calling this method the browser instance will become unusable!
     *
     * @since 2.0
     */
    void close();

    /* browser operations */

    /**
     * Opens the given URL in the {@link Browser browser}.
	 
     * @param url the URL to open
     * @return the same instance for fluent API use
     * @see UrlOpener#url(String)
     * @since 2.0
     */
    default Browser open(String url) {
        open().url(url);
        return this;
    }

    /**
     * Returns this {@link Browser browser's} {@link UrlOpener open} operations.
     *
     * @return the open operations
     * @since 2.0
     */
    UrlOpener open();

    /**
     * Returns this {@link Browser browser's} {@link CurrentWindow window} operations.
     *
     * @return the window operations
     * @since 2.0
     */
    CurrentWindow currentWindow();

    /**
     * Returns this {@link Browser browser's} {@link Navigator navigation} operations.
     *
     * @return the navigation operations
     * @since 2.0
     */
    Navigator navigate();

    /**
     * Returns this {@link Browser browser's} {@link FocusSetter focus} operations.
     *
     * @return the focus operations
     * @since 2.0
     */
    FocusSetter focus();

    /**
     * Returns this {@link Browser browser's} {@link AlertHandler alert} operations.
     *
     * @return the alert operations
     * @since 2.0
     */
    AlertHandler alert();

    /**
     * Returns this {@link Browser browser's} {@link ScreenshotTaker screenshot} operations.
     *
     * @return the screenshot operations
     * @since 2.0
     */
    ScreenshotTaker screenshot();

    /**
     * Returns this {@link Browser browser's} {@link PageSourceSaver page source} operations.
     *
     * @return the page source operations
     * @since 2.0
     */
    PageSourceSaver pageSource();

    /**
     * Returns this {@link Browser browser's} {@link JavaScriptExecutor} operations.
     *
     * @return the JavaScript operations
     * @since 2.0
     */
    JavaScriptExecutor javaScript();


    /**
     * Returns this {@link Browser browser's} {@link EventSystem event system}.
     *
     * @return the event system operations
     * @since 2.0
     */
    EventSystem events();

    /* getter */

    /**
     * Returns the underlying Selenium {@link WebDriver web driver}.
     * <p>
     * <b>Note to developers and testers:</b> Needing to use this method outside
     * of {@link Page page} and {@link PageFragment page fragment} subclasses
     * (i.e. tests) is a signal that the WebTester abstraction <i>might</i> be missing a feature.
     * In these cases contact the WebTester team by making a feature request.
     *
     * @return the underlying web driver
     * @since 2.0
     */
    WebDriver webDriver();

    /**
     * Returns the {@link Configuration configuration} used by this browser instance.
     * <p>
     * This configuration is set while building the instance using a {@link BrowserBuilder browser builder}.
     *
     * @return the configuration
     * @since 2.0
     */
    Configuration configuration();

}
