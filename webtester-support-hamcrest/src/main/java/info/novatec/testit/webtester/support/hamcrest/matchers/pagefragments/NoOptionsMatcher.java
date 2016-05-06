package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;


public class NoOptionsMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    // TODO: Document

    private int options;

    @Override
    public void describeTo(Description description) {
        description.appendText("no options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        options = item.getOptionCount();
        return options == 0;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has <" + options + "> options");
    }

}
