package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import info.novatec.testit.webtester.pagefragments.EmailField;
import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.SearchField;
import info.novatec.testit.webtester.pagefragments.TelephoneField;
import info.novatec.testit.webtester.pagefragments.TextArea;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.UrlField;


@RunWith(Enclosed.class)
public class GenericTextFieldAssertTest {

    public static class HasText {

        @Test
        public void havingTextPasses() {
            assertThat(genericTextField("foo")).hasText("foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingTextFails() {
            assertThat(genericTextField("bar")).hasText("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericTextFieldAssert original = assertThat(genericTextField("foo"));
            GenericTextFieldAssert returned = original.hasText("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasNotText {

        @Test
        public void notHavingTextPasses() {
            assertThat(genericTextField("bar")).hasNotText("foo");
        }

        @Test(expected = AssertionError.class)
        public void havingTextFails() {
            assertThat(genericTextField("foo")).hasNotText("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericTextFieldAssert original = assertThat(genericTextField("bar"));
            GenericTextFieldAssert returned = original.hasNotText("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class HasTextContaining {

        @Test
        public void havingTextContainingPasses() {
            assertThat(genericTextField("foo bar foo")).hasTextContaining("foo");
        }

        @Test(expected = AssertionError.class)
        public void notHavingTextContainingFails() {
            assertThat(genericTextField("bar bar")).hasTextContaining("foo");
        }

        @Test
        public void invocationReturnsSameAssertionInstance() {
            GenericTextFieldAssert original = assertThat(genericTextField("foo bar foo"));
            GenericTextFieldAssert returned = original.hasTextContaining("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    public static class SubTypeCompatibility {

        @Test
        public void canHandleEmailFiled() {
            assertThat(mockWithText(EmailField.class, "foo")).hasText("foo");
        }

        @Test
        public void canHandlePasswordField() {
            assertThat(mockWithText(PasswordField.class, "foo")).hasText("foo");
        }

        @Test
        public void canHandleSearchField() {
            assertThat(mockWithText(SearchField.class, "foo")).hasText("foo");
        }

        @Test
        public void canHandleTelephoneField() {
            assertThat(mockWithText(TelephoneField.class, "foo")).hasText("foo");
        }

        @Test
        public void canHandleTextArea() {
            assertThat(mockWithText(TextArea.class, "foo")).hasText("foo");
        }

        @Test
        public void canHandleTextField() {
            assertThat(mockWithText(TextField.class, "foo")).hasText("foo");
        }

        @Test
        public void canHandleUrlField() {
            assertThat(mockWithText(UrlField.class, "foo")).hasText("foo");
        }

    }

    private static GenericTextField genericTextField(String text) {
        return mockWithText(GenericTextField.class, text);
    }

    private static <T extends GenericTextField> T mockWithText(Class<T> clazz, String text) {
        T textField = mock(clazz);
        doReturn(text).when(textField).getText();
        return textField;
    }

}
