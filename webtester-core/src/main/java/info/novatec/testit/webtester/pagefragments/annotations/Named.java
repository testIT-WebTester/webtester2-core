package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;


/**
 * This annotation can be used to give a human readable name to {@link PageFragment page fragment} returned by
 * identification methods. This name can be retrieved by calling {@link PageFragment#getName()}.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;Named("Login Button")
 * &#64;IdentifyUsing("#f27abc")
 * Button loginButton();
 * </pre>
 *
 * @see Page
 * @see PageFragment
 * @see IdentifyUsing
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Named {

    /**
     * @return the name to use
     * @see Named
     * @since 2.0
     */
    String value();

}
