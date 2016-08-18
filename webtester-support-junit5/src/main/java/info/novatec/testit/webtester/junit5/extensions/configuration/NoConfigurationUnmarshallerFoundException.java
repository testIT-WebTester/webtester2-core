package info.novatec.testit.webtester.junit5.extensions.configuration;

import lombok.Getter;

import info.novatec.testit.webtester.junit5.extensions.TestClassFormatException;
import info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller.ConfigurationUnmarshaller;


/**
 * This exception is thrown in case no default {@link ConfigurationUnmarshaller} is found when unmarshalling a {@link
 * ConfigurationValue}.
 *
 * @since 2.1
 */
@Getter
public class NoConfigurationUnmarshallerFoundException extends TestClassFormatException {

    /** The type for which no {@link ConfigurationUnmarshaller} was found. */
    private final Class<?> type;

    public NoConfigurationUnmarshallerFoundException(Class<?> type) {
        super("No " + ConfigurationUnmarshaller.class.getSimpleName() + " found for: " + type);
        this.type = type;
    }

}
