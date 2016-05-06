package info.novatec.testit.webtester.css;

import java.util.Map;

import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Implementations of this interface are used to change the style information of {@link PageFragment page fragment}.
 *
 * @see CssProperties
 * @see PageFragment
 * @since 2.0
 */
public interface StyleChanger {

    /**
     * Change the CSS style information of the given {@link PageFragment page fragment} using the given CSS property and
     * value.
     * <p>
     * Since this is regarded as an optional operation, exceptions which occur while changing the style will be logged but
     * not delegated upstream. You can check the return value if this method's success is important.
     *
     * @param pageFragment the page fragment
     * @param property the CSS property to change
     * @param value the value to change it to
     * @return true if the style was changed, false otherwise
     * @see PageFragment
     * @see CssProperties
     * @see StyleChanger
     * @since 2.0
     */
    boolean changeStyleInformation(PageFragment pageFragment, String property, String value);

    /**
     * Change the CSS style information of the given {@link PageFragment page fragment} using the given CSS property to
     * value
     * map.
     * <p>
     * Since this is regarded as an optional operation, exceptions which occur while changing the style will be logged but
     * not delegated upstream. You can check the return value if this method's success is important.
     *
     * @param pageFragment the {@link PageFragment} the CSS style should be changed for.
     * @param cssStyleProperties a map of CSS property to value entries describing which properties to change and which
     * values to use.
     * @return true if the style was changed, false otherwise
     * @see PageFragment
     * @see CssProperties
     * @see StyleChanger
     * @since 2.0
     */
    boolean changeStyleInformation(PageFragment pageFragment, Map<String, String> cssStyleProperties);

}
