package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks whether or not a {@link Button} has a certain label.
 * Instances of this matcher should be initialized using the {@link WebTesterMatchers} factory class.
 * <p>
 * <b>Example:</b> assertThat(button, has(label("type")));
 * <p>
 *
 * @param <T> the type of the checked button
 * @see WebTesterMatchers
 * @since 2.0
 */
public class ButtonLabelMatcher<T extends Button> extends TypeSafeMatcher<T> {

    private final String expected;

    /** The actual label for a possible mismatch description. */
    private String actual;

    /**
     * Creates a new instance for the given label.
     *
     * @param expected the label to check
     * @see ButtonLabelMatcher
     * @since 2.0
     */
    public ButtonLabelMatcher(String expected) {
        this.expected = expected;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("label <" + expected + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actual = item.getLabel();
        return expected.equals(actual);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actual + ">");
    }

}
