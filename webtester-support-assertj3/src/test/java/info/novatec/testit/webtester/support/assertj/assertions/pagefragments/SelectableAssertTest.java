package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;


public class SelectableAssertTest {

    @Nested
    class IsSelectedAssertion {

        @Test
        void passesIfSelected() {
            Selectable selected = selectedSelectable();
            assertThat(selected).isSelected();
        }

        @Test
        void failsIfNotSelected() {
            Selectable notSelected = nonSelectedSelectable();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(notSelected).isSelected();
            });
            assertThat(exception).hasMessage("Expected selectable fragment to be selected, but it wasn't.");
        }

    }

    @Nested
    class IsNotSelectedAssertions {

        @Test
        void passesIfNotSelected() {
            Selectable notSelected = nonSelectedSelectable();
            assertThat(notSelected).isNotSelected();
        }

        @Test
        void failsIfSelected() {
            Selectable selected = selectedSelectable();
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(selected).isNotSelected();
            });
            assertThat(exception).hasMessage("Expected selectable fragment not to be selected, but it was.");
        }

    }

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void isSelected() {
            SelectableAssert original = assertThat(selectedSelectable());
            SelectableAssert returned = original.isSelected();
            assertThat(returned).isSameAs(original);
        }

        @Test
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
