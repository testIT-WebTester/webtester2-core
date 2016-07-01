package info.novatec.testit.webtester.pagefragments;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.adhoc.AdHocFinder;
import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.browser.operations.JavaScriptExecutor;
import info.novatec.testit.webtester.internal.OffersAdHocFinding;
import info.novatec.testit.webtester.internal.OffersBrowserGetter;
import info.novatec.testit.webtester.internal.OffersPageCreation;
import info.novatec.testit.webtester.internal.annotations.ReturnsName;
import info.novatec.testit.webtester.internal.annotations.ReturnsWebElement;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.annotations.Named;


/**
 * Base class for all WebTester page fragments.
 * <p>
 * A page fragment is, as the name suggests, part of a HTML page. It's an abstraction over Selenium's {@link WebElement} and
 * represents the content between an opening an closing tag within the page. This means a fragment can be a single element
 * (i.e. button, text field etc.) or include several other elements (i.e. a form, table etc.).
 * <p>
 * Provides the means to:
 * <ul>
 * <li>retrieve the {@link Browser browser} instance the page was created from</li>
 * <li>retrieve the {@link WebElement web element} instance it is wrapping</li>
 * <li>declaratively find child {@link PageFragment fragments} of this fragment</li>
 * <li>ad-hoc find child {@link PageFragment fragments} of this fragment</li>
 * </ul>
 * Child fragments of a page fragment are looked up within the page fragment's tags.
 * <p>
 * <b>Example Page Fragment:</b>
 * <pre>
 * public interface LoginForm extends PageFragment {
 *
 *     &#64;Wait(Until.VISIBLE)
 *     &#64;IdentifyUsing("#username")
 *     TextField username();
 *
 *     &#64;IdentifyUsing("#password")
 *     PasswordField password();
 *
 *     &#64;Named("Login Button")
 *     &#64;IdentifyUsing("#login")
 *     Button login();
 *
 *     &#64;PostConstruct
 *     default void assertPageIsDisplayed() {
 *         assertThat(username().isVisible).isTrue();
 *         assertThat(password().isVisible).isTrue();
 *         assertThat(login().isVisible).isTrue();
 *     }
 *
 * }
 * </pre>
 *
 * @see IdentifyUsing
 * @see WebElement
 * @see Browser
 * @see AdHocFinder
 */
public interface PageFragment extends OffersBrowserGetter, OffersAdHocFinding, OffersPageCreation {

    /**
     * Returns the underlying {@link WebElement} of this {@link PageFragment}.
     *
     * @return the web element
     * @see WebElement
     * @see PageFragment
     * @since 2.0
     */
    @ReturnsWebElement
    WebElement webElement();

    /**
     * Returns the optional name of this {@link PageFragment}.
     * <p>
     * Names can be set manually by using the {@link Named @Named} annotation on an identification method.
     * If no name is set a default name is tried to be generated. That name consists of the class of the fragment and it's
     * identification information. There are some cases where no name can be generated (i.e. lists / streams of fragments).
     *
     * @return the optional name of the fragment
     * @see Named
     * @see PageFragment
     * @since 2.0
     */
    @ReturnsName
    Optional<String> getName();

    /**
     * Returns the tag name of this {@link PageFragment}.
     *
     * @return the tag name
     * @see WebElement#getTagName()
     * @see PageFragment
     * @since 2.0
     */
    default String getTagName() {
        return webElement().getTagName();
    }

    /**
     * Returns the visible (displayed) textual representation of this {@link PageFragment}.
     * For invisible fragments this returns an empty string.
     *
     * @return the visible text
     * @see WebElement#getText()
     * @see PageFragment
     * @since 2.0
     */
    @Mark(As.READ)
    default String getVisibleText() {
        return StringUtils.defaultString(webElement().getText());
    }

    /**
     * Gets the optional value of the given attribute as a string.
     * If the attribute does not exist, an empty optional is returned.
     * Might include optional with empty string indicating the presence of the attribute but without a value.
     *
     * @param attributeName the name of the attribute to read
     * @return the optional value of the attribute
     * @see WebElement#getAttribute(String)
     * @see PageFragment
     * @since 2.0
     */
    default Optional<String> getAttribute(String attributeName) {
        return Optional.ofNullable(webElement().getAttribute(attributeName));
    }

