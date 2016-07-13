package info.novatec.testit.webtester.junit5.exceptions;

import info.novatec.testit.webtester.browser.Browser;


/**
 * This exception is thrown in case at least one managed {@link Browser} is needed in order to execute the operations of an
 * extension but non was defined.
 *
 * @since 2.1
 */
public class NoManagedBrowserException extends RuntimeException {
    public NoManagedBrowserException() {
        super("there are no managed browser to use!");
    }
}
