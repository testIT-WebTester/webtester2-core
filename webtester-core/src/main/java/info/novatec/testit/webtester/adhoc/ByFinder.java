package info.novatec.testit.webtester.adhoc;

import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.identification.ByProducer;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;


/**
 * This finder implementation uses a fixed {@link By} and provides methods for initializing {@link PageFragment page
 * fragments} based on this identification.
 *
 * @see By
 * @see ByProducer
 * @see ByProducers
 * @see ByFinder#as(Class)
 * @see ByFinder#asGeneric()
 * @see ByFinder#asMany(Class)
 * @see ByFinder#asManyGenerics()
 * @since 2.0
 */
public class ByFinder {

    private final SearchContext searchContext;
    private final By by;
    private final PageFragmentFactory factory;

    public ByFinder(SearchContext searchContext, By by, PageFragmentFactory factory) {
        this.searchContext = searchContext;
        this.by = by;
        this.factory = factory;
    }

    /**
     * Creates a {@link GenericElement generic page element} using the finder's {@link By}.
     *
     * @return the created page object
     * @see By
     * @see ByFinder
     * @see GenericElement
     * @since 2.0
     */
    public GenericElement asGeneric() {
        return as(GenericElement.class);
    }

    /**
     * Creates a {@link PageFragment page fragment} of the given class using the finder's {@link By}.
     *
     * @param <T> the type of the page fragment to create
     * @param fragmentClass the class of the page fragment to create
     * @return the created page object
     * @see By
     * @see ByFinder
     * @see PageFragment
     * @since 2.0
     */
    public <T extends PageFragment> T as(Class<T> fragmentClass) {
        WebElement webElement = searchContext.findElement(by);
        return factory.pageFragment(fragmentClass, webElement);
    }

    /**
     * Creates a {@link Stream} of {@link GenericElement generic page elements} using the finder's {@link By}.
     *
     * @return the created stream
     * @see By
     * @see ByFinder
     * @see GenericElement
     * @since 2.0
     */
    public Stream<GenericElement> asManyGenerics() {
        return asMany(GenericElement.class);
    }

    /**
     * Creates a {@link Stream} of {@link PageFragment page fragments} of the given class using the finder's {@link By}.
     *
     * @param <T> the type of the page fragment stream to create
     * @param fragmentClass the class of the page fragments to create
     * @return the created stream
     * @see By
     * @see ByFinder
     * @see PageFragment
     * @since 2.0
     */
    public <T extends PageFragment> Stream<T> asMany(Class<T> fragmentClass) {
        return searchContext.findElements(by)
            .stream()
            .map(webElement -> factory.pageFragment(fragmentClass, webElement));
    }

}
