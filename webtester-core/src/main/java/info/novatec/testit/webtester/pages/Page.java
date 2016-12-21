package info.novatec.testit.webtester.pages;

import info.novatec.testit.webtester.adhoc.AdHocFinder;
import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.internal.OffersAdHocFinding;
import info.novatec.testit.webtester.internal.OffersBrowserGetter;
import info.novatec.testit.webtester.internal.OffersPageCreation;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;


/**
 * Base class for all WebTester pages.
 * <p>
 * Provides the means to:
 * <ul>
 * <li>retrieve the {@link Browser browser} instance the page was created from</li>
 * <li>create other pages to be used in navigation methods</li>
 * <li>ad-hoc find {@link PageFragment page fragments}</li>
 * </ul>
 * <b>Example Page:</b>
 * <pre>
 * public interface LoginPage extends Page {
 *
 *     &#64;Wait(Until.VISIBLE)
 *     &#64;IdentifyUsing("#username")
 *     TextField username();
 *
 *     &#64;IdentifyUsing("#password")
 *     PasswordField password();
 *
 *     &#64;Named("Login Button")
 *     &#64;IdentifyUsing("#login")
 *     Button login();
 *
 *     &#64;PostConstruct
 *     default void assertPageIsDisplayed() {
 *         assertThat(username().isVisible).isTrue();
 *         assertThat(password().isVisible).isTrue();
 *         assertThat(login().isVisible).isTrue();
 *     }
 *
 *     default MainPage loginWith(String username, String password) {
 *         return setUsername(username).setPassword(password).clickLogin();
 *     }
 *
 *     default LoginPage setUsername(String username) {
 *         username().setText(username);
 *         return this;
 *     }
 *
 *     default LoginPage setPassword(String password) {
 *         password().setText(password);
 *         return this;
 *     }
 *
 *     default MainPage clickLogin() {
 *         login().click();
 *         return create(MainPage.class);
 *     }
 *
 * }
 * </pre>
 *
 * @see IdentifyUsing
 * @see Browser
 * @see PageFragment
 * @see AdHocFinder
 */
public interface Page extends OffersBrowserGetter, OffersPageCreation, OffersAdHocFinding {

    @Override
    default AdHocFinder finder() {
        return new AdHocFinder(browser());
    }

}
