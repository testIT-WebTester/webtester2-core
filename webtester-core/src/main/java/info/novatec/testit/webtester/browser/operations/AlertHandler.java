package info.novatec.testit.webtester.browser.operations;

import java.util.Optional;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

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
     * Accept any alert message in case one is displayed.
     * If no alert is displayed, the method will do nothing.
     * <p>
     * Fires {@link AcceptedAlertEvent} in case a alert was successfully accepted.
     *
     * @see Alert#accept()
     * @since 2.0
     */
    public void acceptIfPresent() {
        if (isPresent()) {
            log.debug("alert was visible");
            accept();
        } else {
            log.debug("alert was not visible");
        }
    }

    /**
     * Accept any displayed alert message. If no alert is displayed, an exception will be thrown.
     * <p>
     * Fires {@link AcceptedAlertEvent} in case a alert was successfully accepted.
     *
     * @throws NoAlertPresentException in case no alert is present
     * @see Alert#accept()
     * @since 2.0
     */
    public void accept() throws NoAlertPresentException {
        StringBuilder builder = new StringBuilder();
        ActionTemplate.browser(browser()).execute(browser -> {
            Alert alert = webDriver().switchTo().alert();
            builder.append(alert.getText());
            alert.accept();
        }).fireEvent(browser -> new AcceptedAlertEvent(builder.toString()));
        log.debug("alert was accepted");
    }

    /**
     * Declines any alert message in case one is displayed.
     * If no alert is displayed, the method will do nothing.
     * <p>
     * Fires {@link DeclinedAlertEvent} in case a alert was successfully accepted.
     *
     * @see Alert#dismiss()
     * @since 2.0
     */
    public void declineIfPresent() {
        if (isPresent()) {
            log.debug("alert was visible");
            decline();
        } else {
            log.debug("alert was not visible");
        }
    }

    /**
     * Declines any displayed alert message. If no alert is displayed, an exception will be thrown.
     * <p>
     * Fires {@link DeclinedAlertEvent} in case a alert was successfully accepted.
     *
     * @throws NoAlertPresentException in case no alert is present
     * @see Alert#dismiss()
     * @since 2.0
     */
    public void decline() throws NoAlertPresentException {
        StringBuilder builder = new StringBuilder();
        ActionTemplate.browser(browser()).execute(browser -> {
            Alert alert = webDriver().switchTo().alert();
            builder.append(alert.getText());
            alert.dismiss();
        }).fireEvent(browser -> new DeclinedAlertEvent(builder.toString()));
        log.debug("alert was declined");
    }

    /**
     * Returns whether or not an {@link Alert} is currently present.
     *
     * @return true if alert is present, otherwise false
     * @see WebDriver.TargetLocator#alert()
     * @since 2.0
     */
    public boolean isPresent() {
        return get().isPresent();
    }

    /**
     * Returns the Selenium {@link Alert} instance as an optional.
     *
     * @return optional of the alert - if no alert is present the optional will be empty.
     * @see Alert
     * @see WebDriver.TargetLocator#alert()
     * @since 2.0
     */
    public Optional<Alert> get() {
        try {
            return Optional.of(webDriver().switchTo().alert());
        } catch (NoAlertPresentException e) {
            return Optional.empty();
        }
    }

}
