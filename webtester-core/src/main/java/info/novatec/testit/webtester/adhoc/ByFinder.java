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
@AllArgsConstructor
@Getter(AccessLevel.PACKAGE)
public class ByFinder {

    @NonNull
    private final PageFragmentFactory factory;
    @NonNull
    private final SearchContext searchContext;
    @NonNull
    private final By by;

    /**
     * Creates a {@link GenericElement generic page element} using the finder's {@link By}.
     * <p>
     * Since this operation requires a {@link WebElement} to be found, it might throw a {@link NoSuchElementException} in
     * case there is no matching element.
     *
     * @return the created page object
     * @throws NoSuchElementException in case the element could not be found
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
     * <p>
     * Since this operation requires a {@link WebElement} to be found, it might throw a {@link NoSuchElementException} in
     * case there is no matching element.
     *
     * @param <T> the type of the page fragment to create
     * @param fragmentClass the class of the page fragment to create
     * @return the created page object
     * @throws NoSuchElementException in case the element could not be found
     * @see By
     * @see ByFinder
     * @see PageFragment
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    public <T extends PageFragment> T as(Class<T> fragmentClass) {
        WebElement webElement = searchContext.findElement(by);
        PageFragmentDescriptor descriptor = PageFragmentDescriptor.builder()
            .pageFragmentType(fragmentClass)
            .webElementSupplier(new StaticWebElementSupplier(webElement))
            .build();
        return ( T ) factory.createInstanceOf(descriptor);
    }

    /**
     * Creates a {@link Stream} of {@link GenericElement generic page elements} using the finder's {@link By}.
     * <p>
     * The stream might be empty if no matching {@link WebElement WebElements} could be found.
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
     * <p>
     * The stream might be empty if no matching {@link WebElement WebElements} could be found.
     *
     * @param <T> the type of the page fragment stream to create
     * @param fragmentClass the class of the page fragments to create
     * @return the created stream
     * @see By
     * @see ByFinder
     * @see PageFragment
     * @since 2.0
     */
    @SuppressWarnings("unchecked")
    public <T extends PageFragment> Stream<T> asMany(Class<T> fragmentClass) {
        return searchContext.findElements(by).stream().map(webElement -> {
            PageFragmentDescriptor descriptor = PageFragmentDescriptor.builder()
                .pageFragmentType(fragmentClass)
                .webElementSupplier(new StaticWebElementSupplier(webElement))
                .build();
            return ( T ) factory.createInstanceOf(descriptor);
        });
    }

}
