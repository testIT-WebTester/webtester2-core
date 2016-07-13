package info.novatec.testit.webtester.junit5.exceptions;

import java.lang.reflect.Field;

import info.novatec.testit.webtester.junit5.extensions.pages.Initialized;


public class StaticPageFieldsNotSupportedException extends RuntimeException {
    public StaticPageFieldsNotSupportedException(Field field) {
        super("@" + Initialized.class.getSimpleName() + " page fields must not be static: " + field);
    }
}
