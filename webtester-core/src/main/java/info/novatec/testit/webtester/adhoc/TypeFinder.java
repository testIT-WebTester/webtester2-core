package info.novatec.testit.webtester.adhoc;

import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.internal.implementation.PageFragmentFactory;
import info.novatec.testit.webtester.internal.implementation.PageFragmentFactory.PageFragmentDescriptor;
import info.novatec.testit.webtester.internal.implementation.pagefragments.StaticWebElementSupplier;
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
@AllArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class TypeFinder<T extends PageFragment> {

    @NonNull
    private final PageFragmentFactory factory;
    @NonNull
    private final SearchContext searchContext;
    @NonNull
    private final Class<T> fragmentClass;

    /**
     * Creates a {@link PageFragment page fragment} using the finder's specified class and the given CSS-Selector
     * expression.
     * <p>
     * Since this operation requires a {@link WebElement} to be found, it might throw a {@link NoSuchElementException} in
     * case there is no matching element.
     *
     * @param cssSelector the CSS-Selector expression to use as an identifier
     * @return the created page object
     * @throws NoSuchElementException in case the element could not be found
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
     * <p>
     * Since this operation requires a {@link WebElement} to be found, it might throw a {@link NoSuchElementException} in
     * case there is no matching element.
     *
     * @param by the By to use
     * @return the created page object
     * @throws NoSuchElementException in case the element could not be found
     * @see By
     * @see ByProducers
     * @see PageFragment
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    public T by(By by) {
        WebElement webElement = searchContext.findElement(by);
        PageFragmentDescriptor descriptor = PageFragmentDescriptor.builder()
            .pageFragmentType(fragmentClass)
            .webElementSupplier(new StaticWebElementSupplier(webElement))
            .build();
        return ( T ) factory.createInstanceOf(descriptor);
    }

    /**
     * Creates a {@link Stream} of {@link PageFragment page fragments} using the finder's specified class and the given
     * CSS-Selector expression.
     * <p>
     * The stream might be empty if no matching {@link WebElement WebElements} could be found.
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
     * <p>
     * The stream might be empty if no matching {@link WebElement WebElements} could be found.
     *
     * @param by the By to use
     * @return the created stream
     * @see By
     * @see ByProducers
     * @see PageFragment
     * @see Stream
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    public Stream<T> manyBy(By by) {
        return searchContext.findElements(by).stream().map(webElement -> {
            PageFragmentDescriptor descriptor = PageFragmentDescriptor.builder()
                .pageFragmentType(fragmentClass)
                .webElementSupplier(new StaticWebElementSupplier(webElement))
                .build();
            return ( T ) factory.createInstanceOf(descriptor);
        });
    }

}
