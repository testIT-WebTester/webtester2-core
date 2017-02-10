package info.novatec.testit.webtester.browser.operations;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import utils.integration.BaseIntTest;


public class AlertHandlerIntTest extends BaseIntTest {

    @Override
    protected String getHTMLFilePath() {
        return "html/browser/alert.html";
    }

    @Test
    public void alertsCanBeAccepted() {
        alert().accept();
        assertThat(alert().isPresent()).isFalse();
    }

    @Test
    public void alertsCanBeDeclined() {
        alert().decline();
        assertThat(alert().isPresent()).isFalse();
    }

    @Test
    public void presenceOfExistentAlertReturnsTrue() {
        try {
            assertThat(alert().isPresent()).isTrue();
        } finally {
            alert().acceptIfPresent();
        }
    }

}
