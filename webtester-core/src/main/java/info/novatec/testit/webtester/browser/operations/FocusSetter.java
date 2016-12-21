package info.novatec.testit.webtester.browser.operations;

import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.browser.SwitchedToDefaultContentEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToFrameEvent;
import info.novatec.testit.webtester.events.browser.SwitchedToWindowEvent;
import info.novatec.testit.webtester.internal.ActionTemplate;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This browser operation offers methods related to focusing on content like other windows or IFrames.
 *
 * @see #onFrame(int)
 * @see #onFrame(String)
 * @see #onWindow(String)
 * @see #onDefaultContent()
 * @since 2.0
 */
@Slf4j
public class FocusSetter extends BaseBrowserOperation {

    /**
     * Creates a new {@link FocusSetter} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public FocusSetter(Browser browser) {
        super(browser);
    }

    /**
     * Sets the browser's focus to the frame with the given index.
     *
     * @param index the index of the frame to focus on
     * @throws NoSuchFrameException in case there is no frame with the given index
     * @see WebDriver.TargetLocator#frame(int)
     * @since 2.0
     */
    public void onFrame(int index) throws NoSuchFrameException {
        ActionTemplate.browser(browser())
            .execute(browser -> doOnFrame(browser, index))
            .fireEvent(browser -> new SwitchedToFrameEvent(index));
        log.debug("focused on frame with index: {}", index);
    }

    private void doOnFrame(Browser browser, int index) {
        browser.webDriver().switchTo().frame(index);
    }

    /**
     * Sets the browser's focus to the frame with the given name or ID.
     *
     * @param nameOrId the name or ID of the frame to focus on
     * @throws NoSuchFrameException in case there is no frame with the given name or ID
     * @see WebDriver.TargetLocator#frame(String)
     * @since 2.0
     */
    public void onFrame(String nameOrId) throws NoSuchFrameException {
        ActionTemplate.browser(browser())
            .execute(browser -> doOnFrame(browser, nameOrId))
            .fireEvent(browser -> new SwitchedToFrameEvent(nameOrId));
        log.debug("focused on frame with name or ID: {}", nameOrId);
    }

    private void doOnFrame(Browser browser, String nameOrId) {
        browser.webDriver().switchTo().frame(nameOrId);
    }

    /**
     * Sets the browser's focus to the frame of the given {@link PageFragment page fragment}.
     *
     * @param frame the page fragment representing the frame to focus on
     * @throws NoSuchFrameException in case there is no frame with the given name or ID
     * @see WebDriver.TargetLocator#frame(String)
     * @since 2.0
     */
    public void onFrame(PageFragment frame) throws NoSuchFrameException {
        ActionTemplate.browser(browser())
            .execute(browser -> doOnFrame(browser, frame))
            .fireEvent(browser -> new SwitchedToFrameEvent(frame));
        log.debug("focused on frame page fragment: {}", frame);
    }

    private void doOnFrame(Browser browser, PageFragment frame) {
        browser.webDriver().switchTo().frame(frame.webElement());
    }

    /**
     * Sets the browser's focus to the window with the given name or handle.
     * <p>
     * <b>Tip:</b> A handle for the current window can be got by using the {@link CurrentWindow#getHandle()} method.
     *
     * @param nameOrHandle the name or handle of the window to focus on
     * @throws NoSuchWindowException in case there is no window with the given name or handle
     * @see Browser#currentWindow()
     * @see CurrentWindow#getHandle()
     * @see WebDriver.TargetLocator#window(String)
     * @since 2.0
     */
    public void onWindow(String nameOrHandle) throws NoSuchWindowException {
        ActionTemplate.browser(browser())
            .execute(browser -> doOnWindow(browser, nameOrHandle))
            .fireEvent(browser -> new SwitchedToWindowEvent(nameOrHandle));
        log.debug("focused on window with name or handle: {}", nameOrHandle);
    }

    private void doOnWindow(Browser browser, String nameOrHandle) {
        browser.webDriver().switchTo().window(nameOrHandle);
    }

    /**
     * Sets the browser's focus to the default content.
     * This is either the first frame or the main content (in case of IFrames).
     *
     * @see WebDriver.TargetLocator#defaultContent()
     * @since 2.0
     */
    public void onDefaultContent() {
        ActionTemplate.browser(browser())
            .execute(this::doOnDefaultContent)
            .fireEvent(browser -> new SwitchedToDefaultContentEvent());
        log.debug("focused on default content");
    }

    private void doOnDefaultContent(Browser browser) {
        browser.webDriver().switchTo().defaultContent();
    }

}
