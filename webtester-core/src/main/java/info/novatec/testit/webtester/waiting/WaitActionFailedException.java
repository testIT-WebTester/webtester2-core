package info.novatec.testit.webtester.waiting;

import info.novatec.testit.webtester.WebTesterException;

public class WaitActionFailedException extends WebTesterException {

    private static final String MESSAGE = "WaitAction '%s' could not be performed!";

    protected WaitActionFailedException(Class<? extends WaitAction> action, Throwable cause) {
        super(String.format(MESSAGE, action), cause);
    }
}
