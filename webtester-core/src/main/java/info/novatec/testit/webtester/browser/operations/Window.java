package info.novatec.testit.webtester.browser.operations;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.browser.ClosedWindowEvent;
import info.novatec.testit.webtester.events.browser.MaximizedWindowEvent;
import info.novatec.testit.webtester.events.browser.RefreshedPageEvent;
import info.novatec.testit.webtester.events.browser.SetWindowPositionEvent;
import info.novatec.testit.webtester.events.browser.SetWindowSizeEvent;
import info.novatec.testit.webtester.internal.ActionTemplate;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This browser operation offers methods related to the manipulation of browser windows.
 *
 * @see #getHandle()
 * @see #refresh()
 * @see #maximize()
 * @see #toggleFullScreen()
 * @see #setPosition(int, int)
 * @see #setSize(int, int)
 * @see #close()
 * @since 2.0
 */
@Slf4j
public class Window extends BaseBrowserOperation {

    /**
     * Creates a new {@link Window} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public Window(Browser browser) {
        super(browser);
    }

    /**
     * Returns the currently focused window's handle.
     * <p>
     * This handle can be used to focus set the focus on that window in case of popups or other focus stealing activities.
     *
     * @return the handle of the current window
     * @see WebDriver#getWindowHandle()
     * @since 2.0
     */
    public String getHandle() {
        return webDriver().getWindowHandle();
    }

    /**
     * Refreshes the content of the currently focused window.
     *
     * @return the original browser of this operation
     * @see WebDriver.Navigation#refresh()
     * @since 2.0
     */
    public Browser refresh() {
        ActionTemplate.browser(browser())
            .execute(browser -> browser.webDriver().navigate().refresh())
            .fireEvent(browser -> new RefreshedPageEvent());
        log.debug("refreshed current window ({})", getHandle());
        return browser();
    }

    /**
     * Maximizes the currently focused window.
     *
     * @return the original browser of this operation
     * @see WebDriver.Window#maximize()
     * @since 2.0
     */
    public Browser maximize() {
        ActionTemplate.browser(browser())
            .execute(browser -> getWindowManager(browser).maximize())
            .fireEvent(browser -> new MaximizedWindowEvent());
        log.debug("maximized current window ({})", getHandle());
        return browser();
    }

    /**
     * Tries to make the currently focused window display in full screen.
     * This is done by pressing F11 while selecting the current page's content.
     * In most browsers this will trigger a switch to full screen - but there are no guarantees.
     * <p>
     * This feature should be used with caution!
     *
     * @return the original browser of this operation
     * @since 2.0
     */
    public Browser toggleFullScreen() {
        ActionTemplate.browser(browser()).execute(browser -> {
            WebDriver webDriver = browser.webDriver();
            WebElement rootElement = webDriver.findElement(By.tagName("html"));
            rootElement.sendKeys(Keys.F11);
        });
        log.debug("made current window ({}) display in full screen", getHandle());
        return browser();
    }

    /**
     * Sets the position of the currently focused window using X and Y coordinates.
     * These coordinates are where the upper left corner of the window will be moved to.
     *
     * @param x the X coordinate part (horizontal)
     * @param y the Y coordinate part (vertical)
     * @return the original browser of this operation
     * @see WebDriver.Window#setPosition(Point)
     * @since 2.0
     */
    public Browser setPosition(int x, int y) {
        ActionTemplate.browser(browser())
            .execute(browser -> getWindowManager(browser).setPosition(new Point(x, y)))
            .fireEvent(browser -> new SetWindowPositionEvent(x, y));
        log.debug("set position of current window ({}) to x={} and y={}", getHandle(), x, y);
        return browser();
    }

    /**
     * Sets the size of the currently focused window using width and height.
     * The window will not be moved by this operation! You can move it using {@link #setPosition(int, int)}.
     *
     * @param width the new width of the window
     * @param height the new height of the window
     * @return the original browser of this operation
     * @see WebDriver.Window#setSize(Dimension)
     * @since 2.0
     */
    public Browser setSize(int width, int height) {
        ActionTemplate.browser(browser())
            .execute(browser -> getWindowManager(browser).setSize(new Dimension(width, height)))
            .fireEvent(browser -> new SetWindowSizeEvent(width, height));
        log.debug("set size of current window ({}) to width={} and height={}", getHandle(), width, height);
        return browser();
    }

    /**
     * Scrolls the window to the given {@link PageFragment}.
     * <p>
     * This is done by using the {@code scrollIntoView(true)} JavaScript function on the underlying element.
     * Since all JavaScript functionality depends heavily on the used browser this might not work in all environments.
     * See <a href="https://developer.mozilla.org/en/docs/Web/API/Element/scrollIntoView">MDN Web API</a> for details.
     *
     * @param fragment the fragment to scroll into view
     * @return the original browser of this operation
     * @see JavaScript
     * @since 2.0
     */
    public Browser scrollTo(PageFragment fragment) {
        log.debug("scrolling [{}] into view", fragment.getName().orElse(fragment.toString()));
        return browser().javaScript().execute("arguments[0].scrollIntoView(true)", fragment);
    }

    /**
     * Closes the currently focused window. If that window was the last open window, the browser wil be closed as well
     * making it unusable in the future. Use this method with care!
     *
     * @return the original browser of this operation
     * @see WebDriver#close()
     * @since 2.0
     */
    public Browser close() {
        log.debug("closing current window ({})", getHandle());
        ActionTemplate.browser(browser()).execute(browser -> {
            browser.webDriver().close();
            browser.webDriver().switchTo().defaultContent();
        }).fireEvent(browser -> new ClosedWindowEvent());
        return browser();
    }

    private WebDriver.Window getWindowManager(Browser browser) {
        return browser.webDriver().manage().window();
    }

}
