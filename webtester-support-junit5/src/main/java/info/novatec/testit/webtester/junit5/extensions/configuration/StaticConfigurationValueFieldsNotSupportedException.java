package info.novatec.testit.webtester.junit5.extensions.configuration;

import java.lang.reflect.Field;

import lombok.Getter;

import info.novatec.testit.webtester.junit5.extensions.TestClassFormatException;


/**
 * This exception is thrown in case a static field was wrongfully annotated with the {@link ConfigurationValue} annotation.
 *
 * @since 2.1
 */
@Getter
public class StaticConfigurationValueFieldsNotSupportedException extends TestClassFormatException {

    /** The static field in question. */
    private final Field field;

    public StaticConfigurationValueFieldsNotSupportedException(Field field) {
        super("@" + ConfigurationValue.class.getSimpleName() + " fields must not be static: " + field);
        this.field = field;
    }

}
