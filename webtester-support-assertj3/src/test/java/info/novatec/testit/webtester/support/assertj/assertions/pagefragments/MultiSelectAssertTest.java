package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static utils.MockFactory.multiSelect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.MultiSelect;


public class MultiSelectAssertTest {

    @Nested
    @DisplayName("hasSelectionWithTexts(..) can be asserted")
    class HasSelectionWithTexts {

        @Test
        @DisplayName("having selection with matching texts passes")
        void havingSelectedTextsPasses() {
            MultiSelect select = multiSelect().withSelectedTexts("foo", "bar").build();
            assertThat(select).hasSelectionWithTexts("foo", "bar");
        }

        @Test
        @DisplayName("having selection with matching texts in wrong order fails")
        void havingSelectedTextsButInWrongOrderFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withSelectedTexts("foo", "bar").build();
                assertThat(select).hasSelectionWithTexts("bar", "foo");
            })).hasMessage("Expected select's selected texts to be <[bar, foo]>, but they were <[foo, bar]>.");
        }

        @Test
        @DisplayName("having selection with different texts fails")
        void notHavingAllSelectedTextsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withSelectedTexts("foo").build();
                assertThat(select).hasSelectionWithTexts("bar");
            })).hasMessage("Expected select's selected texts to be <[bar]>, but they were <[foo]>.");
        }

    }

    @Nested
    @DisplayName("hasSelectionWithValues(..) can be asserted")
    class HasSelectionWithValues {

        @Test
        @DisplayName("having selection with matching values passes")
        void havingSelectedValuesPasses() {
            MultiSelect select = multiSelect().withSelectedValues("v1", "v2").build();
            assertThat(select).hasSelectionWithValues("v1", "v2");
        }

        @Test
        @DisplayName("having selection with matching values in wrong order fails")
        void havingSelectedValuesButInWrongOrderFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withSelectedValues("v1", "v2").build();
                assertThat(select).hasSelectionWithValues("v2", "v1");
            })).hasMessage("Expected select's selected values to be <[v2, v1]>, but they were <[v1, v2]>.");
        }

        @Test
        @DisplayName("having selection with different values fails")
        void notHavingAllSelectedValuesFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withSelectedValues("v1").build();
                assertThat(select).hasSelectionWithValues("v2");
            })).hasMessage("Expected select's selected values to be <[v2]>, but they were <[v1]>.");
        }

    }

    @Nested
    @DisplayName("hasSelectionWithIndices(..) can be asserted")
    class HasSelectionWithIndices {

        @Test
        @DisplayName("having selection with matching indices passes")
        void havingSelectedIndicesPasses() {
            MultiSelect select = multiSelect().withSelectedIndices(1, 2).build();
            assertThat(select).hasSelectionWithIndices(1, 2);
        }

        @Test
        @DisplayName("having selection with matching indices in wrong order fails")
        void havingSelectedIndicesButInWrongOrderFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withSelectedIndices(1, 2).build();
                assertThat(select).hasSelectionWithIndices(2, 1);
            })).hasMessage("Expected select's selected indices to be <[2, 1]>, but they were <[1, 2]>.");
        }

        @Test
        @DisplayName("having selection with different indices fails")
        void notHavingAllSelectedIndicesFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withSelectedIndices(1).build();
                assertThat(select).hasSelectionWithIndices(2);
            })).hasMessage("Expected select's selected indices to be <[2]>, but they were <[1]>.");
        }

    }

    @Nested
    @DisplayName("hasSelectedOptions() can be asserted")
    class HasSelectedOptions {

        @Test
        @DisplayName("having at least one selected option passes")
        void havingSelectedOptionsPasses() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(1).build();
            assertThat(select).hasSelectedOptions();
        }

        @Test
        @DisplayName("having no selected options fails")
        void notHavingAnySelectedOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withNumberOfSelectedOptions(0).build();
                assertThat(select).hasSelectedOptions();
            })).hasMessage("Expected select to have selected options, but it didn't.");
        }

    }

    @Nested
    @DisplayName("hasSelectedOptions(..) can be asserted")
    class HasExactNumberOfSelectedOptions {

        @Test
        @DisplayName("having matching number of selected options passes")
        void havingExactNumberOfSelectedOptionsPasses() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(5).build();
            assertThat(select).hasSelectedOptions(5);
        }

        @Test
        @DisplayName("having different number of selected options fails")
        void notHavingExactNumberOfSelectedOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withNumberOfSelectedOptions(5).build();
                assertThat(select).hasSelectedOptions(1);
            })).hasMessage("Expected select to have <1> selected options, but it had <5>.");
        }

    }

    @Nested
    @DisplayName("hasNoSelectedOptions() can be asserted")
    class HasNoSelectedOptions {

        @Test
        @DisplayName("having no selected options passes")
        void havingNoSelectedOptionsPasses() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(0).build();
            assertThat(select).hasNoSelectedOptions();
        }

        @Test
        @DisplayName("having even one selected option fails")
        void havingSelectedOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                MultiSelect select = multiSelect().withNumberOfSelectedOptions(1).build();
                assertThat(select).hasNoSelectedOptions();
            })).hasMessage("Expected select to have no selected options, but it had <1>.");
        }

    }

    @Nested
    @DisplayName("assertions provide fluent API")
    class AssertionProvidesFluentApi {

        @Test
        @DisplayName("hasSelectionWithTexts(..)")
        void hasSelectionWithTexts() {
            MultiSelect select = multiSelect().withSelectedTexts("foo", "bar").build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithTexts("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasSelectionWithValues(..)")
        void hasSelectionWithValues() {
            MultiSelect select = multiSelect().withSelectedValues("v1", "v2").build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithValues("v1", "v2");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasSelectionWithIndices(..)")
        void hasSelectionWithIndices() {
            MultiSelect select = multiSelect().withSelectedIndices(1, 2).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectionWithIndices(1, 2);
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasSelectedOptions()")
        void hasSelectedOptions() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(5).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectedOptions();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasSelectedOptions(..)")
        void hasSelectedOptionsWithNumber() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(5).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasSelectedOptions(5);
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasNoSelectedOptions()")
        void hasNoSelectedOptions() {
            MultiSelect select = multiSelect().withNumberOfSelectedOptions(0).build();
            MultiSelectAssert original = assertThat(select);
            MultiSelectAssert returned = original.hasNoSelectedOptions();
            assertThat(returned).isSameAs(original);
        }

    }

}
