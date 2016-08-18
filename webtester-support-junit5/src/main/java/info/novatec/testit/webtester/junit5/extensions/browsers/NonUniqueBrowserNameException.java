package info.novatec.testit.webtester.junit5.extensions.browsers;

import lombok.Getter;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.TestClassFormatException;


/**
 * This exception is thrown in case there is more than one {@link Managed} {@link Browser} field declared.
 *
 * @since 2.1
 */
@Getter
public class NonUniqueBrowserNameException extends TestClassFormatException {

    private final String name;

    public NonUniqueBrowserNameException(String name) {
        super("Every browser needs a unique name! There are multiple browsers declared for: " + name);
        this.name = name;
    }

}
