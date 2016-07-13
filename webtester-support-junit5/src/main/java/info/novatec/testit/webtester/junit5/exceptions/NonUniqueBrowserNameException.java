package info.novatec.testit.webtester.junit5.exceptions;

import info.novatec.testit.webtester.browser.Browser;


/**
 * This exception is thrown in case there is more than one managed {@link Browser} with the same name when execution an
 * extension.
 *
 * @since 2.1
 */
public class NonUniqueBrowserNameException extends RuntimeException {
    public NonUniqueBrowserNameException() {
        super("every browser needs a unique name!");
    }
}
