package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.traits.Selectable;

@RunWith(Enclosed.class)
public class SelectableAssertTest {

    public static class IsSelected {

        @Test
        public void beingSelectedPasses() {
            assertThat(selectedSelectable()).isSelected();
        }

        @Test(expected = AssertionError.class)
        public void notBeingSelectedFails() {
            assertThat(nonSelectedSelectable()).isSelected();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            SelectableAssert original = assertThat(selectedSelectable());
            SelectableAssert returned = original.isSelected();
            assertThat(returned).isSameAs(original);
        }

    }

    public static class IsNotSelected {

        @Test
        public void notBeingSelectedPasses() {
            assertThat(nonSelectedSelectable()).isNotSelected();
        }

        @Test(expected = AssertionError.class)
        public void beingSelectedFails() {
            assertThat(selectedSelectable()).isNotSelected();
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            SelectableAssert original = assertThat(nonSelectedSelectable());
            SelectableAssert returned = original.isNotSelected();
            assertThat(returned).isSameAs(original);
        }

    }

    private static Selectable selectedSelectable() {
        return MockFactory.selectable().isSelected().build();
    }

    private static Selectable nonSelectedSelectable() {
        return MockFactory.selectable().isNotSelected().build();
    }

}
