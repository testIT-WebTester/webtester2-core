package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.GenericSelect;


public class GenericSelectAssertTest {

    @Nested
    @DisplayName("hasOptionsWithTexts(..) can be asserted")
    class HasOptionsWithTexts {

        @Test
        @DisplayName("having options with matching texts passes")
        void havingAllOptionsPasses() {
            assertThat(genericSelectWithTexts("foo", "bar")).hasOptionsWithTexts("foo", "bar");
        }

        @Test
        @DisplayName("having options with matching texts in wrong order fails")
        void havingOptionsButInWrongOrderFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithTexts("foo", "bar")).hasOptionsWithTexts("bar", "foo");
            })).hasMessage("Expected select's option texts to be <[bar, foo]>, but were <[foo, bar]>.");
        }

        @Test
        @DisplayName("having options with different texts fails")
        void notHavingAllOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithTexts("foo")).hasOptionsWithTexts("xur");
            })).hasMessage("Expected select's option texts to be <[xur]>, but were <[foo]>.");
        }

    }

    @Nested
    @DisplayName("hasOptionsWithTextsInAnyOrder(..) can be asserted")
    class HasOptionsWithTextsInAnyOrder {

        @Test
        @DisplayName("having options with matching texts in correct order passes")
        void havingOptionsInCorrectOrderPasses() {
            GenericSelect select = genericSelectWithTexts("foo", "bar");
            assertThat(select).hasOptionsWithTextsInAnyOrder("foo", "bar");
        }

        @Test
        @DisplayName("having options with matching texts in wrong order passes")
        void havingOptionsInWrongOrderPasses() {
            GenericSelect select = genericSelectWithTexts("foo", "bar");
            assertThat(select).hasOptionsWithTextsInAnyOrder("bar", "foo");
        }

        @Test
        @DisplayName("having options with different texts fails")
        void notHavingAllOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithTexts("foo")).hasOptionsWithTextsInAnyOrder("xur");
            })).hasMessage("Expected select's option texts to be <[xur]> in any order, but were <[foo]>.");
        }

    }

    @Nested
    @DisplayName("hasOptionsWithValues(..) can be asserted")
    class HasOptionsWithValues {

        @Test
        @DisplayName("having options with matching values passes")
        void havingAllOptionsPasses() {
            assertThat(genericSelectWithValues("foo", "bar")).hasOptionsWithValues("foo", "bar");
        }

        @Test
        @DisplayName("having options with matching values in wrong order fails")
        void havingOptionsButInWrongOrderFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithValues("foo", "bar")).hasOptionsWithValues("bar", "foo");
            })).hasMessage("Expected select's option values to be <[bar, foo]>, but were <[foo, bar]>.");
        }

        @Test
        @DisplayName("having options with different values fails")
        void notHavingAllOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithValues("foo")).hasOptionsWithValues("xur");
            })).hasMessage("Expected select's option values to be <[xur]>, but were <[foo]>.");
        }

    }

    @Nested
    @DisplayName("hasOptionsWithValuesInAnyOrder(..) can be asserted")
    class HasOptionsWithValuesInAnyOrder {

        @Test
        @DisplayName("having options with matching values in correct order passes")
        void havingOptionsInCorrectOrderPasses() {
            GenericSelect select = genericSelectWithValues("foo", "bar");
            assertThat(select).hasOptionsWithValuesInAnyOrder("foo", "bar");
        }

        @Test
        @DisplayName("having options with matching values in wrong order passes")
        void havingOptionsInWrongOrderPasses() {
            GenericSelect select = genericSelectWithValues("foo", "bar");
            assertThat(select).hasOptionsWithValuesInAnyOrder("bar", "foo");
        }

        @Test
        @DisplayName("having options with different values fails")
        void notHavingAllOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithValues("foo")).hasOptionsWithValuesInAnyOrder("xur");
            })).hasMessage("Expected select's option values to be <[xur]> in any order, but were <[foo]>.");
        }

    }

    @Nested
    @DisplayName("hasOptions() can be asserted")
    class HasOptions {

        @Test
        @DisplayName("having at least one option passes")
        void havingOptionsPasses() {
            assertThat(genericSelectWithNumberOfOptions(1)).hasOptions();
        }

        @Test
        @DisplayName("having no options fails")
        void notHavingAnyOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithNumberOfOptions(0)).hasOptions();
            })).hasMessage("Expected select to have options, but it didn't.");
        }

    }

    @Nested
    @DisplayName("hasOptions(..) can be asserted")
    class HasExactNumberOfOptions {

        @Test
        @DisplayName("having matching number of options passes")
        void havingExactNumberOfOptionsPasses() {
            assertThat(genericSelectWithNumberOfOptions(5)).hasOptions(5);
        }

        @Test
        @DisplayName("having different number of options fails")
        void notHavingExactNumberOfOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithNumberOfOptions(5)).hasOptions(1);
            })).hasMessage("Expected select to have <1> options, but it had <5>.");
        }

    }

    @Nested
    @DisplayName("hasNoOptions() can be asserted")
    class HasNoOptions {

        @Test
        @DisplayName("having no options passes")
        void notHavingAnyOptionsPasses() {
            assertThat(genericSelectWithNumberOfOptions(0)).hasNoOptions();
        }

        @Test
        @DisplayName("having even one option fails")
        void havingOptionsFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericSelectWithNumberOfOptions(1)).hasNoOptions();
            })).hasMessage("Expected select to have no options, but it did have <1>.");
        }

    }

    @Nested
    @DisplayName("assertions provide fluent API")
    class AssertionProvidesFluentApi {

        @Test
        @DisplayName("hasOptionsWithTexts(..)")
        void hasOptionsWithTexts() {
            GenericSelectAssert original = assertThat(genericSelectWithTexts("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithTexts("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasOptionsWithTextsInAnyOrder(..)")
        void hasOptionsWithTextsInAnyOrder() {
            GenericSelectAssert original = assertThat(genericSelectWithTexts("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithTextsInAnyOrder("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasOptionsWithValues(..)")
        void hasOptionsWithValues() {
            GenericSelectAssert original = assertThat(genericSelectWithValues("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithValues("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasOptionsWithValuesInAnyOrder(..)")
        void hasOptionsWithValuesInAnyOrder() {
            GenericSelectAssert original = assertThat(genericSelectWithValues("foo", "bar"));
            GenericSelectAssert returned = original.hasOptionsWithValuesInAnyOrder("foo", "bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasOptions()")
        void hasOptions() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(5));
            GenericSelectAssert returned = original.hasOptions();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasOptions(..)")
        void hasOptionsWithNumber() {
            GenericSelectAssert original = assertThat(genericSelectWithNumberOfOptions(5));
            GenericSelectAssert returned = original.hasOptions(5);
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasNoOptions()")
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
