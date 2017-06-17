package info.novatec.testit.webtester.internal.implementation.pages.exceptions;

import info.novatec.testit.webtester.internal.implementation.exceptions.DynamicImplementationException;
import info.novatec.testit.webtester.pages.Page;


public class PageCreationException extends DynamicImplementationException {

    private static final String MESSAGE = "Could not create instance of '%s':";

    public PageCreationException(Class<? extends Page> pageType, Throwable cause) {
        super(String.format(MESSAGE, pageType.getCanonicalName()), cause);
    }

}
