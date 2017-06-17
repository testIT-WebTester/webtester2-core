package info.novatec.testit.webtester.internal.implementation.pages.exceptions;

import info.novatec.testit.webtester.internal.implementation.exceptions.DynamicImplementationException;
import info.novatec.testit.webtester.pages.Page;


public class PageMustBeInterfaceException extends DynamicImplementationException {

    private static final String MESSAGE =
        "The page '%s' is not an interface! Only interfaces can be implemented by WebTester.";

    public PageMustBeInterfaceException(Class<? extends Page> type) {
        super(String.format(MESSAGE, type.getCanonicalName()));
    }

}
