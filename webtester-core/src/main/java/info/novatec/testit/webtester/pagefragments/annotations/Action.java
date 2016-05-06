package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.internal.configuration.NamedProperties;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Marks an {@link PageFragment page fragment} method to be an 'action'.
 * <p>
 * Marking a method as an action allows for the following:
 * <ul>
 * <li>Decelerating the execution by setting the {@link NamedProperties#ACTIONS_DECELERATION} property.</li>
 * <li>Automatic marking as 'used' in case the {@link NamedProperties#MARKINGS} is set.</li>
 * </ul>
 * <p>
 * <b>Constraints:</b> The annotation is only evaluated for page fragment default methods!
 *
 * @see PageFragment
 * @see NamedProperties#ACTIONS_DECELERATION
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
}
