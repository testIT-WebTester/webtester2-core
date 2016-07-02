package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link GenericSelect} has options with a given list of texts.
 * The texts are validated in order, and all expected option texts need to be specified!
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(optionsWithTexts("foo", "bar")));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class OptionsTextsMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    private final List<String> expected;

    /** The actual list of texts for a possible mismatch description. */
    private List<String> actual;

    /**
     * Creates a new instance for the give list of texts.
     *
     * @param expected the expected texts
     * @see OptionsTextsMatcher
     * @since 2.0
     */
    public OptionsTextsMatcher(List<String> expected) {
        this.expected = new LinkedList<>(expected);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("options with texts <" + expected + "> in order");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getOptionTexts();
        return expected.equals(actual);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
