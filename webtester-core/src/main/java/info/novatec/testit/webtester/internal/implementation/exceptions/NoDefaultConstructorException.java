package info.novatec.testit.webtester.internal.implementation.exceptions;

public class NoDefaultConstructorException extends DynamicImplementationException {

    private static final String MESSAGE = "Class '%s' does not have an accessible default constructor!";

    public NoDefaultConstructorException(Class<?> type, Throwable cause) {
        super(String.format(MESSAGE, type.getCanonicalName()), cause);
    }

}
