package info.novatec.testit.webtester.internal.implementation.exceptions;

import info.novatec.testit.webtester.WebTesterException;


public class DynamicImplementationException extends WebTesterException {

    protected DynamicImplementationException(String message) {
        super(message);
    }

    protected DynamicImplementationException(String message, Throwable cause) {
        super(message, cause);
    }

}
