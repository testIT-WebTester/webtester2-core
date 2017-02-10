package info.novatec.testit.webtester.pagefragments.mapping.validators;

import org.openqa.selenium.WebElement;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;
import info.novatec.testit.webtester.internal.mapping.DefaultMappingValidator;
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
@AllArgsConstructor
public class JustTag implements Validator {

    public static final String DESCRIPTION_FORMAT = "Element having '%s' as it's tag";

    @NonNull
    private final String tagName;

    @Override
    public boolean isValid(WebElement webElement) {
        return tagName.equalsIgnoreCase(webElement.getTagName());
    }

    @Override
    public String describe() {
        return String.format(DESCRIPTION_FORMAT, tagName);
    }

}
