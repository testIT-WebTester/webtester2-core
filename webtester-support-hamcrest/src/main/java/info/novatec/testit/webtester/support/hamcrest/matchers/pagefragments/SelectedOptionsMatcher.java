package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class SelectedOptionsMatcher<T extends MultiSelect> extends TypeSafeMatcher<T> {

    // TODO: Document

    @Override
    public void describeTo(Description description) {
        description.appendText("selected options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        return item.getSelectionCount() > 0;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has no selected options");
    }

}
