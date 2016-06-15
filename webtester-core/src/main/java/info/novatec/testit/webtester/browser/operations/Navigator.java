package info.novatec.testit.webtester.browser.operations;

import java.util.stream.IntStream;

import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.browser.NavigatedBackwardsEvent;
import info.novatec.testit.webtester.events.browser.NavigatedForwardsEvent;
import info.novatec.testit.webtester.internal.ActionTemplate;


/**
 * This browser operation offers methods related to the navigation of the browser's history.
 *
 * @see #backwards()
 * @see #forwards()
 * @since 2.0
 */
@Slf4j
public class Navigator extends BaseBrowserOperation {

    /**
     * Creates a new {@link Navigator} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public Navigator(Browser browser) {
        super(browser);
    }

    /**
     * Navigates backwards in the {@link Browser} history N times.
     *
     * @param times the number of times the backwards navigation should be executed
     * @see #backwards()
     * @see WebDriver.Navigation#back()
     * @since 2.0
     */
    public void backwards(int times) {
        IntStream.range(0, times).forEach(i -> backwards());
    }

    /**
     * Navigates backwards in the {@link Browser} history. If there is no history
     * available, this method will do nothing.
     *
     * @see WebDriver.Navigation#back()
     * @since 2.0
     */
    public void backwards() {
        ActionTemplate.browser(browser())
            .execute(browser -> browser.webDriver().navigate().back())
            .fireEvent(browser -> new NavigatedBackwardsEvent());
        log.debug("navigated backwards in browser history");
    }

    /**
     * Navigates forwards in the {@link Browser} history N times.
     *
     * @param times the number of times the forwards navigation should be executed
     * @see #forwards()
     * @see WebDriver.Navigation#forward()
     * @since 2.0
     */
    public void forwards(int times) {
        IntStream.range(0, times).forEach(i -> forwards());
    }

    /**
     * Navigates forwards in the {@link Browser} history. If there is no forward
     * page available, this method will do nothing.
     *
     * @see WebDriver.Navigation#forward()
     * @since 2.0
     */
    public void forwards() {
        ActionTemplate.browser(browser())
            .execute(browser -> browser.webDriver().navigate().forward())
            .fireEvent(browser -> new NavigatedForwardsEvent());
        log.debug("navigated forwards in browser history");
    }

}
