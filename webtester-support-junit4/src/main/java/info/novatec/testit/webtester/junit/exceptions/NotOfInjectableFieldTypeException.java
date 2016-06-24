package info.novatec.testit.webtester.junit.exceptions;

import java.lang.reflect.Field;


@SuppressWarnings("serial")
public class NotOfInjectableFieldTypeException extends IllegalTestClassStructureException {

    public NotOfInjectableFieldTypeException(Field field) {
        super("the configuration value field '" + field
            + "' is not of an injectable type! Injectable types are: Boolean, Integer, Long, Float, Double and String");
    }

}
