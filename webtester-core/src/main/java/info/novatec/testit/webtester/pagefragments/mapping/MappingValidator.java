package info.novatec.testit.webtester.pagefragments.mapping;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;


/**
 * Validates the mapping information of a {@link PageFragment} class against given {@link WebElement web elements}.
 *
 * @see WebElement
 * @see Mapping
 * @see Mappings
 * @see Validator
 * @since 2.0
 */
public interface MappingValidator {

    /**
     * Asserts that the given {@link WebElement web elements} conforms to at least one of the valid {@link Mapping} of this
     * {@link MappingValidator} instance.
     *
     * @param webElement the web element to check
     * @return the same web element for use in fluent APIs
     * @throws MappingException in case the web element does not conform to any valid mapping
     * @see MappingValidator
     * @see WebElement
     * @see Mapping
     * @see Mappings
     * @see Validator
     * @since 2.0
     */
    WebElement assertValidity(WebElement webElement) throws MappingException;

}
