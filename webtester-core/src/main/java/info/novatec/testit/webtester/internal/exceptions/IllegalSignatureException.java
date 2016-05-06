package info.novatec.testit.webtester.internal.exceptions;

import info.novatec.testit.webtester.WebTesterException;


public class IllegalSignatureException extends WebTesterException {

    public IllegalSignatureException(String message) {
        super(message);
    }

    public IllegalSignatureException(Throwable cause) {
        super(cause);
    }

    public IllegalSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

}
