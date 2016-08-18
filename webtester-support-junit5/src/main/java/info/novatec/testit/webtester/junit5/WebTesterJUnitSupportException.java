package info.novatec.testit.webtester.junit5;

import info.novatec.testit.webtester.WebTesterException;


/**
 * This is the base exception class of all exception thrown by the WebTester JUnit 5 support module.
 *
 * @since 2.1
 */
public class WebTesterJUnitSupportException extends WebTesterException {

    protected WebTesterJUnitSupportException(String message) {
        super(message);
    }

}
