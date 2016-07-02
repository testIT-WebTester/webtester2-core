package info.novatec.testit.webtester.pagefragments.identification;

import info.novatec.testit.webtester.WebTesterException;


/**
 * This exception is thrown if a {@link ByProducer} could not be initialized.
 *
 * @see ByProducers
 * @since 2.0
 */
public class InvalidByProducerException extends WebTesterException {

    public InvalidByProducerException(String message, Throwable cause) {
        super(message, cause);
    }

}
