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
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class TextFieldIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/textField.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* set text */

    @Test
    public void textCanBeSet() {
        TextField textField = page.empty().setText("foo");
        assertThat(textField.getText()).isEqualTo("foo");
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

    @Test
    public void textOfTextFieldWithMaxLengthMightOnlyBePartiallySet() {
        TextField textField = page.maxLength().setText("1234567890");
        assertThat(textField.getText()).isEqualTo("12");
    }

    /* append text */

    @Test
    public void textCanBeAppended() {
        TextField element = page.withValue().appendText(" with appended text");
        assertThat(element.getText()).isEqualTo("value with appended text");
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

    @Test
    public void textOfTextFieldWithMaxLengthMightOnlyBePartiallyAppended() {
        TextField textField = page.withValueAndMaxLength().appendText("3456");
        assertThat(textField.getText()).isEqualTo("123");
    }

    /* clear */

    @Test
    public void textCanBeCleared() {
        TextField textField = page.withValue().clear();
        assertThat(textField.getText()).isEmpty();
    }

    @Test
    public void clearingTextFiresEvent() {
        EventCaptor.capture(eventSystem(), ClearedEvent.class)
            .execute(() -> page.withValue().clear())
            .assertEventWasFired();
    }

    /* get text */

    @Test
    public void gettingTextReturnsItAsString() {
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
        page.enterTextField().pressEnter();
        assertThat(currentPageTitle()).isEqualTo("Target Page");
    }

    @Test
    public void pressingEnterFiresEvent(){
        EventCaptor.capture(eventSystem(), EnterPressedEvent.class)
            .execute(() -> page.enterTextField().pressEnter())
            .assertEventWasFired();
    }

    /* mapping */

    @Test
    public void inputNoTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.inputNone());
    }

    @Test
    public void inputEmptyTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.inputEmpty());
    }

    @Test
    public void inputTextTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.inputText());
    }

    @Test(expected = MappingException.class)
    public void nonTextFieldTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noTextField());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#maxLength")
        TextField maxLength();

        @IdentifyUsing("#empty")
        TextField empty();
        @IdentifyUsing("#withValue")
        TextField withValue();
        @IdentifyUsing("#withValueAndMaxLength")
        TextField withValueAndMaxLength();

        @IdentifyUsing("#input_none")
        TextField inputNone();
        @IdentifyUsing("#input_empty")
        TextField inputEmpty();
        @IdentifyUsing("#input_text")
        TextField inputText();
        @IdentifyUsing("#noTextField")
        TextField noTextField();

        @IdentifyUsing("#enterTextField")
        TextField enterTextField();

    }

}
