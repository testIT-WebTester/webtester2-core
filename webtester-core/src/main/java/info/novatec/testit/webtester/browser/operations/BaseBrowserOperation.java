package info.novatec.testit.webtester.browser.operations;

import org.openqa.selenium.WebDriver;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;


public class BaseBrowserOperation {

    private final Browser browser;

    protected BaseBrowserOperation(Browser browser) {
        this.browser = browser;
    }

    protected final Browser browser() {
        return browser;
    }

    protected final Configuration configuration() {
        return browser.configuration();
    }

    protected final WebDriver webDriver() {
        return browser.webDriver();
    }

    protected EventSystem events() {
        return browser().events();
    }

}
