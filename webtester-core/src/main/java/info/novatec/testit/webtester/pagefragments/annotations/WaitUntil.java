package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.Predicate;

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
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pages.Page;


/**
 * Identification methods of {@link Page pages} and {@link PageFragment page fragments} can be annotation with this
 * annotation. With it the framework will wait until the specified {@link #value() predicate} returns true or the timeout is
 * reached.
 * <p>
 * <b>Important:</b> The used predicate class must provide a default constructor! Hence not all of our provided {@link
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
     * The until predicate to use for the wait operation.
     *
     * @return the until predicate to use
     * @since 2.0
     */
    Class<? extends Predicate<PageFragment>> value();

    // TODO: timeout

}
