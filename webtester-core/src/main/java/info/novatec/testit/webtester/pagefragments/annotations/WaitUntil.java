package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.conditions.Condition;
import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.conditions.pagefragments.Disabled;
import info.novatec.testit.webtester.conditions.pagefragments.Editable;
import info.novatec.testit.webtester.conditions.pagefragments.Enabled;
import info.novatec.testit.webtester.conditions.pagefragments.Interactable;
import info.novatec.testit.webtester.conditions.pagefragments.Invisible;
import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.PresentAndVisible;
import info.novatec.testit.webtester.conditions.pagefragments.ReadOnly;
import info.novatec.testit.webtester.conditions.pagefragments.Selected;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;


/**
 * Identification methods of {@link Page pages} and {@link PageFragment page fragments} can be annotation with this
 * annotation. With it the framework will wait until the specified {@link #value() condition} returns true or the timeout is
 * reached. In case no custom timeout is configured, the defaults of the host {@link Browser browser's} {@link
 * Configuration} is used.
 * <p>
 * <b>Important:</b> The used condition class must provide a default constructor! Hence not all of our provided {@link
 * Conditions} will work. The following conditions can be used:
 * <ul>
 * <li>{@link Disabled}</li>
 * <li>{@link Editable}</li>
 * <li>{@link Enabled}</li>
 * <li>{@link Interactable}</li>
 * <li>{@link Invisible}</li>
 * <li>{@link Present}</li>
 * <li>{@link PresentAndVisible}</li>
 * <li>{@link ReadOnly}</li>
 * <li>{@link Selected}</li>
 * <li>{@link Visible}</li>
 * </ul>
 * <p>
 * <b>Example:</b>
 * <pre>
 * &#64;WaitUntil(Visible.class)
 * &#64;IdentifyUsing("#username")
 * TextField username();
 * </pre>
 *
 * @see WaitUntil
 * @since 2.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WaitUntil {

    /**
     * The until condition to use for the wait operation.
     *
     * @return the until condition to use
     * @since 2.0
     */
    Class<? extends Condition> value();

    /**
     * The timeout to use. Defaults to {@code 0} which will signal the framework to use the {@link Browser browser's}
     * configured default timeout. The default unit of time is seconds. This can be changed by also setting the {@link
     * #unit()} property.
     *
     * @return the timeout to use
     * @since 2.0
     */
    int timeout() default 0;

    /**
     * The {@link TimeUnit} to use for the timeout. This will only be used in case {@link #timeout()} is set to a value
     * greater than {@code 0}. The smallest unit that will have an effect is milliseconds!
     *
     * @return the time unit to use
     * @since 2.0
     */
    TimeUnit unit() default TimeUnit.SECONDS;

}
