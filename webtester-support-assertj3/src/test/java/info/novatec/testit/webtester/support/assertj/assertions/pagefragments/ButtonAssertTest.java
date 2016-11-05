package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.Button;


public class ButtonAssertTest {

    @Nested
    @DisplayName("hasLabel(..) can be asserted")
    class HasLabelCanBeAsserted {

        @Test
        @DisplayName("having matching label passes")
        void sameLabel() {
            assertThat(buttonWithLabel("foo")).hasLabel("foo");
        }

        @Test
        @DisplayName("having a different label fails")
        void differentLabel() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(buttonWithLabel("bar")).hasLabel("foo");
            })).hasMessage("Expected buttons's label to be <foo>, but was <bar>.");
        }

    }

    @Nested
    @DisplayName("hasNotLabel(..) can be asserted")
    class HasNotLabelCanBeAsserted {

        @Test
        @DisplayName("having a different label passes")
        void differentLabel() {
            assertThat(buttonWithLabel("bar")).hasNotLabel("foo");
        }

        @Test
        @DisplayName("having matching label fails")
        void sameLabel() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(buttonWithLabel("foo")).hasNotLabel("foo");
            })).hasMessage("Expected buttons's label not to be <foo>, but it was.");
        }

    }

    @Nested
    @DisplayName("assertions provide fluent API")
    class AssertionProvidesFluentApi {

        @Test
        @DisplayName("hasLabel(..)")
        void hasLabel() {
            ButtonAssert original = assertThat(buttonWithLabel("foo"));
            ButtonAssert returned = original.hasLabel("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasNotLabel(..)")
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
