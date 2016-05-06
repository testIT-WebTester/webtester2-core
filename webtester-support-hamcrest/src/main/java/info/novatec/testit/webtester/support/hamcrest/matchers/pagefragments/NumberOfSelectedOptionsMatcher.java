package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class NumberOfSelectedOptionsMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private final int numberOfOptions;
    private int actualNumberOfOptions;

    public NumberOfSelectedOptionsMatcher(int numberOfOptions) {
        this.numberOfOptions = numberOfOptions;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("<" + numberOfOptions + "> selected options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualNumberOfOptions = item.getSelectionCount();
        return actualNumberOfOptions == numberOfOptions;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has <" + actualNumberOfOptions + "> selected options");
    }

}
