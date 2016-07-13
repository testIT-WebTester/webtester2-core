package info.novatec.testit.webtester.junit5.exceptions;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.BrowserFactory;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateBrowsersUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.CreateUsing;
import info.novatec.testit.webtester.junit5.extensions.browsers.ManagedBrowserExtension;


/**
 * This exception is thrown in case {@link Browser} injections should be done by the {@link ManagedBrowserExtension} but
 * there was neither a {@link CreateBrowsersUsing} annotation on the class nor a {@link CreateUsing} annotation on the
 * browser field to define which {@link BrowserFactory} to use when creating the browser instance.
 *
 * @since 2.1
 */
public class NoBrowserFactoryException extends RuntimeException {

    public NoBrowserFactoryException() {
        super("No browser factory configured! Either annotate class with " + CreateBrowsersUsing.class
            + " or browser field with " + CreateUsing.class);
    }

}
