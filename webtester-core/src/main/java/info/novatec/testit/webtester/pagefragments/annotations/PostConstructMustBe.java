package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
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
 * annotation. Doing so will trigger an automatic post construct check on the page / page fragment after it's
 * initialization. This check will take the {@link PostConstructMustBe @PostConstructMustBe's} condition and evaluate it.
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
 * // this will throw an exception if the username is not visible when the page is initialized
 * &#64;PostConstructMustBe(Visible.class)
 * &#64;IdentifyUsing("#username")
 * TextField username();
 *
 * // in cases where fragments are created using JavaScript an additional wait can be defined in the following way:
 * &#64;PostConstructMustBe(Visible.class)
 * &#64;WaitUntil(Present.class)
 * &#64;IdentifyUsing("#username")
 * TextField username();
 * </pre>
 *
 * @see Predicate
 * @see Conditions
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostConstructMustBe {

    /**
     * The {@link Predicate} predicate to use for the must check.
     *
     * @return the predicate to use
     * @since 2.0
     */
    Class<? extends Predicate<PageFragment>> value();

}
