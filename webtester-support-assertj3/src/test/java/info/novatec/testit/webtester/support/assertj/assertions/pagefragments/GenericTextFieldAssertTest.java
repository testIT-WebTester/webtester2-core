package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.EmailField;
import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.SearchField;
import info.novatec.testit.webtester.pagefragments.TelephoneField;
import info.novatec.testit.webtester.pagefragments.TextArea;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.UrlField;


public class GenericTextFieldAssertTest {

    @Test
    public void hasTextPositive() {
        assertThat(genericTextField("foo")).hasText("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasTextNegative() {
        assertThat(genericTextField("bar")).hasText("foo");
    }

    @Test
    public void hasNotTextPositive() {
        assertThat(genericTextField("bar")).hasNotText("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasNotTextNegative() {
        assertThat(genericTextField("foo")).hasNotText("foo");
    }

    @Test
    public void hasTextContainingPositive() {
        assertThat(genericTextField("foo bar foo")).hasTextContaining("foo");
    }

    @Test(expected = AssertionError.class)
    public void hasTextContainingNegative() {
        assertThat(genericTextField("bar bar")).hasTextContaining("foo");
    }

    @Test
    public void allSubTypesAreAccepted() {
        assertThat(mockWithText(EmailField.class, "foo")).hasText("foo");
        assertThat(mockWithText(PasswordField.class, "foo")).hasText("foo");
        assertThat(mockWithText(SearchField.class, "foo")).hasText("foo");
        assertThat(mockWithText(TelephoneField.class, "foo")).hasText("foo");
        assertThat(mockWithText(TextArea.class, "foo")).hasText("foo");
        assertThat(mockWithText(TextField.class, "foo")).hasText("foo");
        assertThat(mockWithText(UrlField.class, "foo")).hasText("foo");
    }

    private GenericTextField genericTextField(String text) {
        return mockWithText(GenericTextField.class, text);
    }

    private <T extends GenericTextField> T mockWithText(Class<T> clazz, String text) {
        T textField = mock(clazz);
        doReturn(text).when(textField).getText();
        return textField;
    }

}