    /**
     * Sets the value of an attribute of this {@link PageFragment} using JavaScript.
     * <p>
     * <b>Example:</b>
     * <pre>
     * // will change the value of a text field to 'foo'
     * textField.setAttribute("value", "foo");
     * </pre>
     *
     * @param attributeName the name of the attribute to set
     * @param value the value to set the attribute to
     * @see PageFragment
     * @see JavaScriptExecutor
     * @since 2.0
     */
    default void setAttribute(String attributeName, String value) {
        String escapedValue = StringUtils.replace(value, "\"", "\\\"");
        String script = "arguments[0]." + attributeName + " = \"" + escapedValue + "\"";
        browser().javaScript().execute(script, this, value);
    }

    /**
     * Gets the optional value of the given CSS property as a string.
     * If the CSS property is not set, an empty optional is returned.
     *
     * @param cssProperty the name of the CSS property to read
     * @return the optional value of the property
     * @see WebElement#getCssValue(String)
     * @see PageFragment
     * @since 2.0
     */
    default Optional<String> getCssValue(String cssProperty) {
        return Optional.ofNullable(webElement().getCssValue(cssProperty)).filter(StringUtils::isNotBlank);
    }

    /**
     * Returns whether or not this {@link PageFragment} is visible on the page.
     * For a fragment to be visible it has to exist within the page's DOM and be not hidden by CSS.
     * In case the fragment is not part of the DOM, an exception is thrown!
     *
     * @return true if the fragment is visible
     * @throws NoSuchElementException in case the element does not exist within the DOM
     * @see WebElement#isDisplayed()
     * @see PageFragment
     * @since 2.0
     */
    default boolean isVisible() throws NoSuchElementException {
        return webElement().isDisplayed();
    }

    /**
     * Returns whether or not this {@link PageFragment} is invisible but part of the page.
     * For a fragment to be invisible it has to exist within the page's DOM and be hidden by CSS.
     * In case the fragment is not part of the DOM, an exception is thrown!
     *
     * @return true if the fragment is invisible
     * @throws NoSuchElementException in case the element does not exist within the DOM
     * @see #isVisible()
     * @see WebElement#isDisplayed()
     * @see PageFragment
     * @since 2.0
     */
    default boolean isInvisible() throws NoSuchElementException {
        return !isVisible();
    }

    /**
     * Returns whether or not this {@link PageFragment} is part of the page's DOM.
     * This can be used to avoid catching the {@link NoSuchElementException} in order to check if a dynamic element exists.
     *
     * @return true if the fragment is part of the page's DOM
     * @see WebElement
     * @see PageFragment
     * @since 2.0
     */
    default boolean isPresent() {
        try {
            webElement();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Returns whether or not this {@link PageFragment} is NOT part of the page's DOM.
     * This can be used to avoid catching the {@link NoSuchElementException} in order to check if a dynamic element does no
     * exist.
     *
     * @return true if the fragment is NOT part of the page's DOM
     * @see #isPresent()
     * @see WebElement
     * @see PageFragment
     * @since 2.0
     */
    default boolean isNotPresent() {
        return !isPresent();
    }

    /**
     * Returns whether or not this {@link PageFragment} is enabled.
     * <p>
     * <b>Note:</b> Ths is will generally return true for everything but disabled <code>&lt;input&gt;</code> elements.
     *
     * @return true if the fragment is enabled
     * @see WebElement#isEnabled()
     * @see PageFragment
     * @since 2.0
     */
    default boolean isEnabled() {
        return webElement().isEnabled();
    }

    /**
     * Returns whether or not this {@link PageFragment} is disabled.
     * <p>
     * <b>Note:</b> Ths is will generally return false for everything but disabled <code>&lt;input&gt;</code> elements.
     *
     * @return true if the fragment is disabled
     * @see #isEnabled()
     * @see WebElement#isEnabled()
     * @see PageFragment
     * @since 2.0
     */
    default boolean isDisabled() {
        return !isEnabled();
    }

    /* ad-hoc page fragment finding */

    @Override
    default AdHocFinder finder() {
        return new AdHocFinder(this);
    }

}
