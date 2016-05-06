package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class NoSelectedOptionsMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    private int selectedOptions;

    @Override
    public void describeTo(Description description) {
        description.appendText("no selected options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        selectedOptions = item.getSelectionCount();
        return selectedOptions == 0;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has <" + selectedOptions + "> selected options");
    }

}
