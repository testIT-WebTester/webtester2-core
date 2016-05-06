package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;


public class OptionsMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    // TODO: Document

    @Override
    public void describeTo(Description description) {
        description.appendText("options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        return item.getOptionCount() > 0;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has no options");
    }

}
