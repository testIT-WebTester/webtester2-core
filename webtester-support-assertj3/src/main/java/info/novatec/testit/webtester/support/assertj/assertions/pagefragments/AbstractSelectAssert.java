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
public abstract class AbstractSelectAssert<A extends AbstractSelectAssert, B extends GenericSelect>
    extends AbstractPageFragmentAssert<A, B> {

    protected AbstractSelectAssert(B actual, Class<A> selfType) {
        super(actual, selfType);
    }

    // TODO document

    public A hasOptionsWithTexts(String... texts) {
        String errorMessage = "Expected select's option texts to be <%s>, but were <%s>.";
        List<String> actualTexts = actual.getOptionTexts();
        assertThat(actualTexts).overridingErrorMessage(errorMessage, Arrays.toString(texts), actualTexts)
            .containsExactly(texts);
        return ( A ) this;
    }

    public A hasOptionsWithTextsInAnyOrder(String... texts) {
        String errorMessage = "Expected select's option texts to be <%s> in any order, but were <%s>.";
        List<String> actualTexts = actual.getOptionTexts();
        assertThat(actualTexts).overridingErrorMessage(errorMessage, Arrays.toString(texts), actualTexts)
            .containsOnly(texts);
        return ( A ) this;
    }

    public A hasOptionsWithValues(String... values) {
        String errorMessage = "Expected select's option values to be <%s>, but were <%s>.";
        List<String> actualValues = actual.getOptionValues();
        assertThat(actualValues).overridingErrorMessage(errorMessage, Arrays.toString(values), actualValues)
            .containsExactly(values);
        return ( A ) this;
    }

    public A hasOptionsWithValuesInAnyOrder(String... values) {
        String errorMessage = "Expected select's option values to be <%s> in any order, but were <%s>.";
        List<String> actualValues = actual.getOptionValues();
        assertThat(actualValues).overridingErrorMessage(errorMessage, Arrays.toString(values), actualValues)
            .containsOnly(values);
        return ( A ) this;
    }

    public A hasOptions() {
        String errorMessage = "Expected select to have options, but it didn't.";
        assertThat(actual.getOptionCount()).overridingErrorMessage(errorMessage).isNotZero();
        return ( A ) this;
    }

    public A hasOptions(Integer numberOfOptions) {
        String errorMessage = "Expected select to have <%s> options, but it had <%s>.";
        Integer actualNumberOfOptions = actual.getOptionCount();
        assertThat(actualNumberOfOptions).overridingErrorMessage(errorMessage, numberOfOptions, actualNumberOfOptions)
            .isEqualTo(numberOfOptions);
        return ( A ) this;
    }

    public A hasNoOptions() {
        String errorMessage = "Expected select to have no options, but it did have <%s>.";
        int actualNumberOfOptions = actual.getOptionCount();
        assertThat(actualNumberOfOptions).overridingErrorMessage(errorMessage, actualNumberOfOptions).isZero();
        return ( A ) this;
    }

}
