package info.novatec.testit.webtester.internal.implementation.pagefragments.exceptions;

import info.novatec.testit.webtester.internal.implementation.exceptions.DynamicImplementationException;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PageFragmentMustBeInterfaceException extends DynamicImplementationException {

    private static final String MESSAGE =
        "The page fragment '%s' is not an interface! Only interfaces can be implemented by WebTester.";

    public PageFragmentMustBeInterfaceException(Class<? extends PageFragment> type) {
        super(String.format(MESSAGE, type.getCanonicalName()));
    }

}
