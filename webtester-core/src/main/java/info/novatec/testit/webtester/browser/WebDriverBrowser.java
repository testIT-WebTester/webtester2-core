package info.novatec.testit.webtester.browser;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.adhoc.AdHocFinder;
import info.novatec.testit.webtester.browser.operations.AlertHandler;
import info.novatec.testit.webtester.browser.operations.CurrentWindow;
import info.novatec.testit.webtester.browser.operations.FocusSetter;
import info.novatec.testit.webtester.browser.operations.JavaScriptExecutor;
import info.novatec.testit.webtester.browser.operations.Navigator;
import info.novatec.testit.webtester.browser.operations.PageSourceSaver;
import info.novatec.testit.webtester.browser.operations.ScreenshotTaker;
import info.novatec.testit.webtester.browser.operations.UrlOpener;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.config.builders.DefaultConfigurationBuilder;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.EventSystemImpl;
import info.novatec.testit.webtester.events.browser.ClosedBrowserEvent;
import info.novatec.testit.webtester.internal.PageFactory;
import info.novatec.testit.webtester.pages.Page;


/**
 * Implements {@link Browser browser} by using a wrapped Selenium {@link WebDriver web driver}.
 * <p>
 * This class can be initialized using the static factory methods:
 * <ul>
 * <li><code>WebDriverBrowser.buildForWebDriver(webDriver)</code></li>
 * <li><code>WebDriverBrowser.forWebDriver(webDriver).withConfiguration(config).
 * build()</code></li>
 * </ul>
 *
 * @see Browser
 * @see WebDriver
 * @see BrowserBuilder
 * @see WebDriverBrowser#forWebDriver(WebDriver)
 * @see WebDriverBrowser#buildForWebDriver(WebDriver)
 * @since 2.0
 */
@Slf4j
public final class WebDriverBrowser implements Browser {

    private final Configuration configuration;
    private final WebDriver webDriver;

    private final UrlOpener open;
    private final CurrentWindow window;
    private final Navigator navigate;
    private final AlertHandler alert;
    private final ScreenshotTaker screenshot;
    private final PageSourceSaver pageSource;
    private final JavaScriptExecutor javaScript;
    private final FocusSetter focus;
    private final EventSystem eventSystem;

    private final AdHocFinder adHocFinder;
    private final PageFactory pageFactory;

    private boolean closed;

    private WebDriverBrowser(Configuration configuration, WebDriver webDriver) {

        this.configuration = configuration;
        this.webDriver = webDriver;

        this.open = new UrlOpener(this);
        this.window = new CurrentWindow(this);
        this.navigate = new Navigator(this);
        this.alert = new AlertHandler(this);
        this.screenshot = new ScreenshotTaker(this);
        this.pageSource = new PageSourceSaver(this);
        this.javaScript = new JavaScriptExecutor(this);
        this.focus = new FocusSetter(this);
        this.eventSystem = new EventSystemImpl(this);

        this.adHocFinder = new AdHocFinder(this);
        this.pageFactory = new PageFactory(this);

    }

    @Override
    public <T extends Page> T create(Class<T> pageClass) {
        return pageFactory.page(pageClass);
    }

    @Override
    public void close() {
        if (!closed) {
            log.debug("closing browser {}", this);
            events().fireEvent(new ClosedBrowserEvent());
            webDriver().quit();
            closed = true;
        } else {
            log.debug("browser {} already closed - ignoring call to close", this);
        }
    }

    @Override
    public String currentPageTitle() {
        return StringUtils.defaultString(webDriver().getTitle());
    }

    @Override
    public String currentUrl() {
        return StringUtils.defaultString(webDriver().getCurrentUrl());
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

    @Override
    public WebDriver webDriver() {
        return webDriver;
    }

    @Override
    public UrlOpener open() {
        return open;
    }

    @Override
    public CurrentWindow currentWindow() {
        return window;
    }

    @Override
    public FocusSetter focus() {
        return focus;
    }

    @Override
    public Navigator navigate() {
        return navigate;
    }

    @Override
    public AlertHandler alert() {
        return alert;
    }

    @Override
    public ScreenshotTaker screenshot() {
        return screenshot;
    }

    @Override
    public PageSourceSaver pageSource() {
        return pageSource;
    }

    @Override
    public JavaScriptExecutor javaScript() {
        return javaScript;
    }

    @Override
    public EventSystem events() {
        return eventSystem;
    }

    @Override
    public AdHocFinder finder() {
        return adHocFinder;
    }

    /* factory */

    /**
     * Creates a new {@link WebDriverBrowser browser} using the given {@link WebDriver web driver}.
     *
     * @param webDriver the web driver to use
     * @return the created browser
     * @since 2.0
     */
    public static Browser buildForWebDriver(WebDriver webDriver) {
        return forWebDriver(webDriver).build();
    }

    /**
     * Starts the creation of a new {@link WebDriverBrowser browser} by creating
     * a {@link WebDriverBrowserBuilder builder} using the given
     * {@link WebDriver web driver} as a starting point.
     *
     * @param webDriver the web driver to use
     * @return the created builder
     * @since 2.0
     */
    public static BrowserBuilder forWebDriver(WebDriver webDriver) {
        return new WebDriverBrowserBuilder(webDriver);
    }

    @Slf4j
    private static class WebDriverBrowserBuilder implements BrowserBuilder {

        private WebDriver webDriver;
        private Configuration customConfiguration;

        public WebDriverBrowserBuilder(WebDriver webDriver) {
            this.webDriver = webDriver;
            log.debug("building browser for web driver: {}", webDriver);
        }

        @Override
        public BrowserBuilder withConfiguration(Configuration configuration) {
            this.customConfiguration = configuration;
            log.debug("using custom configuration: {}", configuration);
            return this;
        }

        @Override
        public Browser build() {
            Configuration configuration;
            if (customConfiguration != null) {
                configuration = customConfiguration;
            } else {
                configuration = DefaultConfigurationBuilder.create();
            }
            return new WebDriverBrowser(configuration, webDriver);
        }

    }

}
