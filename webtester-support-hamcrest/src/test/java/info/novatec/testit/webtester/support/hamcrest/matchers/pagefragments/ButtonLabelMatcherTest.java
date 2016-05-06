package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.label;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.mockito.Mockito;

import info.novatec.testit.webtester.pagefragments.Button;


public class ButtonLabelMatcherTest {

    @Test
    public void equalLabelMatches() {
        assertThat(buttonWithLabel("foo"), has(label("foo")));
    }

    @Test(expected = AssertionError.class)
    public void wrongLabelDoesNotMatch() {
        assertThat(buttonWithLabel("foo"), has(label("bar")));
    }

    private Button buttonWithLabel(String label) {
        Button fragment = Mockito.mock(Button.class);
        doReturn(label).when(fragment).getLabel();
        return fragment;
    }

}
