package info.novatec.testit.webtester.junit5.extensions;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;

import info.novatec.testit.webtester.junit5.WebTesterJUnitSupportException;


/**
 * This exception is thrown in case any of WebTester's {@link Extension Extensions} is executed with {@link ExtensionContext}
 * without a test class.
 *
 * @since 2.1
 */
public class NoTestClassException extends WebTesterJUnitSupportException {

    public NoTestClassException() {
        super("There is no test class in the extension context!");
    }

}
