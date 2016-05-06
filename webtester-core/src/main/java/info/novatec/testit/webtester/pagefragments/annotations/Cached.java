package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.internal.configuration.NamedProperties;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Allows for the configuration of the caching behaviour of a {@link PageFragment page fragment's}
 * underlying {@link WebElement}.
 * This annotation overrides the global configuration of {@link NamedProperties#CACHES}.
 * <p>
 * <b>Note: </b> Activating caches may lead to {@link StaleElementReferenceException}s in case of heavy JavaScript use.
 * <p>
 * <b>Examples:</b>
 * <pre>
 * public interface SomePage extends Page {
 *
 *      // underlying WebElement will be cached according to global configuration
 *      &#64;IdentifyUsing("#textfield")
 *      TextField textField1()
 *
 *      // underlying WebElement will be cached
 *      &#64;Cached
 *      &#64;IdentifyUsing("#textfield")
 *      TextField textField2()
 *
 *      // underlying WebElement will be cached
 *      &#64;Cached(true)
 *      &#64;IdentifyUsing("#textfield")
 *      TextField textField3()
 *
 *      // underlying WebElement will NOT be cached
 *      &#64;Cached(false)
 *      &#64;IdentifyUsing("#textfield")
 *      TextField textField4()
 *
 * }
 * </pre>
 * <p>
 * <b>Constraints:</b> The annotation is only evaluated for single page fragment returning
 * methods annotated with {@link IdentifyUsing}!
 *
 * @see NamedProperties
 * @see PageFragment
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cached {

    /**
     * Whether or not the WebElement should be cached. This will override whatever global configuration is set.
     *
     * @return true if caching is active, false otherwise
     */
    boolean value() default true;

}
