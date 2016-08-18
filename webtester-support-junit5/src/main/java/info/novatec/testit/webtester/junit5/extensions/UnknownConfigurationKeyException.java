package info.novatec.testit.webtester.junit5.extensions;

import lombok.Getter;

import info.novatec.testit.webtester.config.Configuration;


/**
 * This exception is thrown in case no value for a given key could be found in a {@link Configuration}.
 *
 * @since 2.1
 */
@Getter
public class UnknownConfigurationKeyException extends TestClassFormatException {

    /** The unknown configuration key. */
    private final String key;

    public UnknownConfigurationKeyException(String key) {
        super("No value found for configuration key: '" + key + "'");
        this.key = key;
    }

}
