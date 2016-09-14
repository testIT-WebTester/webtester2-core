package info.novatec.testit.webtester.junit5.exceptions;

import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;


/**
 * This exception is thrown in case any of WebTester's {@link Extension Extensions} is executed with {@link ExtensionContext}
 * without a test class.
 *
 * @since 2.1
 */
public class NoTestClassException extends RuntimeException {

    public NoTestClassException() {
        super("no test class in context");
    }

}
