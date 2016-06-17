package info.novatec.testit.webtester.config;

import info.novatec.testit.webtester.WebTesterException;


@SuppressWarnings("serial")
public class ConfigurationException extends WebTesterException {

    protected ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    protected ConfigurationException(String message) {
        super(message);
    }

}
