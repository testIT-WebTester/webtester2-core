package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.support.assertj.WebTesterAssertions;


/**
 * Contains assertions for {@link Button buttons}.
 *
 * @see WebTesterAssertions
 * @see Button
 * @since 2.0
 */
public class ButtonAssert extends AbstractPageFragmentAssert<ButtonAssert, Button> {

    public ButtonAssert(Button actual) {
        super(actual, ButtonAssert.class);
    }

    /**
     * Asserts that the {@link Button button's} label is equal to the given label.
     *
     * @param label the expected label
     * @return same assertion instance for fluent API
     * @see Button#getLabel()
     * @since 2.0
     */
    public ButtonAssert hasLabel(String label) {
        String errorMessage = "Expected buttons's label to be <%s>, but was <%s>.";
        String actualLabel = actual.getLabel();
        assertThat(actualLabel).overridingErrorMessage(errorMessage, label, actualLabel).isEqualTo(label);
        return this;
    }

    /**
     * Asserts that the {@link Button button's} label is not equal to the given label.
     *
     * @param label the not expected label
     * @return same assertion instance for fluent API
     * @see Button#getLabel()
     * @since 2.0
     */
    public ButtonAssert hasNotLabel(String label) {
        String errorMessage = "Expected buttons's label not to be <%s>, but it was.";
        String actualLabel = actual.getLabel();
        assertThat(actualLabel).overridingErrorMessage(errorMessage, label).isNotEqualTo(label);
        return this;
    }

}
