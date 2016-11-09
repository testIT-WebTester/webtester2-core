package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link GenericSelect} has options with a given list of values.
 * The values are validated in order, and all expected option values need to be specified!
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(optionsWithValues("foo", "bar")));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class OptionsValuesMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    private final List<String> expected;

    /** The actual list of values for a possible mismatch description. */
    private List<String> actual;

    /**
     * Creates a new instance for the give list of values.
     *
     * @param expected the expected values
     * @see OptionsValuesMatcher
     * @since 2.0
     */
    public OptionsValuesMatcher(List<String> expected) {
        this.expected = new ArrayList<>(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("options with values <" + expected + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getOptionValues();
        return expected.equals(actual);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
