package info.novatec.testit.webtester.junit5.exceptions;

public class NonUniqueBrowserNameException extends RuntimeException {
    public NonUniqueBrowserNameException() {
        super("every browser needs a unique name!");
    }
}
