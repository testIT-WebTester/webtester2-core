package info.novatec.testit.webtester.junit5.exceptions;

import java.lang.reflect.Field;

import info.novatec.testit.webtester.junit5.extensions.configuration.ConfigurationValue;


public class StaticConfigurationValueFieldsNotSupportedException extends RuntimeException {
    public StaticConfigurationValueFieldsNotSupportedException(Field field) {
        super("@" + ConfigurationValue.class.getSimpleName() + " fields must not be static: " + field);
    }
}
