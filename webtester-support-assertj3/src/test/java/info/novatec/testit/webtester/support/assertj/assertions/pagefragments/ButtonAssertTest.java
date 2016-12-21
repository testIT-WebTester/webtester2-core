package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.Button;


public class ButtonAssertTest {

    @Nested
    class HasLabelAssertion {

        @Test
        void passesForMatchingLabels() {
            Button fooButton = buttonWithLabel("foo");
            assertThat(fooButton).hasLabel("foo");
        }

        @Test
        void failsForDifferentLabels() {
            Button barButton = buttonWithLabel("bar");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(barButton).hasLabel("foo");
            });
            assertThat(exception).hasMessage("Expected buttons's label to be <foo>, but was <bar>.");
        }

    }

    @Nested
    class HasNotLabelAssertion {

        @Test
        void passesForDifferentLabels() {
            Button barButton = buttonWithLabel("bar");
            assertThat(barButton).hasNotLabel("foo");
        }

        @Test
        void failsForMatchingLabels() {
            Button fooButton = buttonWithLabel("foo");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooButton).hasNotLabel("foo");
            });
            assertThat(exception).hasMessage("Expected buttons's label not to be <foo>, but it was.");
        }

    }

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void hasLabel() {
            ButtonAssert original = assertThat(buttonWithLabel("foo"));
            ButtonAssert returned = original.hasLabel("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNotLabel() {
            ButtonAssert original = assertThat(buttonWithLabel("bar"));
            ButtonAssert returned = original.hasNotLabel("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    Button buttonWithLabel(String label) {
        Button button = mock(Button.class);
        doReturn(label).when(button).getLabel();
        return button;
    }

}
