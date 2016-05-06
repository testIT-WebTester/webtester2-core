package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;


/**
 * Identification methods of {@link Page pages} and {@link PageFragment page fragments} can be annotation with this
 * annotation. Doing so will trigger an automatic post construct check on the page / page fragment after it's
 * initialization. This check will take the {@link Must @Must's} condition (provided by the {@link Be}) and evaluate it.
 * <p>
 * <b>Example:</b>
 * <pre>
 * // this will throw an exception if the username is not visible when the page is initialized
 * &#64;Must(Be.VISIBLE)
 * &#64;IdentifyUsing("#username")
 * TextField username();
 *
 * // in cases where fragments are created using JavaScript an additional wait can be defined in the following way:
 * &#64;Must(Be.VISIBLE)
 * &#64;Wait(Until.PRESENT)
 * &#64;IdentifyUsing("#username")
 * TextField username();
 * </pre>
 *
 * @see Be
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Must {

    /**
     * The {@link Be} predicate to use for the must check.
     *
     * @return the be predicate to use
     * @since 2.0
     */
    Be value();

}
