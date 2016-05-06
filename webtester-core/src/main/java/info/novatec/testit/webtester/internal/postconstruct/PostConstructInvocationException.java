package info.novatec.testit.webtester.internal.postconstruct;

import info.novatec.testit.webtester.WebTesterException;


public class PostConstructInvocationException extends WebTesterException {

    private static final String MESSAGE = "Invocation of @PostConstruct annotated method failed! See cause for details:";

    public PostConstructInvocationException(Throwable cause) {
        super(MESSAGE,cause);
    }

}
