package info.novatec.testit.webtester.pagefragments.mapping.validators;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;
import info.novatec.testit.webtester.pagefragments.mapping.DefaultMappingValidator;
import info.novatec.testit.webtester.pagefragments.mapping.Validator;


/**
 * This {@link Validator} validates a {@link WebElement web element's} tag and nothing else.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;Mapping(tag="div")
 * public interface Div extends PageFragment {...}
 * </pre>
 *
 * @see Validator
 * @see Mapping
 * @see Mappings
 * @see DefaultMappingValidator
 * @since 2.0
 */
public class JustTag implements Validator {

    public static final String DESCRIPTION_FORMAT = "Element having '%s' as it's tag";

    private final String tagName;

    public JustTag(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean isValid(WebElement webElement) {
        return tagName.equalsIgnoreCase(webElement.getTagName());
    }

    @Override
    public String describe() {
        return String.format(DESCRIPTION_FORMAT, tagName);
    }

}
