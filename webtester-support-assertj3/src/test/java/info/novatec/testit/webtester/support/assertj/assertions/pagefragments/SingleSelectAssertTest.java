package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static utils.MockFactory.singleSelect;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SingleSelectAssertTest {

    @Nested
    @DisplayName("hasSelectionWithText(..) can be asserted")
    class HasSelectionWithText {

        @Test
        @DisplayName("having selection with matching text passes")
        void havingSelectionWithTextPasses() {
            SingleSelect select = singleSelect().withSelectedText("foo").build();
            assertThat(select).hasSelectionWithText("foo");
        }

        @Test
        @DisplayName("having selection with different text fails")
        void notHavingSelectionWithTextFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                SingleSelect select = singleSelect().withSelectedText("foo").build();
                assertThat(select).hasSelectionWithText("bar");
            })).hasMessage("Expected select's selection text to be <bar>, but was <foo>.");
        }

    }

    @Nested
    @DisplayName("hasSelectionWithValue(..) can be asserted")
    class HasSelectionWithValue {

        @Test
        @DisplayName("having selection with matching value passes")
        void havingSelectionWithValuePasses() {
            SingleSelect select = singleSelect().withSelectedValue("v1").build();
            assertThat(select).hasSelectionWithValue("v1");
        }

        @Test
        @DisplayName("having selection with different value fails")
        void notHavingSelectionWithValueFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                SingleSelect select = singleSelect().withSelectedValue("v1").build();
                assertThat(select).hasSelectionWithValue("v2");
            })).hasMessage("Expected select's selection value to be <v2>, but was <v1>.");
        }

    }

    @Nested
    @DisplayName("hasSelectionWithIndex(..) can be asserted")
    class HasSelectionWithIndex {

        @Test
        @DisplayName("having selection with matching index passes")
        void havingSelectionWithIndexPasses() {
            SingleSelect select = singleSelect().withSelectedIndex(1).build();
            assertThat(select).hasSelectionWithIndex(1);
        }

        @Test
        @DisplayName("having selection with different index fails")
        void notHavingSelectionWithIndexFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                SingleSelect select = singleSelect().withSelectedIndex(1).build();
                assertThat(select).hasSelectionWithIndex(2);
            })).hasMessage("Expected select's selection index to be <2>, but was <1>.");
        }

    }

    @Nested
    @DisplayName("assertions provide fluent API")
    class AssertionProvidesFluentApi {

        @Test
        @DisplayName("withSelectedText(..)")
        void withSelectedText() {
            SingleSelect select = singleSelect().withSelectedText("foo").build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("withSelectedValue(..)")
        void withSelectedValue() {
            SingleSelect select = singleSelect().withSelectedValue("v1").build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithValue("v1");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("withSelectedIndex(..)")
        void withSelectedIndex() {
            SingleSelect select = singleSelect().withSelectedIndex(1).build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithIndex(1);
            assertThat(returned).isSameAs(original);
        }

    }

}
