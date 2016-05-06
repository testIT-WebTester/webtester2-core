package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.identification.ByProducer;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;
import info.novatec.testit.webtester.pagefragments.identification.producers.CssSelector;
import info.novatec.testit.webtester.pages.Page;


/**
 * Annotating a {@link Page page's} method with this annotation will mark it as an {@link PageFragment page fragement}
 * identification method. These methods return instances of page fragments using the annotation's properties to find
 * {@link WebElement} on the currently displayed page.
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;IdentifyUsing("#username")
 * TextField username();
 *
 * &#64;IdentifyUsing(value = "password", how = Id.class)
 * PasswordField password();
 * </pre>
 *
 * @see Page
 * @see PageFragment
 * @see IdentifyUsing
 * @see ByProducer
 * @see ByProducers
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IdentifyUsing {

    /**
     * @return the value to use in combination with the {@link ByProducer}
     */
    String value();

    /**
     * @return the {@link ByProducer} to use in combination with the value - defaults to {@link CssSelector}
     */
    Class<? extends ByProducer> how() default CssSelector.class;

}
