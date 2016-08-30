package info.novatec.testit.webtester.internal;

import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.adhoc.ByFinder;
import info.novatec.testit.webtester.adhoc.TypeFinder;
import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;
import info.novatec.testit.webtester.adhoc.AdHocFinder;


public interface OffersAdHocFinding {

    /**
     * Creates a new {@link AdHocFinder page fragment finder}.
     * This finder can be used to programmatically identify and create {@link PageFragment page fragments}.
     *
     * @return the newly create finder
     * @see AdHocFinder
     * @since 2.0
     */
    AdHocFinder finder();

    /**
     * Shorthand method for finding a {@link GenericElement generic page element} via a CSS-Selector expression.
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li><code>browser.find("#username").sendKeys("testuser");</code></li>
     * <li><code>browser.find("#button").click();</code></li>
     * <li><code>browser.find("#headline").getVisibleText();</code></li>
     * </ul>
     * <p>
     * Since this operation requires a {@link WebElement} to be found, it might throw a {@link NoSuchElementException} in
     * case there is no matching element.
     *
     * @param cssSelector the CSS-Selector expression to use
     * @return the generic element for the given selector
     * @throws NoSuchElementException in case the element could not be found
     * @see AdHocFinder
     * @see AdHocFinder#findGeneric()
     * @since 2.0
     */
    default GenericElement find(String cssSelector) {
        return finder().findGeneric().by(cssSelector);
    }

    /**
     * Shorthand method for finding a {@link Stream} of {@link GenericElement generic page elements} via a CSS-Selector
     * expression.
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li><code>browser.findMany(".button").filter(is(visible()));</code></li>
     * </ul>
     * <p>
     * The stream might be empty if no matching {@link WebElement WebElements} could be found.
     *
     * @param cssSelector the CSS-Selector expression to use
     * @return the list of generic elements for the given selector
     * @see AdHocFinder
     * @see AdHocFinder#findGeneric()
     * @since 2.0
     */
    default Stream<GenericElement> findMany(String cssSelector) {
        return finder().findGeneric().manyBy(cssSelector);
    }

    /**
     * Shorthand method for creating a new {@link ByFinder By based finder}.
     * <p>
     * Matching {@link By} instances can be created using the {@link ByProducers} utility class.
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li>
     * <code>browser.findBy(id("username")).as(TextField.class).setText("testuser");</code>
     * </li>
     * <li><code>browser.findBy(css("#button")).as(Button.class).click();</code>
     * </li>
     * <li>
     * <code>browser.findBy(xpath(".//h1")).asGeneric().getVisibleText();</code>
     * </li>
     * </ul>
     *
     * @param by the By to use when identifying an element
     * @return the new identification finder
     * @see AdHocFinder
     * @see AdHocFinder#findBy(By)
     * @since 2.0
     */
    default ByFinder findBy(By by) {
        return finder().findBy(by);
    }
    /**
     * Shorthand method for creating a new {@link TypeFinder type based finder}.
     * <p>
     * The given page object class is used for all subsequent operations on the finder.
     * <p>
     * <b>Examples:</b>
     * <ul>
     * <li>
     * <code>browser.find(TextField.class).by(id("username")).setText("testuser");</code>
     * </li>
     * <li><code>browser.find(Button.class).by(css("#button")).click();</code>
     * </li>
     * <li>
     * <code>browser.find(GenericElement.class).by(xpath(".//h1")).getVisibleText();</code>
     * </li>
     * </ul>
     *
     * @param <T> the type of the page fragment to create a finder for
     * @param fragmentClass the page fragment class to use when creating an element
     * @return the new type finder
     * @see AdHocFinder
     * @see AdHocFinder#find(Class)
     * @since 2.0
     */
    default <T extends PageFragment> TypeFinder<T> find(Class<T> fragmentClass) {
        return finder().find(fragmentClass);
    }

}
