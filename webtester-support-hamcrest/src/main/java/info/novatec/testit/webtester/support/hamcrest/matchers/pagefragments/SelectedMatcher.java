package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link Selectable} is selected.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(checkbox, is(selected()));
 * <p>
 *
 * @param <T> the type of the checked selectable
 * @see WebTesterMatchers
 * @since 2.0
 */
public class SelectedMatcher<T extends Selectable<?>> extends TypeSafeMatcher<T> {

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
