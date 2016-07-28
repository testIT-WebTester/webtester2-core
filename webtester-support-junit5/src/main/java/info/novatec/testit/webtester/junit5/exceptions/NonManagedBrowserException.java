package info.novatec.testit.webtester.junit5.exceptions;

public class NonManagedBrowserException extends RuntimeException {
    public NonManagedBrowserException() {
        super("there are no managed browser to use!");
    }
}
