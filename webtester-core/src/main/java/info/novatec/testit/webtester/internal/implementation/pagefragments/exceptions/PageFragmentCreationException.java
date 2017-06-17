package info.novatec.testit.webtester.internal.implementation.pagefragments.exceptions;

import info.novatec.testit.webtester.internal.implementation.exceptions.DynamicImplementationException;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PageFragmentCreationException extends DynamicImplementationException {

    private static final String MESSAGE = "Could not create instance of '%s':";

    public PageFragmentCreationException(Class<? extends PageFragment> pageFragmentType, Throwable cause) {
        super(String.format(MESSAGE, pageFragmentType.getCanonicalName()), cause);
    }

}
