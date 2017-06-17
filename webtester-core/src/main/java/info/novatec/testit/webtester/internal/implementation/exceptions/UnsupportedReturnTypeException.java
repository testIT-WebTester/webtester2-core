package info.novatec.testit.webtester.internal.implementation.exceptions;

import java.lang.reflect.Method;


public class UnsupportedReturnTypeException extends DynamicImplementationException {

    private static final String MESSAGE = "Return type of method '%s' is not supported!";

    public UnsupportedReturnTypeException(Method method) {
        super(String.format(MESSAGE, method));
    }

}
