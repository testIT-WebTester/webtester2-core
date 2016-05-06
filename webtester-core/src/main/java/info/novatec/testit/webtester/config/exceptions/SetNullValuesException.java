package info.novatec.testit.webtester.config.exceptions;

import info.novatec.testit.webtester.config.ConfigurationException;


@SuppressWarnings("serial")
public class SetNullValuesException extends ConfigurationException {

    public SetNullValuesException(String key) {
        super("Null value is not allowed when setting a property! [property:" + key + ']');
    }

}
