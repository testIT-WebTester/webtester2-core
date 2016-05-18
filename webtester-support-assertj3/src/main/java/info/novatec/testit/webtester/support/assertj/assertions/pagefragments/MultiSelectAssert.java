package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.support.assertj.WebTesterAssertions;


/**
 * Contains assertions for {@link MultiSelect multi selects}.
 *
 * @see WebTesterAssertions
 * @see MultiSelect
 * @since 2.0
 */
public class MultiSelectAssert extends AbstractSelectAssert<MultiSelectAssert, MultiSelect> {

    public MultiSelectAssert(MultiSelect actual) {
        super(actual, MultiSelectAssert.class);
    }

    /**
     * Asserts that the {@link MultiSelect multi select's} selection has the given texts in order.
     *
     * @param texts the expected texts
     * @return same assertion instance for fluent API
     * @see MultiSelect#getSelectionTexts()
     * @since 2.0
     */
    public MultiSelectAssert hasSelectionWithTexts(String... texts) {
        String errorMessage = "Expected select's selection texts to be <%s>, but were <%s>.";
        List<String> actualTexts = actual.getSelectionTexts();
        assertThat(actualTexts).overridingErrorMessage(errorMessage, Arrays.toString(texts), actualTexts)
            .containsExactly(texts);
        return this;
    }

    /**
     * Asserts that the {@link MultiSelect multi select's} selection has the given values in order.
     *
     * @param values the expected values
     * @return same assertion instance for fluent API
     * @see MultiSelect#getSelectionValues()
     * @since 2.0
     */
    public MultiSelectAssert hasSelectionWithValues(String... values) {
        String errorMessage = "Expected select's selection values to be <%s>, but were <%s>.";
        List<String> actualValues = actual.getSelectionValues();
        assertThat(actualValues).overridingErrorMessage(errorMessage, Arrays.toString(values), actualValues)
            .containsExactly(values);
        return this;
    }

    /**
     * Asserts that the {@link MultiSelect multi select's} selection has the given indices in order.
     *
     * @param indices the expected indices
     * @return same assertion instance for fluent API
     * @see MultiSelect#getSelectionValues()
     * @since 2.0
     */
    public MultiSelectAssert hasSelectionWithIndices(Integer... indices) {
        String errorMessage = "Expected select's selection indices to be <%s>, but were <%s>.";
        List<Integer> actualIndices = actual.getSelectionIndices();
        assertThat(actualIndices).overridingErrorMessage(errorMessage, Arrays.toString(indices), actualIndices)
            .containsExactly(indices);
        return this;
    }

    /**
     * Asserts that the {@link MultiSelect multi select} has any selected options.
     *
     * @return same assertion instance for fluent API
     * @see MultiSelect#getSelectionCount()
     * @since 2.0
     */
    public MultiSelectAssert hasSelectedOptions() {
        String errorMessage = "Expected select to have selected options, but it didn't.";
        assertThat(actual.getSelectionCount()).overridingErrorMessage(errorMessage).isNotZero();
        return this;
    }

    /**
     * Asserts that the {@link MultiSelect multi select} has the given number of selected options.
     *
     * @param numberOfSelectedOptions the number of selected options
     * @return same assertion instance for fluent API
     * @see MultiSelect#getSelectionCount()
     * @since 2.0
     */
    public MultiSelectAssert hasSelectedOptions(Integer numberOfSelectedOptions) {
        String errorMessage = "Expected select to have <%s> selected options, but it had <%s>.";
        Integer actualNumber = actual.getSelectionCount();
        assertThat(actualNumber).overridingErrorMessage(errorMessage, numberOfSelectedOptions, actualNumber)
            .isEqualTo(numberOfSelectedOptions);
        return this;
    }

    /**
     * Asserts that the {@link MultiSelect multi select} has no selected options.
     *
     * @return same assertion instance for fluent API
     * @see MultiSelect#getSelectionCount()
     * @since 2.0
     */
    public MultiSelectAssert hasNoSelectedOptions() {
        String errorMessage = "Expected select to have no selected options, but it had <%s>.";
        int actualNumber = actual.getSelectionCount();
        assertThat(actualNumber).overridingErrorMessage(errorMessage, actualNumber).isZero();
        return this;
    }

}
