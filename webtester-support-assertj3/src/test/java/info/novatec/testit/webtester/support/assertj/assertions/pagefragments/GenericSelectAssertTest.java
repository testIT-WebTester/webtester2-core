package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.GenericSelect;


public class GenericSelectAssertTest {

    @Nested
    class HasOptionsWithTextsAssertion {

        @Test
        void passesForMatchingOptions() {
            GenericSelect fooBarSelect = genericSelectWithTexts("foo", "bar");
            assertThat(fooBarSelect).hasOptionsWithTexts("foo", "bar");
        }

        @Test
        void failsForMatchingOptionsInWrongOrder() {
            GenericSelect fooBarSelect = genericSelectWithTexts("foo", "bar");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooBarSelect).hasOptionsWithTexts("bar", "foo");
            });
            assertThat(exception).hasMessage("Expected select's option texts to be <[bar, foo]>, but were <[foo, bar]>.");
        }

        @Test
        void failsForDifferentOptions() {
            GenericSelect fooSelect = genericSelectWithTexts("foo");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasOptionsWithTexts("xur");
            });
            assertThat(exception).hasMessage("Expected select's option texts to be <[xur]>, but were <[foo]>.");
        }

    }

    @Nested
    class HasOptionsWithTextsInAnyOrderAssertion {

        @Test
        void passesForMatchingOptionsInOrder() {
            GenericSelect fooBarSelect = genericSelectWithTexts("foo", "bar");
            assertThat(fooBarSelect).hasOptionsWithTextsInAnyOrder("foo", "bar");
        }

        @Test
        void passesForMatchingOptionsInWrongOrder() {
            GenericSelect fooBarSelect = genericSelectWithTexts("foo", "bar");
            assertThat(fooBarSelect).hasOptionsWithTextsInAnyOrder("bar", "foo");
        }

        @Test
        void failsForDifferentOptions() {
            GenericSelect fooSelect = genericSelectWithTexts("foo");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasOptionsWithTextsInAnyOrder("xur");
            });
            assertThat(exception).hasMessage("Expected select's option texts to be <[xur]> in any order, but were <[foo]>.");
        }

    }

    @Nested
    class HasOptionsWithValuesAssertion {

        @Test
        void passesForMatchingOptions() {
            GenericSelect fooBarSelect = genericSelectWithValues("foo", "bar");
            assertThat(fooBarSelect).hasOptionsWithValues("foo", "bar");
        }

        @Test
        void failsForMatchingOptionsInWrongOrder() {
            GenericSelect fooBarSelect = genericSelectWithValues("foo", "bar");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooBarSelect).hasOptionsWithValues("bar", "foo");
            });
            assertThat(exception).hasMessage("Expected select's option values to be <[bar, foo]>, but were <[foo, bar]>.");
        }

        @Test
        void failsForDifferentOptions() {
            GenericSelect fooSelect = genericSelectWithValues("foo");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasOptionsWithValues("xur");
            });
            assertThat(exception).hasMessage("Expected select's option values to be <[xur]>, but were <[foo]>.");
        }

    }

    @Nested
    class HasOptionsWithValuesInAnyOrderAssertion {

        @Test
        void passesForMatchingOptionsInOrder() {
            GenericSelect fooBarSelect = genericSelectWithValues("foo", "bar");
            assertThat(fooBarSelect).hasOptionsWithValuesInAnyOrder("foo", "bar");
        }

        @Test
        void passesForMatchingOptionsInWrongOrder() {
            GenericSelect fooBarSelect = genericSelectWithValues("foo", "bar");
            assertThat(fooBarSelect).hasOptionsWithValuesInAnyOrder("bar", "foo");
        }

        @Test
        void failsForDifferentOptions() {
            GenericSelect fooSelect = genericSelectWithValues("foo");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasOptionsWithValuesInAnyOrder("xur");
            });
            assertThat(exception).hasMessage("Expected select's option values to be <[xur]> in any order, but were <[foo]>.");
        }

    }

    @Nested
    class HasOptionsAssertion {

        @Test
        void passesForOneOption() {
            GenericSelect selectWithOneOption = genericSelectWithNumberOfOptions(1);
            assertThat(selectWithOneOption).hasOptions();
        }

        @Test
        void passesForTwoOption() {
            GenericSelect selectWithTwoOptions = genericSelectWithNumberOfOptions(2);
            assertThat(selectWithTwoOptions).hasOptions();
        }

        @Test
        void failsForZeroOptions() {
            GenericSelect selectWithoutOptions = genericSelectWithNumberOfOptions(0);
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(selectWithoutOptions).hasOptions();
            });
            assertThat(exception).hasMessage("Expected select to have options, but it didn't.");
        }

    }

    @Nested
    class HasNumberOfOptionsAssertion {

        @Test
        void passesForMatchingNumberOfOptions() {
            GenericSelect selectWithFifeOptions = genericSelectWithNumberOfOptions(5);
            assertThat(selectWithFifeOptions).hasOptions(5);
        }

        @Test
        void failsForDifferentNumberOfOptions() {
            GenericSelect selectWithFifeOptions = genericSelectWithNumberOfOptions(5);
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(selectWithFifeOptions).hasOptions(1);
            });
            assertThat(exception).hasMessage("Expected select to have <1> options, but it had <5>.");
        }

    }

    @Nested
    class HasNoOptionsAssertion {

        @Test
        void passesForZeroOptions() {
            GenericSelect selectWithoutOptions = genericSelectWithNumberOfOptions(0);
            assertThat(selectWithoutOptions).hasNoOptions();
        }

        @Test
        void failsForOneOption() {
            GenericSelect selectWithOneOption = genericSelectWithNumberOfOptions(1);
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(selectWithOneOption).hasNoOptions();
            });
            assertThat(exception).hasMessage("Expected select to have no options, but it did have <1>.");
        }

    }

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void hasOptionsWithTexts() {
            GenericSelectAssert original = assertThat(genericSelectWithTexts("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithTexts("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasOptionsWithTextsInAnyOrder() {
            GenericSelectAssert original = assertThat(genericSelectWithTexts("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithTextsInAnyOrder("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasOptionsWithValues() {
            GenericSelectAssert original = assertThat(genericSelectWithValues("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithValues("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasOptionsWithValuesInAnyOrder() {
            GenericSelectAssert original = assertThat(genericSelectWithValues("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithValuesInAnyOrder("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasOptions() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(5));
            GenericSelectAssert returned = original.hasOptions();
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNumberOfOptions() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(5));
            GenericSelectAssert returned = original.hasOptions(5);
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNoOptions() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(0));
            GenericSelectAssert returned = original.hasNoOptions();
            assertThat(returned).isSameAs(original);
        }

    }

    GenericSelect genericSelectWithTexts(String... texts) {
        GenericSelect select = mock(GenericSelect.class);
        doReturn(Arrays.asList(texts)).when(select).getOptionTexts();
        return select;
    }

    GenericSelect genericSelectWithValues(String... values) {
        GenericSelect select = mock(GenericSelect.class);
        doReturn(Arrays.asList(values)).when(select).getOptionValues();
        return select;
    }

    GenericSelect genericSelectWithNumberOfOptions(int numberOfOptions) {
        GenericSelect select = mock(GenericSelect.class);
        doReturn(numberOfOptions).when(select).getOptionCount();
        return select;
    }

}
