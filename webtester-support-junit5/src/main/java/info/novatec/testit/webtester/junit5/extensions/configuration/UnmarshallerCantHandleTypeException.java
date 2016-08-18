package info.novatec.testit.webtester.junit5.extensions.configuration;

import lombok.Getter;

import info.novatec.testit.webtester.junit5.extensions.TestClassFormatException;
import info.novatec.testit.webtester.junit5.extensions.configuration.unmarshaller.ConfigurationUnmarshaller;


/**
 * This exception is thrown in case a {@link ConfigurationUnmarshaller} is declared but can't handle the type it is used for.
 *
 * @since 2.1
 */
@Getter
public class UnmarshallerCantHandleTypeException extends TestClassFormatException {

    /** The used {@link ConfigurationUnmarshaller} type. */
    private final Class<? extends ConfigurationUnmarshaller> unmarshallerType;
    /** The type the {@link ConfigurationUnmarshaller} was used on. */
    private final Class<?> type;

    public UnmarshallerCantHandleTypeException(Class<? extends ConfigurationUnmarshaller> unmarshallerType, Class<?> type) {
        super(ConfigurationUnmarshaller.class.getSimpleName() + " '" + unmarshallerType + "' can't handle: " + type);
        this.unmarshallerType = unmarshallerType;
        this.type = type;
    }

}
