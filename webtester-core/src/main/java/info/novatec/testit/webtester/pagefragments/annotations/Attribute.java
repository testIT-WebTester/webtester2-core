package info.novatec.testit.webtester.pagefragments.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Marks an {@link PageFragment page fragment} method to return the value of a certain attribute.
 * <p>
 * <b>Example:</b>
 * <pre>
 * public interface SomePageFragment extends PageFragment {
 *
 *      // will return the value of the 'foo.bar' attribute of this page fragment as a Long
 *      &#64;Attribute("foo.bar")
 *      Long fooBar();
 *
 * }
 * </pre>
 * The following return types are supported:
 * <ul>
 * <li>String</li>
 * <li>Boolean</li>
 * <li>Integer</li>
 * <li>Long</li>
 * <li>Float</li>
 * <li>Double</li>
 * <li>Optional&lt;String&gt;</li>
 * <li>Optional&lt;Boolean&gt;</li>
 * <li>Optional&lt;Integer&gt;</li>
 * <li>Optional&lt;Long&gt;</li>
 * <li>Optional&lt;Float&gt;</li>
 * <li>Optional&lt;Double&gt;</li>
 * </ul>
 * <p>
 * <b>Constraints:</b> The annotation is only evaluated for page fragment methods without a (default) implementation!
 *
 * @see PageFragment
 * @since 2.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Attribute {

    /**
     * The name of the attribute who's value should be returned by the annotated method.
     *
     * @return the name of the attribute
     */
    String value();

}
