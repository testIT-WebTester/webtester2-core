package info.novatec.testit.webtester.browser.operations;

import java.net.URL;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.browser.OpenedUrlEvent;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.internal.ActionTemplate;


/**
 * This browser operation offers methods related to the opening of URLs and creation of related {@link Page pages}.
 *
 * @see #defaultEntryPoint()
 * @see #defaultEntryPoint(Class)
 * @see #url(String)
 * @see #url(String, Class)
 * @see #url(URL)
 * @see #url(URL, Class)
 * @since 2.0
 */
@Slf4j
public class Open extends BaseBrowserOperation {

    /**
     * Creates a new {@link Open} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public Open(Browser browser) {
        super(browser);
    }

    /**
     * Navigates to the default entry point configured in {@link Configuration#getDefaultEntryPoint()}.
     *
     * @return the original browser of this operation
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public Browser defaultEntryPoint() {
        String entryPoint = configuration().getDefaultEntryPoint()
            .filter(StringUtils::isNotBlank)
            .orElseThrow(() -> new IllegalStateException("no default entry point defined"));
        return url(entryPoint);
    }

    /**
     * Navigates to the default entry point configured in {@link Configuration#getDefaultEntryPoint()}
     * and return a new {@link Page page} instance of the given class.
     *
     * @param <T> the type of the page
     * @param pageClass the page's class
     * @return the newly created page
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public <T extends Page> T defaultEntryPoint(Class<T> pageClass) {
        return defaultEntryPoint().create(pageClass);
    }

    /**
     * Navigates to the given url and return a new {@link Page page} instance of the given class.
     *
     * @param <T> the type of the page
     * @param url the URL to open
     * @param pageClass the page's class
     * @return the newly created page
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public <T extends Page> T url(URL url, Class<T> pageClass) {
        return url(url).create(pageClass);
    }

    /**
     * Navigates to the given url and return a new {@link Page page} instance of the given class.
     *
     * @param <T> the type of the page
     * @param url the URL to open
     * @param pageClass the page's class
     * @return the newly created page
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public <T extends Page> T url(String url, Class<T> pageClass) {
        return url(url).create(pageClass);
    }

    /**
     * Navigates to the given url.
     *
     * @param url the URL to open
     * @return the original browser of this operation
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public Browser url(URL url) {
        return url(url.toString());
    }

    /**
     * Navigates to the given url.
     *
     * @param url the URL to open
     * @return the original browser of this operation
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public Browser url(String url) {
        ActionTemplate.browser(browser())
            .execute(browser -> webDriver().get(url))
            .fireEvent(browser -> new OpenedUrlEvent(url));
        log.debug("opened URL: {}", url);
        return browser();
    }

}
