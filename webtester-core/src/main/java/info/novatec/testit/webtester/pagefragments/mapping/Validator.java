package info.novatec.testit.webtester.pagefragments.mapping;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;


/**
 * Constellations are used to validate {@link WebElement web elements} for {@link PageFragment page fragments}. They provide
 * a way to implement more complex validation logic then the {@link Mapping @Mapping} annotation con provide on it's own.
 *
 * @see Mapping
 * @see Mappings
 * @see WebElement
 * @see PageFragment
 * @since 2.0
 */
public interface Validator {

    /**
     * Validates if the given {@link WebElement web element} is valid for being used as a specific {@link PageFragment page
     * fragment}.
     *
     * @param webElement the web element to validate
     * @return true if the web element is valid, otherwise false
     * @see WebElement
     * @since 2.0
     */
    boolean isValid(WebElement webElement);

    /**
     * Returns a textual description of this validator. It is used in case all validators of a page fragment fail to describe
     * what valida the constellations are.
     *
     * @return a textual description of this validator
     * @since 2.0
     */
    String describe();

}
