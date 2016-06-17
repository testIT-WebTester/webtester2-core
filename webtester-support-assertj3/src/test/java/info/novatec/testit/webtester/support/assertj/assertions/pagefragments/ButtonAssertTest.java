package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import info.novatec.testit.webtester.pagefragments.Button;


@RunWith(Enclosed.class)
public class ButtonAssertTest {

    public static class HasLabel {

        @Test
        public void havingLabelPasses() {
            assertThat(buttonWithLabel("foo")).hasLabel("foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingLabelFails() {
            assertThat(buttonWithLabel("bar")).hasLabel("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            ButtonAssert original = assertThat(buttonWithLabel("foo"));
            ButtonAssert returned = original.hasLabel("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class NotHasLabel {

        @Test
        public void notHavingLabelPasses() {
            assertThat(buttonWithLabel("bar")).hasNotLabel("foo");
        }

        @Test(expected = AssertionError.class)
        public void havingLabelFails() {
            assertThat(buttonWithLabel("foo")).hasNotLabel("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            ButtonAssert original = assertThat(buttonWithLabel("bar"));
            ButtonAssert returned = original.hasNotLabel("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    private static Button buttonWithLabel(String label) {
        Button button = mock(Button.class);
        doReturn(label).when(button).getLabel();
        return button;
    }

}
