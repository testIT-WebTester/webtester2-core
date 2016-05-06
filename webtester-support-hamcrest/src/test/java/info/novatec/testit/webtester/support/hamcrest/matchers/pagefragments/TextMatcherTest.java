package info.novatec.testit.webtester.support.hamcrest.matchers.pagefragments;

import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.has;
import static info.novatec.testit.webtester.support.hamcrest.WebTesterMatchers.text;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.mockito.Mockito;

import info.novatec.testit.webtester.pagefragments.GenericTextField;


public class TextMatcherTest {

    @Test
    public void equalTextMatches() {
        assertThat(fieldWithText("foo"), has(text("foo")));
    }

    @Test(expected = AssertionError.class)
    public void wrongTextDoesNotMatch() {
        assertThat(fieldWithText("foo"), has(text("bar")));
    }

    private GenericTextField<?> fieldWithText(String text) {
        GenericTextField<?> fragment = Mockito.mock(GenericTextField.class);
        doReturn(text).when(fragment).getText();
        return fragment;
    }

}
