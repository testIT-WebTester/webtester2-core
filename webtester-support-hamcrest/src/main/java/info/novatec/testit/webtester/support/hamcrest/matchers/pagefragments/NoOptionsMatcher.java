package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link GenericSelect} has no options.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(noOptions()));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class NoOptionsMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    /** The actual number of options for a possible mismatch description. */
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
