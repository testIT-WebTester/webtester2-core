package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.mapping.Validator;
import info.novatec.testit.webtester.pagefragments.mapping.validators.NoOpValidator;


/**
 * This annotation can be applied to {@link PageFragment page fragment} classes and will be evaluated when checking the page
 * object's class mapping. This check will be executed whenever the actual {@link WebElement web element} is retrieved from a
 * page fragment.
 * <p>
 * There are several ways of using this annotation:
 * <dl>
 * <dt><b>@Mapping(tag="div")</b></dt>
 * <dd>Will be evaluated as 'valid' in case the web element has the tag 'div'.</dd>
 * <dt><b>@Mapping(tag="select", attribute="multiple")</b></dt>
 * <dd>Will be evaluated as 'valid' in case the web element has the tag 'select' and the 'multiple' attribute is
 * present.</dd>
 * <dt><b>@Mapping(tag="select", attribute="!multiple")</b></dt>
 * <dd>Will be evaluated as 'valid' in case the web element has the tag 'select' and the 'multiple' attribute is not
 * present.</dd>
 * <dt><b>@Mapping(tag="input", attribute="type", values={"text", "password"})</b></dt>
 * <dd>Will be evaluated as 'valid' in case the web element has the tag 'input' and the 'type' attribute has either the
 * 'text' oder 'password' value.</dd>
 * <dt><b>@Mapping(validator=FooValidator.class)</b></dt>
 * <dd>Will create a new instance of the given validator class and use it to evaluate the web element.</dd>
 * </dl>
 *
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Mappings.class)
public @interface Mapping {

    /**
     * @return the valid tag for this {@link Mapping}
     * @since 2.0
     */
    String tag();

    /**
     * @return the name of the attribute that needs to be present for this {@link Mapping} - might be negated by prefixing
     * the attribute's name with a '!'.
     * @since 2.0
     */
    String attribute() default "";

    /**
     * @return a number of valid values for the given attribute
     * @since 2.0
     */
    String[] values() default {};

    /**
     * @return tha validator class to use when evaluating the web element
     * @since 1.2.0
     */
    Class<? extends Validator> validator() default NoOpValidator.class;

}
