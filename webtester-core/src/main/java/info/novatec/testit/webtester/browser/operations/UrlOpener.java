package info.novatec.testit.webtester.browser.operations;

import java.net.URL;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.browser.OpenedUrlEvent;
import info.novatec.testit.webtester.internal.ActionTemplate;
import info.novatec.testit.webtester.pages.Page;


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
public class UrlOpener extends BaseBrowserOperation {

    /**
     * Creates a new {@link UrlOpener} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public UrlOpener(Browser browser) {
        super(browser);
    }

    /**
     * Navigates to the default entry point configured in {@link Configuration#getDefaultEntryPoint()}.
     *
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public void defaultEntryPoint() {
        String entryPoint = configuration().getDefaultEntryPoint()
            .filter(StringUtils::isNotBlank)
            .orElseThrow(() -> new IllegalStateException("no default entry point defined"));
        url(entryPoint);
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
        defaultEntryPoint();
        return browser().create(pageClass);
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
        url(url);
        return browser().create(pageClass);
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
        url(url);
        return browser().create(pageClass);
    }

    /**
     * Navigates to the given url.
     *
     * @param url the URL to open
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public void url(URL url) {
        url(url.toString());
    }

    /**
     * Navigates to the given url.
     *
     * @param url the URL to open
     * @see org.openqa.selenium.WebDriver#get(String)
     * @since 2.0
     */
    public void url(String url) {
        ActionTemplate.browser(browser())
            .execute(browser -> webDriver().get(url))
            .fireEvent(browser -> new OpenedUrlEvent(url));
        log.debug("opened URL: {}", url);
    }

}
