package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.config.Configuration;
import info.novatec.testit.webtester.markings.Marker;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This annotation can be used to trigger the marking of a {@link PageFragment} in case a specific method of that fragment
 * is
 * invoked.
 * <p>
 * <b>Example:</b>
 * <pre>
 * public interface FooFragment extends PageFragment {
 *
 *      &#64;Mark(As.USED)
 *      default void changeStateOfFragment() {
 *          ...
 *      }
 *
 * }
 * </pre>
 *
 * @see Marker
 * @see Configuration#isMarkingsEnabled()
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mark {

    /**
     * The kind of marking that should be applied. Depending on this the used color may differ.
     *
     * @return the kind of marking
     * @see Mark
     * @see Marker
     * @see As
     * @since 2.0
     */
    As value();

}
