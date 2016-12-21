package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClearedEvent;
import info.novatec.testit.webtester.events.pagefragments.EnterPressedEvent;
import info.novatec.testit.webtester.events.pagefragments.TextAppendedEvent;
import info.novatec.testit.webtester.events.pagefragments.TextSetEvent;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class PasswordFieldIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/passwordField.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* set text */

    @Test
    public void textCanBeSet() {
        PasswordField passwordField = page.empty().setText("foo");
        assertThat(passwordField.getText()).isEqualTo("foo");
    }

    @Test
    public void settingTextFiresEvent() {
        EventCaptor.capture(eventSystem(), TextSetEvent.class)
            .execute(() -> page.empty().setText("foo"))
            .assertEventWasFired()
            .assertEvent(event -> {
                assertThat(event.getBefore()).isEqualTo("");
                assertThat(event.getAfter()).isEqualTo("foo");
            });
    }

    /* append text */

    @Test
    public void textCanBeAppended() {
        PasswordField passwordField = page.withValue().appendText(" with appended text");
        assertThat(passwordField.getText()).isEqualTo("value with appended text");
    }

    @Test
    public void appendingTextFiresEvent() {
        EventCaptor.capture(eventSystem(), TextAppendedEvent.class)
            .execute(() -> page.withValue().appendText(" with appended text"))
            .assertEventWasFired()
            .assertEvent(event -> {
                assertThat(event.getBefore()).isEqualTo("value");
                assertThat(event.getAfter()).isEqualTo("value with appended text");
            });
    }

    /* clear */

    @Test
    public void textCanBeCleared() {
        PasswordField passwordField = page.withValue().clear();
        assertThat(passwordField.getText()).isEmpty();
    }

    @Test
    public void clearingTextFiresEvent() {
        EventCaptor.capture(eventSystem(), ClearedEvent.class)
            .execute(() -> page.withValue().clear())
            .assertEventWasFired();
    }

    /* get text */

    @Test
    public void gettingTextOfReturnsItAsString() {
        assertThat(page.withValue().getText()).isEqualTo("value");
    }

    @Test
    public void gettingTextOfEmptyFieldReturnsEmptyString() {
        assertThat(page.empty().getText()).isEqualTo("");
    }

    /* get visible text */

    @Test
    public void gettingVisibleTextOfEmptyFieldReturnsEmptyString() {
        assertThat(page.empty().getVisibleText()).isEqualTo("");
    }

    @Test
    public void gettingVisibleTextReturnsString() {
        assertThat(page.withValue().getVisibleText()).isEqualTo("value");
    }

    /* pressing enter */

    @Test
    public void pressingEnterSendsForm(){
        page.enterPasswordField().pressEnter();
        assertThat(currentPageTitle()).isEqualTo("Target Page");
    }

    @Test
    public void pressingEnterFiresEvent(){
        EventCaptor.capture(eventSystem(), EnterPressedEvent.class)
            .execute(() -> page.enterPasswordField().pressEnter())
            .assertEventWasFired();
    }

    /* mapping */

    @Test
    public void inputPasswordTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.empty());
    }

    @Test(expected = MappingException.class)
    public void nonPasswordFieldTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noPasswordField());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#empty")
        PasswordField empty();
        @IdentifyUsing("#withValue")
        PasswordField withValue();

        @IdentifyUsing("#noPasswordField")
        PasswordField noPasswordField();

        @IdentifyUsing("#enterPasswordField")
        PasswordField enterPasswordField();

    }

}
