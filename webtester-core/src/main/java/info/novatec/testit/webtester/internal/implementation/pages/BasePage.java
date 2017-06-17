package info.novatec.testit.webtester.internal.implementation.pages;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pages.Page;


public class BasePage implements Page {

    private final Browser browser;

    public BasePage(Browser browser) {
        this.browser = browser;
    }

    @Override
    public Browser browser() {
        return browser;
    }

    @Override
    public <T extends Page> T create(Class<T> pageClass) {
        return browser().create(pageClass);
    }

}
