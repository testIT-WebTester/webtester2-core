package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers;


/**
 * This {@link TypeSafeMatcher} checks weather or not a {@link Button} has a certain label.
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

    /** The expected label. */
    private final String label;

    /** The actual label for a possible mismatch description. */
    private String actualLabel;

    /**
     * Creates a new instance for the given label.
     *
     * @param label the label to check
     * @see ButtonLabelMatcher
     * @since 2.0
     */
    public ButtonLabelMatcher(String label) {
        this.label = label;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("label <" + label + ">");
    }

    @Override
    protected boolean matchesSafely(T item) {
        actualLabel = item.getLabel();
        return label.equals(actualLabel);
    }

    @Override
    protected void describeMismatchSafely(T item, Description mismatchDescription) {
        mismatchDescription.appendText("was <" + actualLabel + ">");
    }

}
