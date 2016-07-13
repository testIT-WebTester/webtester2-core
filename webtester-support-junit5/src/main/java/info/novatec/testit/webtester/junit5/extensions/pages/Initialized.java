package info.novatec.testit.webtester.junit5.extensions.pages;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.BeforeEach;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.junit5.extensions.browsers.Managed;
import info.novatec.testit.webtester.pages.Page;


/**
 * This annotation can be used on {@link Page} fields in order to trigger the automatic creation and injection of a
 * corresponding instance into the field.
 * <p>
 * The injection is done before the first {@link BeforeEach} annotated method is executed. Which means that these pages can
 * be used by those methods.
 * <p>
 * Because pages are created for a specific browser a {@link #source()} must be specified in case multiple browsers are used
 * within the same test.
 * <p>
 * <b>Note:</b> This annotation can only be applied on non-static instance fields!
 *
 * @see Managed
 * @see Browser
 * @see Page
 * @see PageInitializerExtension
 * @since 2.1
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Initialized {

    /**
     * The name of the source {@link Browser} to use when creating a {@link Page}.
     *
     * @return the source browser's name
     * @see Managed
     * @since 2.1
     */
    String source() default "default";

}
