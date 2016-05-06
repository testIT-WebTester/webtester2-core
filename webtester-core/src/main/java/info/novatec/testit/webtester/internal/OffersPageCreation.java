package info.novatec.testit.webtester.internal;

import info.novatec.testit.webtester.internal.annotations.CreatesPage;
import info.novatec.testit.webtester.pages.Page;


public interface OffersPageCreation {

    /**
     * Creates an instance of the given {@link Page page} class.
     *
     * @param <T> the type of the page object to return
     * @param pageClass the page object class to be initialized
     * @return an initialize page object of the given class
     * @see Page
     * @since 2.0
     */
    @CreatesPage
    <T extends Page> T create(Class<T> pageClass);

}
