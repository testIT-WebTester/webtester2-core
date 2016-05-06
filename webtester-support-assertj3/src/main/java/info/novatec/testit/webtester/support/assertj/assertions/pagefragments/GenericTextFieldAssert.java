package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import info.novatec.testit.webtester.pagefragments.EmailField;
import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.SearchField;
import info.novatec.testit.webtester.pagefragments.TelephoneField;
import info.novatec.testit.webtester.pagefragments.TextArea;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.UrlField;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;
import info.novatec.testit.webtester.support.assertj.WebTesterAssertions;


/**
 * Contains assertions for {@link Selectable selectables}.
 *
 * @see WebTesterAssertions
 * @see GenericTextField
 * @see EmailField
 * @see PasswordField
 * @see SearchField
 * @see TelephoneField
 * @see TextArea
 * @see TextField
 * @see UrlField
 * @since 2.0
 */
public class GenericTextFieldAssert extends AbstractPageFragmentAssert<GenericTextFieldAssert, GenericTextField> {

    public GenericTextFieldAssert(GenericTextField actual) {
        super(actual, GenericTextFieldAssert.class);
    }

    /**
     * Asserts that the {@link GenericTextField text field's} text is equal to the given text.
     *
     * @param text the expected text
     * @return same assertion instance for fluent API
     * @see GenericTextField#getText()
     * @since 2.0
     */
    public GenericTextFieldAssert hasText(String text) {
        String errorMessage = "Expected text field fragment to have text <%s>, but was <%s>.";
        String actualText = actual.getText();
        assertThat(actualText).overridingErrorMessage(errorMessage, text, actualText).isEqualTo(text);
        return this;
    }

    /**
     * Asserts that the {@link GenericTextField text field's} text is not equal to the given text.
     *
     * @param text the non expected text
     * @return same assertion instance for fluent API
     * @see GenericTextField#getText()
     * @since 2.0
     */
    public GenericTextFieldAssert hasNotText(String text) {
        String errorMessage = "Expected text field fragment not to have text <%s>, but it has.";
        String actualText = actual.getText();
        assertThat(actualText).overridingErrorMessage(errorMessage, text, actualText).isNotEqualTo(text);
        return this;
    }

    /**
     * Asserts that the {@link GenericTextField text field's} text contains a certain text fragment.
     *
     * @param textFragment the expected text fragment
     * @return same assertion instance for fluent API
     * @see GenericTextField#getText()
     * @since 2.0
     */
    public GenericTextFieldAssert hasTextContaining(String textFragment) {
        String errorMessage = "Expected text field's text to contain <%s>, but it didn't.";
        String actualText = actual.getText();
        assertThat(actualText).overridingErrorMessage(errorMessage, textFragment).contains(textFragment);
        return this;
    }

}
