package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.unit.MockFactory.multiSelect;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class MultiSelectAssertTest {

    @Nested
    class HasSelectionWithTextsAssertion {

        @Test
        void passesForMatchingSelection() {
            MultiSelect fooBarSelect = multiSelect().withSelectedTexts("foo", "bar").build();
            assertThat(fooBarSelect).hasSelectionWithTexts("foo", "bar");
        }

        @Test
        void failsForMatchingSelectionInWrongOrder() {
            MultiSelect fooBarSelect = multiSelect().withSelectedTexts("foo", "bar").build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooBarSelect).hasSelectionWithTexts("bar", "foo");
            });
            assertThat(exception).hasMessage("Expected select's selected texts to be <[bar, foo]>, but they were <[foo, bar]>.");
        }

        @Test
        void failsForDifferentSelection() {
            MultiSelect fooSelect = multiSelect().withSelectedTexts("foo").build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasSelectionWithTexts("bar");
            });
            assertThat(exception).hasMessage("Expected select's selected texts to be <[bar]>, but they were <[foo]>.");
        }

    }

    @Nested
    class HasSelectionWithValuesAssertion {

        @Test
        void passesForMatchingSelection() {
            MultiSelect fooBarSelect = multiSelect().withSelectedValues("foo", "bar").build();
            assertThat(fooBarSelect).hasSelectionWithValues("foo", "bar");
        }

        @Test
        void failsForMatchingSelectionInWrongOrder() {
            MultiSelect fooBarSelect = multiSelect().withSelectedValues("foo", "bar").build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooBarSelect).hasSelectionWithValues("bar", "foo");
            });
            assertThat(exception).hasMessage("Expected select's selected values to be <[bar, foo]>, but they were <[foo, bar]>.");
        }

        @Test
        void failsForDifferentSelection() {
            MultiSelect fooSelect = multiSelect().withSelectedValues("foo").build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasSelectionWithValues("bar");
            });
            assertThat(exception).hasMessage("Expected select's selected values to be <[bar]>, but they were <[foo]>.");
        }

    }

    @Nested
    class HasSelectionWithIndicesAssertion {

        @Test
        void passesForMatchingSelection() {
            MultiSelect oneTwoSelect = multiSelect().withSelectedIndices(1, 2).build();
            assertThat(oneTwoSelect).hasSelectionWithIndices(1, 2);
        }

        @Test
        void failsForMatchingSelectionInWrongOrder() {
            MultiSelect oneTwoSelect = multiSelect().withSelectedIndices(1, 2).build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(oneTwoSelect).hasSelectionWithIndices(2, 1);
            });
            assertThat(exception).hasMessage("Expected select's selected indices to be <[2, 1]>, but they were <[1, 2]>.");
        }

        @Test
        void failsForDifferentSelection() {
            MultiSelect oneSelect = multiSelect().withSelectedIndices(1).build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(oneSelect).hasSelectionWithIndices(2);
            });
            assertThat(exception).hasMessage("Expected select's selected indices to be <[2]>, but they were <[1]>.");
        }

    }

    @Nested
    class HasSelectedOptionsAssertion {

        @Test
        void passesForOneSelection() {
            MultiSelect oneSelectedOption = multiSelect().withNumberOfSelectedOptions(1).build();
            assertThat(oneSelectedOption).hasSelectedOptions();
        }

        @Test
        void passesForTwiSelections() {
            MultiSelect twoSelectedOptions = multiSelect().withNumberOfSelectedOptions(2).build();
            assertThat(twoSelectedOptions).hasSelectedOptions();
        }

        @Test
        void failsForZeroSelections() {
            MultiSelect noSelectedOptions = multiSelect().withNumberOfSelectedOptions(0).build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(noSelectedOptions).hasSelectedOptions();
            });
            assertThat(exception).hasMessage("Expected select to have selected options, but it didn't.");
        }

    }

    @Nested
    class HasNumberOfSelectedOptionsAssertion {

        @Test
        void passesForMatchingNumberOfSelections() {
            MultiSelect fiveSelectedOptions = multiSelect().withNumberOfSelectedOptions(5).build();
            assertThat(fiveSelectedOptions).hasSelectedOptions(5);
        }

        @Test
        void failsForDifferentNumberOfSelections() {
            MultiSelect fiveSelectedOptions = multiSelect().withNumberOfSelectedOptions(5).build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fiveSelectedOptions).hasSelectedOptions(1);
            });
            assertThat(exception).hasMessage("Expected select to have <1> selected options, but it had <5>.");
        }

    }

    @Nested
    class HasNoSelectedOptionsAssertion {

        @Test
        void passesForZeroSelections() {
            MultiSelect noSelectedOptions = multiSelect().withNumberOfSelectedOptions(0).build();
            assertThat(noSelectedOptions).hasNoSelectedOptions();
        }

        @Test
        void failsForOneSelection() {
            MultiSelect oneSelectedOption = multiSelect().withNumberOfSelectedOptions(1).build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(oneSelectedOption).hasNoSelectedOptions();
            });
            assertThat(exception).hasMessage("Expected select to have no selected options, but it had <1>.");
        }

    }

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void hasSelectionWithTexts() {
            MultiSelect select = multiSelect().withSelectedTexts("foo", "bar").build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithTexts("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasSelectionWithValues() {
            MultiSelect select = multiSelect().withSelectedValues("v1", "v2").build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithValues("v1", "v2");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasSelectionWithIndices() {
            MultiSelect select = multiSelect().withSelectedIndices(1, 2).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithIndices(1, 2);
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasSelectedOptions() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(5).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectedOptions();
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNumberOfSelectedOptions() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(5).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectedOptions(5);
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNoSelectedOptions() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(0).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasNoSelectedOptions();
            assertThat(returned).isSameAs(original);
        }

    }

}
