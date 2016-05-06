package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


public class InvisibleMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

    // TODO: Document

    @Override
    public void describeTo(Description description) {
        description.appendText("invisible");
    }

    @Override
    protected boolean matchesSafely(T item) {
        return Conditions.invisible().test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was not invisible");
    }

}
