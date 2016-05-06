package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class SelectedMatcher<T extends Selectable<?>> extends TypeSafeMatcher<T> {

    // TODO: Document

    @Override
    public void describeTo(Description description) {
        description.appendText("selected");
    }

    @Override
    protected boolean matchesSafely(T item) {
        return Conditions.selected().test(item);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was not selected");
    }

}
