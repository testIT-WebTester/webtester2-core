package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import java.lang.reflect.Field;

import lombok.Getter;

import info.novatec.testit.webtester.junit5.extensions.TestClassFormatException;


/**
 * This exception is thrown in case a static field was wrongfully annotated with the {@link Registered} annotation.
 *
 * @since 2.2
 */
@Getter
public class StaticEventListenerFieldsNotSupportedException extends TestClassFormatException {

    public StaticEventListenerFieldsNotSupportedException(Field field) {
        super("@" + Registered.class.getSimpleName() + " page fields must not be static: " + field);
    }

}
