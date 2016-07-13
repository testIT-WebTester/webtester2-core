package info.novatec.testit.webtester.junit5.exceptions;

public class NoManagedBrowserForNameException extends RuntimeException {
    public NoManagedBrowserForNameException(String name) {
        super("there is no managed browser with the name: " + name);
    }
}
