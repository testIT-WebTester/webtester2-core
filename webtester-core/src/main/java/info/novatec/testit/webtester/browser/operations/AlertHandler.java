package info.novatec.testit.webtester.browser.operations;

import org.openqa.selenium.Alert;
import org.openqa.selenium.Beta;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.security.Credentials;
import org.openqa.selenium.security.UserAndPassword;

import lombok.extern.slf4j.Slf4j;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.events.browser.AcceptedAlertEvent;
import info.novatec.testit.webtester.events.browser.DeclinedAlertEvent;
import info.novatec.testit.webtester.internal.ActionTemplate;


/**
 * This browser operation offers methods related to alert message handling.
 *
 * @see #accept()
 * @see #acceptIfPresent()
 * @see #decline()
 * @see #declineIfPresent()
 * @since 2.0
 */
@Slf4j
public class AlertHandler extends BaseBrowserOperation {

    /**
     * Creates a new {@link AlertHandler} for the given {@link Browser}.
     *
     * @param browser the browser to use
     * @since 2.0
     */
    public AlertHandler(Browser browser) {
        super(browser);
    }

    /**
     * Authenticate a Basic-Auth popup using the given username and password.
     * <p>
     * This operation is declared as "BETA" by the Selenium developers and might break in the future in case it changes.
     *
     * @param username the username to use
     * @param password the password to use
     * @return the original browser of this operation
     * @see Alert#authenticateUsing(Credentials)
     * @since 2.0
     */
    @Beta
    public Browser authenticateWith(String username, String password) {
        return authenticateWith(new UserAndPassword(username, password));
    }

    /**
     * Authenticate a Basic-Auth popup using the given {@link Credentials credendials}.
     * <p>
     * This operation is decalred as "BETA" by the Selenium developers and might break in the future in case it changes.
     *
     * @param credentials the credentials to use
     * @return the original browser of this operation
     * @see Alert#authenticateUsing(Credentials)
     * @since 2.0
     */
    @Beta
    public Browser authenticateWith(Credentials credentials) {
        ActionTemplate.browser(browser()).execute(browser -> webDriver().switchTo().alert().authenticateUsing(credentials));
        log.debug("authenticated using credentials: {}", credentials);
        return browser();
    }

    /**
     * Accept any alert message in case one is displayed.
     * If no alert is displayed, the method will do nothing.
     * <p>
     * Fires {@link AcceptedAlertEvent} in case a alert was successfully accepted.
     *
     * @return the original browser of this operation
     * @see Alert#accept()
     * @since 2.0
     */
    public Browser acceptIfPresent() {
        if (isPresent()) {
            log.debug("alert was visible");
            return accept();
        }
        log.debug("alert was not visible");
        return browser();
    }

    /**
     * Accept any displayed alert message. If no alert is displayed, an exception will be thrown.
     * <p>
     * Fires {@link AcceptedAlertEvent} in case a alert was successfully accepted.
     *
     * @return the original browser of this operation
     * @throws NoAlertPresentException in case no alert is present
     * @see Alert#accept()
     * @since 2.0
     */
    public Browser accept() throws NoAlertPresentException {
        StringBuilder builder = new StringBuilder();
        ActionTemplate.browser(browser()).execute(browser -> {
            Alert alert = webDriver().switchTo().alert();
            builder.append(alert.getText());
            alert.accept();
        }).fireEvent(browser -> new AcceptedAlertEvent(builder.toString()));
        log.debug("alert was accepted");
        return browser();
    }

    /**
     * Declines any alert message in case one is displayed.
     * If no alert is displayed, the method will do nothing.
     * <p>
     * Fires {@link DeclinedAlertEvent} in case a alert was successfully accepted.
     *
     * @return the original browser of this operation
     * @see Alert#dismiss()
     * @since 2.0
     */
    public Browser declineIfPresent() {
        if (isPresent()) {
            log.debug("alert was visible");
            return decline();
        }
        log.debug("alert was not visible");
        return browser();
    }

    /**
     * Declines any displayed alert message. If no alert is displayed, an exception will be thrown.
     * <p>
     * Fires {@link DeclinedAlertEvent} in case a alert was successfully accepted.
     *
     * @return the original browser of this operation
     * @throws NoAlertPresentException in case no alert is present
     * @see Alert#dismiss()
     * @since 2.0
     */
    public Browser decline() throws NoAlertPresentException {
        StringBuilder builder = new StringBuilder();
        ActionTemplate.browser(browser()).execute(browser -> {
            Alert alert = webDriver().switchTo().alert();
            builder.append(alert.getText());
            alert.dismiss();
        }).fireEvent(browser -> new DeclinedAlertEvent(builder.toString()));
        log.debug("alert was declined");
        return browser();
    }

    /**
     * Returns whether or not an {@link Alert} is currently present.
     *
     * @return true if alert is present, otherwise false
     * @see WebDriver.TargetLocator#alert()
     * @since 2.0
     */
    public boolean isPresent() {
        try {
            webDriver().switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

}
