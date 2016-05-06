package info.novatec.testit.webtester.pagefragments.mapping.validators;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;
import info.novatec.testit.webtester.pagefragments.mapping.MappingValidatorImpl;
import info.novatec.testit.webtester.pagefragments.mapping.Validator;


/**
 * This {@link Validator} validates a {@link WebElement web element's} tag and the non-existence of a given attribute.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;Mapping(tag="select", attribute="!multiple")
 * public interface SingleSelect extends PageFragment {...}
 * </pre>
 *
 * @see Validator
 * @see Mapping
 * @see Mappings
 * @see MappingValidatorImpl
 * @since 2.0
 */
public class TagWithoutAttribute extends JustTag {

    public static final String DESCRIPTION_FORMAT = "%s and an attribute '%s' not being present";

    private final String attributeName;

    public TagWithoutAttribute(String tagName, String attributeName) {
        super(tagName);
        this.attributeName = attributeName;
    }

    @Override
    public boolean isValid(WebElement webElement) {
        String attributeValue = webElement.getAttribute(attributeName);
        return super.isValid(webElement) && attributeValue == null;
    }

    @Override
    public String describe() {
        return String.format(DESCRIPTION_FORMAT, super.describe(), attributeName);
    }

}
