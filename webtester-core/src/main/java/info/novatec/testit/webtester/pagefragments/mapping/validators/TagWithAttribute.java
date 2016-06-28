package info.novatec.testit.webtester.pagefragments.mapping.validators;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;
import info.novatec.testit.webtester.pagefragments.mapping.DefaultMappingValidator;
import info.novatec.testit.webtester.pagefragments.mapping.Validator;


/**
 * This {@link Validator} validates a {@link WebElement web element's} tag and the existence of a given attribute.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;Mapping(tag="select", attribute="multiple")
 * public interface MultiSelect extends PageFragment {...}
 * </pre>
 *
 * @see Validator
 * @see Mapping
 * @see Mappings
 * @see DefaultMappingValidator
 * @since 2.0
 */
public class TagWithAttribute extends JustTag {

    public static final String DESCRIPTION_FORMAT = "%s and an attribute '%s' present";

    private final String attributeName;

    public TagWithAttribute(String tagName, String attributeName) {
        super(tagName);
        this.attributeName = attributeName;
    }

    @Override
    public boolean isValid(WebElement webElement) {
        String attributeValue = webElement.getAttribute(attributeName);
        return super.isValid(webElement) && attributeValue != null;
    }

    @Override
    public String describe() {
        return String.format(DESCRIPTION_FORMAT, super.describe(), attributeName);
    }

}
