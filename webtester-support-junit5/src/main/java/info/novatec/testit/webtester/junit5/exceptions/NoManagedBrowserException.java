package info.novatec.testit.webtester.junit5.exceptions;

public class NoManagedBrowserException extends RuntimeException {
    public NoManagedBrowserException() {
        super("there are no managed browser to use!");
    }
}
