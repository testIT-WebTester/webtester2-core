package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.GenericSelect;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link GenericSelect} has a specific number of options.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(select, has(options(5)));
 * <p>
 *
 * @param <T> the type of the checked select
 * @see WebTesterMatchers
 * @since 2.0
 */
public class NumberOfOptionsMatcher<T extends GenericSelect<T>> extends TypeSafeMatcher<T> {

    /** The expected number of options. */
    private final int numberOfOptions;

    /** The actual number of options for a possible mismatch description. */
    private int actualNumberOfOptions;

    /**
     * Creates a new instance for the given number of options.
     *
     * @param numberOfOptions the number of options
     * @see NumberOfOptionsMatcher
     * @since 2.0
     */
    public NumberOfOptionsMatcher(int numberOfOptions) {
        this.numberOfOptions = numberOfOptions;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("<" + numberOfOptions + "> options");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualNumberOfOptions = item.getOptionCount();
        return actualNumberOfOptions == numberOfOptions;
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("has <" + actualNumberOfOptions + "> options");
    }

}
