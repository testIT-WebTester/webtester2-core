package info.novatec.testit.webtester.internal.must;

import info.novatec.testit.webtester.WebTesterException;


public class MustConditionException extends WebTesterException {

    private static final String MESSAGE = "Invocation of @Must annotated method failed! See cause for details:";

    public MustConditionException(String message) {
        super(message);
    }

    public MustConditionException(Throwable cause) {
        super(MESSAGE,cause);
    }

}
