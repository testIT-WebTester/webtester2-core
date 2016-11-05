package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("hasText(..) can be asserted")
    class HasText {

        @Test
        @DisplayName("having matching text passes")
        void havingTextPasses() {
            assertThat(genericTextField("foo")).hasText("foo");
        }

        @Test
        @DisplayName("having different text fails")
        void notHavingTextFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericTextField("bar")).hasText("foo");
            })).hasMessage("Expected text field fragment to have text <foo>, but was <bar>.");
        }

    }

    @Nested
    @DisplayName("hasNotText(..) can be asserted")
    class HasNotText {

        @Test
        @DisplayName("having different text passes")
        void notHavingTextPasses() {
            assertThat(genericTextField("bar")).hasNotText("foo");
        }

        @Test
        @DisplayName("having matching text fails")
        void havingTextFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericTextField("foo")).hasNotText("foo");
            })).hasMessage("Expected text field fragment not to have text <foo>, but it has.");
        }

    }

    @Nested
    @DisplayName("hasTextContaining(..) can be asserted")
    class HasTextContaining {

        @Test
        @DisplayName("having text containing the part passes")
        void havingTextContainingPasses() {
            assertThat(genericTextField("foo bar foo")).hasTextContaining("foo");
        }

        @Test
        @DisplayName("having text that does not contain the part fails")
        void notHavingTextContainingFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(genericTextField("bar bar")).hasTextContaining("foo");
            })).hasMessage("Expected text field's text to contain <foo>, but it didn't.");
        }

    }

    @Nested
    @DisplayName("sub-classes of GenericTextField can be handled")
    class SubTypeCompatibility {

        @Test
        @DisplayName("EmailField")
        void canHandleEmailFiled() {
            assertThat(mockWithText(EmailField.class, "foo")).hasText("foo");
        }

        @Test
        @DisplayName("PasswordField")
        void canHandlePasswordField() {
            assertThat(mockWithText(PasswordField.class, "foo")).hasText("foo");
        }

        @Test
        @DisplayName("SearchField")
        void canHandleSearchField() {
            assertThat(mockWithText(SearchField.class, "foo")).hasText("foo");
        }

        @Test
        @DisplayName("TelephoneField")
        void canHandleTelephoneField() {
            assertThat(mockWithText(TelephoneField.class, "foo")).hasText("foo");
        }

        @Test
        @DisplayName("TextArea")
        void canHandleTextArea() {
            assertThat(mockWithText(TextArea.class, "foo")).hasText("foo");
        }

        @Test
        @DisplayName("TextField")
        void canHandleTextField() {
            assertThat(mockWithText(TextField.class, "foo")).hasText("foo");
        }

        @Test
        @DisplayName("UrlField")
        void canHandleUrlField() {
            assertThat(mockWithText(UrlField.class, "foo")).hasText("foo");
        }

    }

    @Nested
    @DisplayName("assertions provide fluent API")
    class AssertionProvidesFluentApi {

        @Test
        @DisplayName("hasText(..)")
        void hasText() {
            GenericTextFieldAssert original = assertThat(genericTextField("foo"));
            GenericTextFieldAssert returned = original.hasText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasNotText(..)")
        void hasNotText() {
            GenericTextFieldAssert original = assertThat(genericTextField("bar"));
            GenericTextFieldAssert returned = original.hasNotText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasTextContaining(..)")
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
