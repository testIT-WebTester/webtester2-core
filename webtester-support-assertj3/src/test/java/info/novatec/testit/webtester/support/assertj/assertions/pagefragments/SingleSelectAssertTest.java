package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static utils.unit.MockFactory.singleSelect;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.SingleSelect;


public class SingleSelectAssertTest {

    @Nested
    class HasSelectionWithTextAssertion {

        @Test
        void passesForMatchingSelection() {
            SingleSelect fooSelect = singleSelect().withSelectedText("foo").build();
            assertThat(fooSelect).hasSelectionWithText("foo");
        }

        @Test
        void failsForDifferentSelection() {
            SingleSelect fooSelect = singleSelect().withSelectedText("foo").build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasSelectionWithText("bar");
            });
            assertThat(exception).hasMessage("Expected select's selection text to be <bar>, but was <foo>.");
        }

    }

    @Nested
    class HasSelectionWithValueAssertion {

        @Test
        void passesForMatchingSelection() {
            SingleSelect fooSelect = singleSelect().withSelectedValue("foo").build();
            assertThat(fooSelect).hasSelectionWithValue("foo");
        }

        @Test
        void failsForDifferentSelection() {
            SingleSelect fooSelect = singleSelect().withSelectedValue("foo").build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooSelect).hasSelectionWithValue("bar");
            });
            assertThat(exception).hasMessage("Expected select's selection value to be <bar>, but was <foo>.");
        }

    }

    @Nested
    class HasSelectionWithIndexAssertion {

        @Test
        void passesForMatchingSelection() {
            SingleSelect indexOneSelect = singleSelect().withSelectedIndex(1).build();
            assertThat(indexOneSelect).hasSelectionWithIndex(1);
        }

        @Test
        void failsForDifferentSelection() {
            SingleSelect indexOneSelect = singleSelect().withSelectedIndex(1).build();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(indexOneSelect).hasSelectionWithIndex(2);
            });
            assertThat(exception).hasMessage("Expected select's selection index to be <2>, but was <1>.");
        }

    }

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void withSelectedText() {
            SingleSelect select = singleSelect().withSelectedText("foo").build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void withSelectedValue() {
            SingleSelect select = singleSelect().withSelectedValue("v1").build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithValue("v1");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void withSelectedIndex() {
            SingleSelect select = singleSelect().withSelectedIndex(1).build();
            SingleSelectAssert original = assertThat(select);
            SingleSelectAssert returned = original.hasSelectionWithIndex(1);
            assertThat(returned).isSameAs(original);
        }

    }

}
