package info.novatec.testit.webtester.internal.postconstruct;

import info.novatec.testit.webtester.WebTesterException;


public class PostConstructMustBeConditionException extends WebTesterException {

    private static final String MESSAGE =
        "Invocation of @PostConstructMustBe annotated method failed! See cause for details:";

    public PostConstructMustBeConditionException(String message) {
        super(message);
    }

    public PostConstructMustBeConditionException(Throwable cause) {
        super(MESSAGE, cause);
    }

}
