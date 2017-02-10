package info.novatec.testit.webtester.browser.operations;

import org.openqa.selenium.WebDriver;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.events.EventSystem;

@RequiredArgsConstructor
class BaseBrowserOperation {

    @NonNull
    private final Browser browser;

    Browser browser() {
        return browser;
    }

    Configuration configuration() {
        return browser.configuration();
    }

    WebDriver webDriver() {
        return browser.webDriver();
    }

    EventSystem events() {
        return browser().events();
    }

}
