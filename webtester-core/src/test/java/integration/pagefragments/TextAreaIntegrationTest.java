package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClearedEvent;
import info.novatec.testit.webtester.events.pagefragments.TextAppendedEvent;
import info.novatec.testit.webtester.events.pagefragments.TextSetEvent;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pagefragments.TextArea;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class TextAreaIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/textArea.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* set text */

    @Test
    public void textCanBeSet() {
        TextArea textArea = page.empty().setText("foo");
        assertThat(textArea.getText()).isEqualTo("foo");
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
    public void multiLineTextCanBeSet() {
        String multiLineText = "foo\nbar\nxur";
        TextArea textArea = page.empty().setText(multiLineText);
        assertThat(textArea.getText()).isEqualTo(multiLineText);
    }

    /* append text */

    @Test
    public void textCanBeAppended() {
        TextArea textArea = page.withValue().appendText(" with appended text");
        assertThat(textArea.getText()).isEqualTo("value with appended text");
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
    public void multiLineTextCanBeAppended() {
        String multiLineText = "\nfoo\nbar\nxur";
        TextArea textArea = page.withValue().appendText(multiLineText);
        assertThat(textArea.getText()).isEqualTo("value" + multiLineText);
    }

    /* clear */

    @Test
    public void textCanBeCleared() {
        TextArea textArea = page.withValue().clear();
        assertThat(textArea.getText()).isEmpty();
    }

    @Test
    public void clearingTextFiresEvent() {
        EventCaptor.capture(eventSystem(), ClearedEvent.class)
            .execute(() -> page.withValue().clear())
            .assertEventWasFired();
    }

    /* get text */

    @Test
    public void gettingTextReturnsIsAsString() {
        assertThat(page.withValue().getText()).isEqualTo("value");
    }

    @Test
    public void gettingTextOfEmptyTextAreaReturnsEmptyString() {
        assertThat(page.empty().getText()).isEqualTo("");
    }

    /* get visible text */

    @Test
    public void gettingVisibleTextOfEmptyTextAreaReturnsEmptyString() {
        assertThat(page.empty().getVisibleText()).isEqualTo("");
    }

    @Test
    public void gettingVisibleTextReturnsString() {
        assertThat(page.withValue().getVisibleText()).isEqualTo("value");
    }

    /* getting rows */

    @Test
    public final void rowCountCanBeRead() {
        assertThat(page.twentyOneRowsFortyTwoColumns().getRowCount()).isEqualTo(21);
    }

    @Test
    public final void ifNoRowCountIsSpecifiedDefaultIsReturned() {
        assertThat(page.noRowsNoColumns().getRowCount()).isEqualTo(2);
    }

    /* getting columns */

    @Test
    public final void columnCountCanBeRead() {
        assertThat(page.twentyOneRowsFortyTwoColumns().getColumnCount()).isEqualTo(42);
    }

    @Test
    public final void ifNoColumnCountIsSpecifiedDefaultIsReturned() {
        assertThat(page.noRowsNoColumns().getColumnCount()).isEqualTo(20);
    }

    /* mapping */

    @Test
    public void textAreaTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.empty());
    }

    @Test(expected = MappingException.class)
    public void nonTextAreaTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noTextArea());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#noRowsNoColumns")
        TextArea noRowsNoColumns();
        @IdentifyUsing("#twentyOneRowsFortyTwoColumns")
        TextArea twentyOneRowsFortyTwoColumns();

        @IdentifyUsing("#empty")
        TextArea empty();
        @IdentifyUsing("#withValue")
        TextArea withValue();
        @IdentifyUsing("#noTextArea")
        TextArea noTextArea();

    }

}
