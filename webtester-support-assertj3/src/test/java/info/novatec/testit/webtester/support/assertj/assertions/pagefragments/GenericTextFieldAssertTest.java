package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.EmailField;
import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.SearchField;
import info.novatec.testit.webtester.pagefragments.TelephoneField;
import info.novatec.testit.webtester.pagefragments.TextArea;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.UrlField;


public class GenericTextFieldAssertTest {

    @Nested
    class HasTextAssertion {

        @Test
        void passesForMatchingTexts() {
            GenericTextField fooTextField = genericTextField("foo");
            assertThat(fooTextField).hasText("foo");
        }

        @Test
        void failsForDifferentTexts() {
            GenericTextField fooTextField = genericTextField("foo");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooTextField).hasText("bar");
            });
            assertThat(exception).hasMessage("Expected text field fragment to have text <bar>, but was <foo>.");
        }

    }

    @Nested
    class HasNotTextAssertion {

        @Test
        void passedForDifferentTexts() {
            GenericTextField fooTextField = genericTextField("foo");
            assertThat(fooTextField).hasNotText("bar");
        }

        @Test
        void failsForMatchingTexts() {
            GenericTextField fooTextField = genericTextField("foo");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(fooTextField).hasNotText("foo");
            });
            assertThat(exception).hasMessage("Expected text field fragment not to have text <foo>, but it has.");
        }

    }

    @Nested
    class HasTextContainingAssertion {

        @Test
        void passesIfTextIsContained() {
            GenericTextField fooBarFooTextField = genericTextField("foo bar foo");
            assertThat(fooBarFooTextField).hasTextContaining("foo");
        }

        @Test
        void failsIfTextIsNotContained() {
            GenericTextField barBarTextField = genericTextField("bar bar");
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(barBarTextField).hasTextContaining("foo");
            });
            assertThat(exception).hasMessage("Expected text field's text to contain <foo>, but it didn't.");
        }

    }

    @Nested
    class AllKindsOfTextFieldsCanBeHandled {

        @Test
        void emailFiled() {
            EmailField emailField = mockWithText(EmailField.class, "foo");
            assertThat(emailField).hasText("foo");
        }

        @Test
        void passwordField() {
            PasswordField passwordField = mockWithText(PasswordField.class, "foo");
            assertThat(passwordField).hasText("foo");
        }

        @Test
        void searchField() {
            SearchField searchField = mockWithText(SearchField.class, "foo");
            assertThat(searchField).hasText("foo");
        }

        @Test
        void telephoneField() {
            TelephoneField telephoneField = mockWithText(TelephoneField.class, "foo");
            assertThat(telephoneField).hasText("foo");
        }

        @Test
        void textArea() {
            TextArea textArea = mockWithText(TextArea.class, "foo");
            assertThat(textArea).hasText("foo");
        }

        @Test
        void textField() {
            TextField textField = mockWithText(TextField.class, "foo");
            assertThat(textField).hasText("foo");
        }

        @Test
        void urlField() {
            UrlField urlField = mockWithText(UrlField.class, "foo");
            assertThat(urlField).hasText("foo");
        }

    }

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void hasText() {
            GenericTextFieldAssert original = assertThat(genericTextField("foo"));
            GenericTextFieldAssert returned = original.hasText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNotText() {
            GenericTextFieldAssert original = assertThat(genericTextField("bar"));
            GenericTextFieldAssert returned = original.hasNotText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasTextContaining() {
            GenericTextFieldAssert original = assertThat(genericTextField("foo bar foo"));
            GenericTextFieldAssert returned = original.hasTextContaining("foo");
            assertThat(returned).isSameAs(original);
        }

    }

    GenericTextField genericTextField(String text) {
        return mockWithText(GenericTextField.class, text);
    }

    <T extends GenericTextField> T mockWithText(Class<T> clazz, String text) {
        T textField = mock(clazz);
        doReturn(text).when(textField).getText();
        return textField;
    }

}
