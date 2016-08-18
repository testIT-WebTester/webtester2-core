package info.novatec.testit.webtester.junit5.extensions;

import info.novatec.testit.webtester.junit5.WebTesterJUnitSupportException;


/**
 * This is the base class for all exceptions regarding the format of a test class.
 *
 * @since 2.1
 */
public class TestClassFormatException extends WebTesterJUnitSupportException {

    protected TestClassFormatException(String message) {
        super(message);
    }

}
