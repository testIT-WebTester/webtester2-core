package info.novatec.testit.webtester;

/**
 * Base exception type for all exceptions directly thrown by WebTester.
 *
 * @since 2.0
 */
@SuppressWarnings("serial")
public class WebTesterException extends RuntimeException {

    protected WebTesterException(String message) {
        super(message);
    }

    protected WebTesterException(Throwable cause) {
        super(cause);
    }

    protected WebTesterException(String message, Throwable cause) {
        super(message, cause);
    }

}
