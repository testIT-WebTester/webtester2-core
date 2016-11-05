package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class SelectableAssertTest {

    @Nested
    @DisplayName("isSelected() can be asserted")
    class IsSelected {

        @Test
        @DisplayName("being selected passes")
        void beingSelectedPasses() {
            assertThat(selectedSelectable()).isSelected();
        }

        @Test
        @DisplayName("not being selected fails")
        void notBeingSelectedFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(nonSelectedSelectable()).isSelected();
            })).hasMessage("Expected selectable fragment to be selected, but it wasn't.");
        }

    }

    @Nested
    @DisplayName("isNotSelected() can be asserted")
    class IsNotSelected {

        @Test
        @DisplayName("not being selected passes")
        void notBeingSelectedPasses() {
            assertThat(nonSelectedSelectable()).isNotSelected();
        }

        @Test
        @DisplayName("being selected fails")
        void beingSelectedFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(selectedSelectable()).isNotSelected();
            })).hasMessage("Expected selectable fragment not to be selected, but it was.");
        }

    }

    @Nested
    @DisplayName("assertions provide fluent API")
    class AssertionProvidesFluentApi {

        @Test
        @DisplayName("isSelected()")
        void isSelected() {
            SelectableAssert original = assertThat(selectedSelectable());
            SelectableAssert returned = original.isSelected();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("isNotSelected()")
        void isNotSelected() {
            SelectableAssert original = assertThat(nonSelectedSelectable());
            SelectableAssert returned = original.isNotSelected();
            assertThat(returned).isSameAs(original);
        }

    }

    Selectable selectedSelectable() {
        return MockFactory.selectable().isSelected().build();
    }

    Selectable nonSelectedSelectable() {
        return MockFactory.selectable().isNotSelected().build();
    }

}
