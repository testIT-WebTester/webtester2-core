package info.novatec.testit.webtester.adhoc;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.PageFragmentFactory;
import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.identification.ByProducer;
import info.novatec.testit.webtester.pagefragments.identification.ByProducers;
import info.novatec.testit.webtester.pages.Page;


/**
 * This class provides the means for finding and creating page fragments 'Ad-Hoc' in a programmatic fashion (compared with
 * declaring pages / fragments). It can be either initialized with a {@link Browser browser} or {@link PageFragment page
 * fragment} instance.
 * <p>
 * Even though it is possible to initialize a finder directly, the preferred way is to use {@link Browser#finder()}, {@link
 * PageFragment#finder()}, {@link Page#finder()} or any of their shorthand methods.
 *
 * @see Page#finder()
 * @see PageFragment#finder()
 * @see Browser#finder()
 * @since 2.0
 */
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class AdHocFinder {

    @NonNull
    private final PageFragmentFactory factory;
    @NonNull
    private final SearchContext searchContext;

    /**
     * Creates a new {@link AdHocFinder finder} instance using the given {@link Browser browser} as a base. The
     * created finder will find / create {@link PageFragment page fragments} by using the whole HTML page as it's search
     * context.
     *
     * @param browser the browser to use
     * @see PageFragment
     * @see AdHocFinder
     * @see Browser
     * @since 2.0
     */
    public AdHocFinder(Browser browser) {
        this.factory = new PageFragmentFactory(browser);
        this.searchContext = browser.webDriver();
    }

    /**
     * Creates a new {@link AdHocFinder finder} instance using the given {@link PageFragment page fragment} as a
     * base. The created finder will find / create page fragments by using the page fragment as it's search context. Meaning
     * that only page elements within the page object's HTML tags will be found.
     *
     * @param parent the page object to use as a parent
     * @see PageFragment
     * @see AdHocFinder
     * @since 2.0
     */
    public AdHocFinder(PageFragment parent) {
        this.factory = new PageFragmentFactory(parent.browser());
        this.searchContext = parent.webElement();
    }

    /**
     * Creates an {@link ByFinder By based finder}. It can be used to create different {@link PageFragment page fragment}
     * instance for a fixed CSS-Selector value. In case you want to use an other identification method use {@link
     * #findBy(By)} instead.
     *
     * @param cssSelector the CSS-Selector expression to use
     * @return the new identification based finder
     * @see By
     * @see ByFinder
     * @see PageFragment
     * @since 2.0
     */
    public ByFinder find(String cssSelector) {
        return findBy(ByProducers.css(cssSelector));
    }

    /**
     * Creates an {@link ByFinder By based finder}. It can be used to create different {@link PageFragment page fragment}
     * instance for a given {@link By By definition}. Instances of these definitions can be created using the {@link
     * ByProducers} utility class.
     *
     * @param by the By to use
     * @return the new identification based finder
     * @see By
     * @see ByFinder
     * @see ByProducers
     * @see ByProducer
     * @see PageFragment
     * @since 2.0
     */
    public ByFinder findBy(By by) {
        return new ByFinder(factory, searchContext, by);
    }

    /**
     * Creates an {@link TypeFinder type based finder} for {@link GenericElement generic elements}.
     * <p>
     * This is equal to calling {@link #find(Class)} with a {@link GenericElement} class reference.
     *
     * @return the new type based finder
     * @see GenericElement
     * @see TypeFinder
     * @see PageFragment
     * @since 2.0
     */
    public TypeFinder<GenericElement> findGeneric() {
        return new TypeFinder<>(factory, searchContext, GenericElement.class);
    }

    /**
     * Creates an {@link TypeFinder type based finder}. It can be used to create different {@link PageFragment page
     * fragment} instance for a given {@link PageFragment page fragment class}.
     *
     * @param <T> the type of the page fragment for which a finder should be created
     * @param fragmentClass the class to use when creating instances
     * @return the new type based finder
     * @see TypeFinder
     * @see PageFragment
     * @since 2.0
     */
    public <T extends PageFragment> TypeFinder<T> find(Class<T> fragmentClass) {
        return new TypeFinder<>(factory, searchContext, fragmentClass);
    }

}
