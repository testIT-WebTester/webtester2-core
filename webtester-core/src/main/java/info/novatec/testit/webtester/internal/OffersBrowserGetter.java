package info.novatec.testit.webtester.internal;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.annotations.ReturnsBrowser;


public interface OffersBrowserGetter {

    /**
     * Returns the {@link Browser browser} this instance was created from.
     *
     * @return the browser of this instance
     * @see Browser
     * @since 2.0
     */
    @ReturnsBrowser
    Browser browser();

}
