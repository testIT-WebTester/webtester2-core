package info.novatec.testit.webtester.junit5.exceptions;

public class NonManagedBrowserForNameException extends RuntimeException {
    public NonManagedBrowserForNameException(String name) {
        super("there is no managed browser with the name: " + name);
    }
}
