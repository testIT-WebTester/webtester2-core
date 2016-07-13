package info.novatec.testit.webtester.junit5.exceptions;

import info.novatec.testit.webtester.browser.Browser;


/**
 * This exception is thrown in case there is no managed {@link Browser} with a given name when execution an extension.
 *
 * @since 2.1
 */
public class NoManagedBrowserForNameException extends RuntimeException {
    public NoManagedBrowserForNameException(String name) {
        super("there is no managed browser with the name: " + name);
    }
}
