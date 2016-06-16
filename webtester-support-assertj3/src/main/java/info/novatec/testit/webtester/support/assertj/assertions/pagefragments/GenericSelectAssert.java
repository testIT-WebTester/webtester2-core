package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import info.novatec.testit.webtester.pagefragments.GenericSelect;
import info.novatec.testit.webtester.support.assertj.WebTesterAssertions;


/**
 * Contains assertions for all {@link GenericSelect selects}.
 *
 * @see WebTesterAssertions
 * @see GenericSelect
 * @since 2.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GenericSelectAssert<A extends GenericSelectAssert, B extends GenericSelect>
    extends AbstractPageFragmentAssert<A, B> {

    public GenericSelectAssert(B actual, Class<A> selfType) {
        super(actual, selfType);
    }

    /**
     * Asserts that the {@link GenericSelect select's} options have the given texts in order.
     *
     * @param texts the expected texts
     * @return same assertion instance for fluent API
     * @see GenericSelect#getOptionTexts()
     * @since 2.0
     */
    public A hasOptionsWithTexts(String... texts) {
        String errorMessage = "Expected select's option texts to be <%s>, but were <%s>.";
        List<String> actualTexts = actual.getOptionTexts();
        assertThat(actualTexts).overridingErrorMessage(errorMessage, Arrays.toString(texts), actualTexts)
            .containsExactly(texts);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link GenericSelect select's} options have the given texts in any order.
     *
     * @param texts the expected texts
     * @return same assertion instance for fluent API
     * @see GenericSelect#getOptionTexts()
     * @since 2.0
     */
    public A hasOptionsWithTextsInAnyOrder(String... texts) {
        String errorMessage = "Expected select's option texts to be <%s> in any order, but were <%s>.";
        List<String> actualTexts = actual.getOptionTexts();
        assertThat(actualTexts).overridingErrorMessage(errorMessage, Arrays.toString(texts), actualTexts)
            .containsOnly(texts);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link GenericSelect select's} options have the given values in order.
     *
     * @param values the expected values
     * @return same assertion instance for fluent API
     * @see GenericSelect#getOptionValues()
     * @since 2.0
     */
    public A hasOptionsWithValues(String... values) {
        String errorMessage = "Expected select's option values to be <%s>, but were <%s>.";
        List<String> actualValues = actual.getOptionValues();
        assertThat(actualValues).overridingErrorMessage(errorMessage, Arrays.toString(values), actualValues)
            .containsExactly(values);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link GenericSelect select's} options have the given values in any order.
     *
     * @param values the expected values
     * @return same assertion instance for fluent API
     * @see GenericSelect#getOptionValues()
     * @since 2.0
     */
    public A hasOptionsWithValuesInAnyOrder(String... values) {
        String errorMessage = "Expected select's option values to be <%s> in any order, but were <%s>.";
        List<String> actualValues = actual.getOptionValues();
        assertThat(actualValues).overridingErrorMessage(errorMessage, Arrays.toString(values), actualValues)
            .containsOnly(values);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link GenericSelect select} has any options.
     *
     * @return same assertion instance for fluent API
     * @see GenericSelect#getOptionCount()
     * @since 2.0
     */
    public A hasOptions() {
        String errorMessage = "Expected select to have options, but it didn't.";
        assertThat(actual.getOptionCount()).overridingErrorMessage(errorMessage).isNotZero();
        return ( A ) this;
    }

    /**
     * Asserts that the {@link GenericSelect select} has the given number of options.
     *
     * @param numberOfOptions the number of options
     * @return same assertion instance for fluent API
     * @see GenericSelect#getOptionCount()
     * @since 2.0
     */
    public A hasOptions(Integer numberOfOptions) {
        String errorMessage = "Expected select to have <%s> options, but it had <%s>.";
        Integer actualNumberOfOptions = actual.getOptionCount();
        assertThat(actualNumberOfOptions).overridingErrorMessage(errorMessage, numberOfOptions, actualNumberOfOptions)
            .isEqualTo(numberOfOptions);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link GenericSelect select} has no options.
     *
     * @return same assertion instance for fluent API
     * @see GenericSelect#getOptionCount()
     * @since 2.0
     */
    public A hasNoOptions() {
        String errorMessage = "Expected select to have no options, but it did have <%s>.";
        int actualNumberOfOptions = actual.getOptionCount();
        assertThat(actualNumberOfOptions).overridingErrorMessage(errorMessage, actualNumberOfOptions).isZero();
        return ( A ) this;
    }

}
