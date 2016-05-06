package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.support.assertj.WebTesterAssertions;


/**
 * Contains assertions for {@link MultiSelect multi selects}.
 *
 * @see WebTesterAssertions
 * @see SingleSelect
 * @since 2.0
 */
public class SingleSelectAssert extends AbstractSelectAssert<SingleSelectAssert, SingleSelect> {

    public SingleSelectAssert(SingleSelect actual) {
        super(actual, SingleSelectAssert.class);
    }

    /**
     * Asserts that the {@link SingleSelect single select's} selection has the given text.
     * <p>
     * <b>Example:</b>
     * <pre>
     * assertThat(select).hasSelectionWithText("lorem ipsum");
     * </pre>
     *
     * @param text the expected text
     * @return same assertion instance for fluent API
     * @see SingleSelect#getSelectionText()
     * @since 2.0
     */
    public SingleSelectAssert hasSelectionWithText(String text) {
        String errorMessage = "Expected select's selection text to be <%s>, but was <%s>.";
        String actualText = actual.getSelectionText().orElse(null);
        assertThat(actualText).overridingErrorMessage(errorMessage, text, actualText).isEqualTo(text);
        return this;
    }

    /**
     * Asserts that the {@link SingleSelect single select's} selection has the given value.
     * <p>
     * <b>Example:</b>
     * <pre>
     * assertThat(select).hasSelectionWithValue("foo");
     * </pre>
     *
     * @param value the expected value
     * @return same assertion instance for fluent API
     * @see SingleSelect#getSelectionValue()
     * @since 2.0
     */
    public SingleSelectAssert hasSelectionWithValue(String value) {
        String errorMessage = "Expected select's selection value to be <%s>, but was <%s>.";
        String actualValue = actual.getSelectionValue().orElse(null);
        assertThat(actualValue).overridingErrorMessage(errorMessage, value, actualValue).isEqualTo(value);
        return this;
    }

    /**
     * Asserts that the {@link SingleSelect single select's} selection has the given index.
     * <p>
     * <b>Example:</b>
     * <pre>
     * assertThat(select).hasSelectionWithIndex(1);
     * </pre>
     *
     * @param index the expected index
     * @return same assertion instance for fluent API
     * @see SingleSelect#getSelectionIndex()
     * @since 2.0
     */
    public SingleSelectAssert hasSelectionWithIndex(Integer index) {
        String errorMessage = "Expected select's selection index to be <%s>, but was <%s>.";
        Integer actualIndex = actual.getSelectionIndex().orElse(null);
        assertThat(actualIndex).overridingErrorMessage(errorMessage, index, actualIndex).isEqualTo(index);
        return this;
    }

}
