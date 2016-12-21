package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.support.assertj.assertions.AbstractWebTesterAssert;


/**
 * Abstract base class for all of WebTester's {@link PageFragment page fragment} related AssertJ assertion classes.
 *
 * @param <A> the "self" type of this assertion class. Please read
 * <a href="http://bit.ly/anMa4g" target="_blank">
 * Emulating 'self types' using Java Generics to simplify fluent API implementation
 * </a>
 * for more details.
 * @param <B> the type of the "actual" value.
 * @since 2.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractPageFragmentAssert<A extends AbstractPageFragmentAssert, B extends PageFragment>
    extends AbstractWebTesterAssert<A, B> {

    protected AbstractPageFragmentAssert(B actual, Class<A> selfType) {
        super(actual, selfType);
    }

    /* tag name */

    /**
     * Asserts that the {@link PageFragment page fragment's} tag is equal to the given tag.
     *
     * @param tagName the tag name
     * @return same assertion instance for fluent API
     * @see PageFragment#getTagName()
     * @since 2.0
     */
    public A hasTag(String tagName) {
        String actualTagName = this.actual.getTagName();
        String errorMessage = "Expected page fragment to have tag <%s>, but was <%s>.";
        assertThat(actualTagName).overridingErrorMessage(errorMessage, tagName, actualTagName).isEqualTo(tagName);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment's} tag is not equal to the given tag.
     *
     * @param tagName the tag name
     * @return same assertion instance for fluent API
     * @see PageFragment#getTagName()
     * @since 2.0
     */
    public A hasNotTag(String tagName) {
        String actualTagName = this.actual.getTagName();
        String errorMessage = "Expected page fragment to not to have tag <%s>, but it did.";
        assertThat(actualTagName).overridingErrorMessage(errorMessage, tagName).isNotEqualTo(tagName);
        return ( A ) this;
    }

    /* visible text */

    /**
     * Asserts that the {@link PageFragment page fragment's} visible text is equal to the given text.
     *
     * @param text the expected text
     * @return same assertion instance for fluent API
     * @see PageFragment#getVisibleText()
     * @since 2.0
     */
    public A hasVisibleText(String text) {
        String actualText = this.actual.getVisibleText();
        String errorMessage = "Expected page fragment's visible text to be <%s>, but was <%s>.";
        assertThat(actualText).overridingErrorMessage(errorMessage, text, actualText).isEqualTo(text);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment's} visible text is not equal to the given text.
     *
     * @param text the non expected text
     * @return same assertion instance for fluent API
     * @see PageFragment#getVisibleText()
     * @since 2.0
     */
    public A hasNotVisibleText(String text) {
        String actualText = this.actual.getVisibleText();
        String errorMessage = "Expected page fragment's visible text not to be <%s>, but it was.";
        assertThat(actualText).overridingErrorMessage(errorMessage, text).isNotEqualTo(text);
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment's} visible text contains the given text fragment.
     *
     * @param textFragment the expected text fragment
     * @return same assertion instance for fluent API
     * @see PageFragment#getVisibleText()
     * @since 2.0
     */
    public A hasVisibleTextContaining(String textFragment) {
        String actualText = this.actual.getVisibleText();
        String errorMessage = "Expected page fragment's visible text to contain <%s>, but it didn't.";
        assertThat(actualText).overridingErrorMessage(errorMessage, textFragment).contains(textFragment);
        return ( A ) this;
    }

    /* attribute values */

    /**
     * Asserts that the {@link PageFragment page fragment} has a certain attribute.
     *
     * @param attributeName the attribute's name
     * @return same assertion instance for fluent API
     * @see PageFragment#getAttribute(String)
     * @since 2.0
     */
    public A hasAttribute(String attributeName) {
        Optional<String> actualValue = actual.getAttribute(attributeName);
        String errorMessage = "Expected page fragment to have attribute <%s>, but it didn't.";
        assertThat(actualValue).overridingErrorMessage(errorMessage, attributeName).isPresent();
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment} doesn't have a certain attribute.
     *
     * @param attributeName the attribute's name
     * @return same assertion instance for fluent API
     * @see PageFragment#getAttribute(String)
     * @since 2.0
     */
    public A hasNotAttribute(String attributeName) {
        Optional<String> actualValue = actual.getAttribute(attributeName);
        String errorMessage = "Expected page fragment not to have attribute <%s>, but it did.";
        assertThat(actualValue).overridingErrorMessage(errorMessage, attributeName).isEqualTo(Optional.empty());
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment} has a certain attribute with a specific value.
     *
     * @param attributeName the attribute's name
     * @param value the expected value of the attribute
     * @return same assertion instance for fluent API
     * @see PageFragment#getAttribute(String)
     * @since 2.0
     */
    public A hasAttributeValue(String attributeName, String value) {
        hasAttribute(attributeName);
        String actualValue = actual.getAttribute(attributeName).orElse(null);
        String errorMessage = "Expected page fragment's <%s> attribute value to be <%s>, but it was <%s>.";
        assertThat(actualValue).overridingErrorMessage(errorMessage, attributeName, value, actualValue).isEqualTo(value);
        return ( A ) this;
    }

    /* visibility */

    /**
     * Asserts that the {@link PageFragment page fragment} is visible.
     *
     * @return same assertion instance for fluent API
     * @see PageFragment#isVisible()
     * @since 2.0
     */
    public A isVisible() {
        String errorMessage = "Expected page fragment to be visible, but it wasn't.";
        assertThat(actual.isVisible()).overridingErrorMessage(errorMessage).isTrue();
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment} is invisible.
     *
     * @return same assertion instance for fluent API
     * @see PageFragment#isInvisible()
     * @since 2.0
     */
    public A isInvisible() {
        String errorMessage = "Expected page fragment to be invisible, but it wasn't.";
        assertThat(actual.isInvisible()).overridingErrorMessage(errorMessage).isTrue();
        return ( A ) this;
    }

    /* presence */

    /**
     * Asserts that the {@link PageFragment page fragment} is present.
     *
     * @return same assertion instance for fluent API
     * @see PageFragment#isPresent()
     * @since 2.0
     */
    public A isPresent() {
        String errorMessage = "Expected page fragment to be present, but it wasn't.";
        assertThat(actual.isPresent()).overridingErrorMessage(errorMessage).isTrue();
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment} isn't present.
     *
     * @return same assertion instance for fluent API
     * @see PageFragment#isNotPresent()
     * @since 2.0
     */
    public A isNotPresent() {
        String errorMessage = "Expected page fragment not to be present, but it was.";
        assertThat(actual.isNotPresent()).overridingErrorMessage(errorMessage).isTrue();
        return ( A ) this;
    }

    /* enabled / disabled */

    /**
     * Asserts that the {@link PageFragment page fragment} is enabled.
     *
     * @return same assertion instance for fluent API
     * @see PageFragment#isEnabled()
     * @since 2.0
     */
    public A isEnabled() {
        String errorMessage = "Expected page fragment to be enabled, but it wasn't.";
        assertThat(actual.isEnabled()).overridingErrorMessage(errorMessage).isTrue();
        return ( A ) this;
    }

    /**
     * Asserts that the {@link PageFragment page fragment} is disabled.
     *
     * @return same assertion instance for fluent API
     * @see PageFragment#isDisabled()
     * @since 2.0
     */
    public A isDisabled() {
        String errorMessage = "Expected page fragment to be disabled, but it wasn't.";
        assertThat(actual.isDisabled()).overridingErrorMessage(errorMessage).isTrue();
        return ( A ) this;
    }

}
