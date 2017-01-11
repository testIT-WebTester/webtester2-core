package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import java.lang.reflect.Field;

import info.novatec.testit.webtester.WebTesterException;


@SuppressWarnings("serial")
public class NoManagedBrowserForNameException extends WebTesterException {

    protected NoManagedBrowserForNameException(Field eventListenerField) {
        super("Field '" + eventListenerField.getName() + "' can't be assigned to a browser. Specify a target browser!");
    }

}
