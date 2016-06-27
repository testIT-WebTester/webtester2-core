package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link PageFragment} is invisible.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(fragment, is(invisible()));
 * <p>
 *
 * @param <T> the type of the checked page fragment
 * @see WebTesterMatchers
 * @since 2.0
 */
public class InvisibleMatcher<T extends PageFragment> extends TypeSafeMatcher<T> {

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
