package info.novatec.testit.webtester.junit5.exceptions;

import java.lang.reflect.Field;

import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;


/**
 * This exception is thrown in case a static field was wrongfully annotated with the {@link ConfigurationValue} annotation.
 *
 * @since 2.1
 */
public class StaticConfigurationValueFieldsNotSupportedException extends RuntimeException {
    public StaticConfigurationValueFieldsNotSupportedException(Field field) {
        super("@" + ConfigurationValue.class.getSimpleName() + " fields must not be static: " + field);
    }
}
