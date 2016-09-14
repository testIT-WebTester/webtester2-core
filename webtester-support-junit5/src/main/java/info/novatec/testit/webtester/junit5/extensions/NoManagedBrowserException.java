package info.novatec.testit.webtester.junit5.extensions;

import info.novatec.testit.webtester.browser.Browser;


/**
 * This exception is thrown in case at least one managed {@link Browser} is needed in order to execute the operations of an
 * extension but non was defined.
 *
 * @since 2.1
 */
public class NoManagedBrowserException extends TestClassFormatException {

    public NoManagedBrowserException() {
        super("There is no managed browser to use!");
    }

}
