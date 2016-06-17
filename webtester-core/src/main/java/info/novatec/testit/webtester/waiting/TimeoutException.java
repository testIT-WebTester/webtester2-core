package info.novatec.testit.webtester.waiting;

import info.novatec.testit.webtester.WebTesterException;

public class TimeoutException extends WebTesterException {

    public TimeoutException(String message) {
        super(message);
    }

    public TimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

}
