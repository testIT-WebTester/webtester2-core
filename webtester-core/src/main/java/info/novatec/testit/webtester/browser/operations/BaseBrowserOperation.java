package info.novatec.testit.webtester.browser.operations;

import org.openqa.selenium.WebDriver;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;

@AllArgsConstructor
public class BaseBrowserOperation {

    @NonNull
    private final Browser browser;

    protected Browser browser() {
        return browser;
    }

    protected Configuration configuration() {
        return browser.configuration();
    }

    protected WebDriver webDriver() {
        return browser.webDriver();
    }

    protected EventSystem events() {
        return browser().events();
    }

}
