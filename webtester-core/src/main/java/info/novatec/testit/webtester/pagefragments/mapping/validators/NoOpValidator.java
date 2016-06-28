package info.novatec.testit.webtester.pagefragments.mapping.validators;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mappings;
import info.novatec.testit.webtester.pagefragments.mapping.DefaultMappingValidator;
import info.novatec.testit.webtester.pagefragments.mapping.Validator;


/**
 * This is a {@link Validator} implementation that basically does nothing. It will return <code>true</code> for each call on
 * {@link #isValid(WebElement)}. The main reason this class exists is to use it as a default in the {@link Mapping}
 * annotation. In case this class is set on the annotation (default), the {@link DefaultMappingValidator} will use the annotation's
 * properties to generate a validator instead of using this one.
 *
 * @see Validator
 * @see Mapping
 * @see Mappings
 * @see DefaultMappingValidator
 * @since 2.0
 */
public class NoOpValidator implements Validator {

    @Override
    public boolean isValid(WebElement webElement) {
        return true;
    }

    @Override
    public String describe() {
        return "NO-OP";
    }

}
