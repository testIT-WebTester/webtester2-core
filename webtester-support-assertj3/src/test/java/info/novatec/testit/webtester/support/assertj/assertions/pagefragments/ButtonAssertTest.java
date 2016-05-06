package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.pagefragments.Button;


@RunWith(MockitoJUnitRunner.class)
public class ButtonAssertTest {

    @Test
    public void hasLabelPositive() {
        assertThat(buttonWithLabel("foo")).hasLabel("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasLabelNegative() {
        assertThat(buttonWithLabel("bar")).hasLabel("foo");
    }

    @Test
    public void notHasLabelPositive() {
        assertThat(buttonWithLabel("bar")).hasNotLabel("foo");
    }

    @Test(expected = AssertionError.class)
    public void notHasLabelNegative() {
        assertThat(buttonWithLabel("foo")).hasNotLabel("foo");
    }

    private Button buttonWithLabel(String label) {
        Button button = mock(Button.class);
        doReturn(label).when(button).getLabel();
        return button;
    }

}
