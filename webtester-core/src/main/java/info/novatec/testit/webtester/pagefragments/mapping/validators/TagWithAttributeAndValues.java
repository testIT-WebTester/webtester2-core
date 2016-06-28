package info.novatec.testit.webtester.pagefragments.mapping.validators;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;
import info.novatec.testit.webtester.pagefragments.mapping.DefaultMappingValidator;
import info.novatec.testit.webtester.pagefragments.mapping.Validator;


/**
 * This {@link Validator} validates a {@link WebElement web element's} tag and the existence of a given attribute plus a
 * number of allowed values for that attribute.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;Mapping(tag="input", attribute="type", values={"", "text"})
 * public interface TextField extends PageFragment {...}
 * </pre>
 *
 * @see Validator
 * @see Mapping
 * @see Mappings
 * @see DefaultMappingValidator
 * @since 2.0
 */
public class TagWithAttributeAndValues extends JustTag {

    private static final String DESCRIPTION_FORMAT = "%s and an attribute '%s' with any value of %s";

    private final String attributeName;
    private final Set<String> validValues;

    public TagWithAttributeAndValues(String tagName, String attributeName, String[] values) {
        super(tagName);
        this.attributeName = attributeName;
        this.validValues = toNormalizedSet(values);
    }

    @Override
    public boolean isValid(WebElement webElement) {
        String attributeValue = webElement.getAttribute(attributeName);
        String normalizedAttributeValue = normalize(attributeValue);
        return super.isValid(webElement) && attributeValue != null && validValues.contains(normalizedAttributeValue);
    }

    @Override
    public String describe() {
        return String.format(DESCRIPTION_FORMAT, super.describe(), attributeName, validValues);
    }

    private static Set<String> toNormalizedSet(String... values) {
        return Arrays.stream(values).map(TagWithAttributeAndValues::normalize).collect(Collectors.toSet());
    }

    private static String normalize(String value) {
        return StringUtils.defaultString(value).toLowerCase();
    }

}
