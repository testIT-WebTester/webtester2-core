package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;


public class NumberOfOptionsMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final int numberOfOptions;
    private int actualNumberOfOptions;

    public NumberOfOptionsMatcher(int numberOfOptions) {
        this.numberOfOptions = numberOfOptions;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("<" + numberOfOptions + "> options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualNumberOfOptions = item.getOptionCount();
        return actualNumberOfOptions == numberOfOptions;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has <" + actualNumberOfOptions + "> options");
    }

}
