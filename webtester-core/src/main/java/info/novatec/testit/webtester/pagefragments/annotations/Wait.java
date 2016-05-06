package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;


/**
 * Identification methods of {@link Page pages} and {@link PageFragment page fragments} can be annotation with this
 * annotation. With it the framework will wait until the specified {@link #value() predicate} returns true or the timeout is
 * reached.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;Wait(Until.VISIBLE)
 * &#64;IdentifyUsing("#username")
 * TextField username();
 * </pre>
 *
 * @see Wait
 * @see Until
 * @since 2.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Wait {

    /**
     * The {@link Until} predicate to use for the wait operation.
     *
     * @return the until predicate to use
     * @since 2.0
     */
    Until value();

}
