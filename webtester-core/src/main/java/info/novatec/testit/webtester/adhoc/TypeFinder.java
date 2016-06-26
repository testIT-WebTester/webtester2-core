package info.novatec.testit.webtester.adhoc;

import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import lombok.AccessLevel;
import lombok.Getter;

import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;


/**
 * This finder implementation uses a fixed {@link PageFragment page fragment} class reference and provides methods for
 * initializing page objects based on this class.
 *
 * @param <T> the type of the page fragments to be created by this finder
 * @see PageFragment
 * @see AdHocFinder
 * @see By
 * @see ByProducers
 * @see TypeFinder#by(String)
 * @see TypeFinder#by(By)
 * @see TypeFinder#manyBy(String)
 * @see TypeFinder#manyBy(By)
 * @since 2.0
 */
@Getter(AccessLevel.PACKAGE)
public class TypeFinder<T extends PageFragment> {

    private final PageFragmentFactory factory;
    private final SearchContext searchContext;
    private final Class<T> fragmentClass;

    public TypeFinder(PageFragmentFactory factory, SearchContext searchContext, Class<T> fragmentClass) {
        this.factory = factory;
        this.searchContext = searchContext;
        this.fragmentClass = fragmentClass;
    }

    /**
     * Creates a {@link PageFragment page fragment} using the finder's specified class and the given CSS-Selector
     * expression.
     *
     * @param cssSelector the CSS-Selector expression to use as an identifier
     * @return the created page object
     * @see By
     * @see ByProducers
     * @see PageFragment
     * @since 2.0
     */
    public T by(String cssSelector) {
        return by(ByProducers.css(cssSelector));
    }

    /**
     * Creates a {@link PageFragment page fragment} using the finder's specified class and the given {@link By}.
     * Instances of these can be created using the {@link ByProducers} utility class.
     *
     * @param by the By to use
     * @return the created page object
     * @see By
     * @see ByProducers
     * @see PageFragment
     * @since 2.0
     */
    public T by(By by) {
        WebElement webElement = searchContext.findElement(by);
        return factory.pageFragment(fragmentClass, webElement);
    }

    /**
     * Creates a {@link Stream} of {@link PageFragment page fragments} using the finder's specified class and the given
     * CSS-Selector expression.
     *
     * @param cssSelector the CSS-Selector expression to use as an identifier
     * @return the created stream
     * @see By
     * @see ByProducers
     * @see PageFragment
     * @see Stream
     * @since 2.0
     */
    public Stream<T> manyBy(String cssSelector) {
        return manyBy(ByProducers.css(cssSelector));
    }

    /**
     * Creates a {@link Stream} of {@link PageFragment page fragments} using the finder's specified class and the given
     * {@link By}. Instances of these can be created using the {@link ByProducers} utility class.
     *
     * @param by the By to use
     * @return the created stream
     * @see By
     * @see ByProducers
     * @see PageFragment
     * @see Stream
     * @since 2.0
     */
    public Stream<T> manyBy(By by) {
        return searchContext.findElements(by)
            .stream()
            .map(webElement -> factory.pageFragment(fragmentClass, webElement));
    }

}
