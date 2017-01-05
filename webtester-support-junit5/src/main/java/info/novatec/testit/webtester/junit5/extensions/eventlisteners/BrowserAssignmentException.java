package info.novatec.testit.webtester.junit5.extensions.eventlisteners;

import java.lang.reflect.Field;

import info.novatec.testit.webtester.WebTesterException;


@SuppressWarnings("serial")
public class BrowserAssignmentException extends WebTesterException {

    protected BrowserAssignmentException(Field eventListenerField) {
        super("Field '" + eventListenerField.getName() + "' can't be assigned to a browser. Specify a target browser!");
    }

}
