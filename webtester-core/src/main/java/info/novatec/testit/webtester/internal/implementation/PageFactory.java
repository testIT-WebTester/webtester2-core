package info.novatec.testit.webtester.internal.implementation;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.implementation.pages.ByteBuddyPageFactory;
import info.novatec.testit.webtester.pages.Page;


public interface PageFactory {

    <T extends Page> T createInstanceOf(Class<T> pageType);

    static PageFactory createInstanceFor(Browser browser) {
        return new ByteBuddyPageFactory(browser);
    }

}
