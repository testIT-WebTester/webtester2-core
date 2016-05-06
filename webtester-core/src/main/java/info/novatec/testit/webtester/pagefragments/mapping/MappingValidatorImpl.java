package info.novatec.testit.webtester.pagefragments.mapping;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.mapping.validators.JustTag;
import info.novatec.testit.webtester.pagefragments.mapping.validators.NoOpValidator;
import info.novatec.testit.webtester.pagefragments.mapping.validators.TagWithAttribute;
import info.novatec.testit.webtester.pagefragments.mapping.validators.TagWithAttributeAndValues;
import info.novatec.testit.webtester.pagefragments.mapping.validators.TagWithoutAttribute;


/**
 * This is the default implementation of {@link MappingValidator}.
 *
 * @see MappingValidator
 * @since 2.0
 */
public class MappingValidatorImpl implements MappingValidator {

    private final Class<? extends PageFragment> pageFragmentType;
    private final List<Validator> validators;
    private final List<String> validConstellationDescriptions;

    public MappingValidatorImpl(Class<? extends PageFragment> pageFragmentType) {
        this.pageFragmentType = pageFragmentType;
        this.validators = extractValidationInformation(pageFragmentType);
        this.validConstellationDescriptions = getValidConstellationDescriptions(this.validators);
    }

    private static List<Validator> extractValidationInformation(Class<?> type) {
        Mapping[] annotations = type.getAnnotationsByType(Mapping.class);
        return Arrays.stream(annotations)
            .map(MappingValidatorImpl::convertToValidConstellation)
            .collect(Collectors.toList());
    }

    private static Validator convertToValidConstellation(Mapping mapping) {
        String tag = mapping.tag();
        String attribute = mapping.attribute();
        String[] values = mapping.values();
        if (mapping.validator() != NoOpValidator.class) {
            try {
                return mapping.validator().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new UndeclaredThrowableException(e);
            }
        } else if (StringUtils.isNotBlank(attribute)) {
            if (values.length > 0) {
                return new TagWithAttributeAndValues(tag, attribute, values);
            }
            if ('!' == attribute.charAt(0)) {
                String attributeName = attribute.substring(1);
                return new TagWithoutAttribute(tag, attributeName);
            } else {
                return new TagWithAttribute(tag, attribute);
            }
        }
        return new JustTag(tag);
    }

    private static List<String> getValidConstellationDescriptions(List<Validator> validConstellations) {
        return validConstellations.stream().map(Validator::describe).collect(Collectors.toList());
    }

    @Override
    public WebElement assertValidity(WebElement webElement) {

        if (validators.isEmpty()) {
            return webElement;
        }

        validators.stream()
            .filter(constellation -> constellation.isValid(webElement))
            .findFirst()
            .orElseThrow(() -> new MappingException(getInvalidityMessage(webElement)));

        return webElement;

    }

    private String getInvalidityMessage(WebElement webElement) {
        return webElement + " is not a valid web element for class: " + pageFragmentType
            + "\n\tValid elements include: \n\t - " + StringUtils.join(validConstellationDescriptions, "\n\t - ");
    }

}
