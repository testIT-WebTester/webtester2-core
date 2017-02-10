package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClearedEvent;
import info.novatec.testit.webtester.events.pagefragments.NumberSetEvent;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class NumberFieldIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/numberField.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* set value */

    @Test
    public void valueOfNumberFieldCanBeSet() {
        NumberField numberField = page.empty().setValue(42L);
        assertThat(numberField.getValue()).contains(42L);
    }

    @Test
    public void settingValueOfNumberFieldsFiresEvent() {
        EventCaptor.capture(eventSystem(), NumberSetEvent.class)
            .execute(() -> page.empty().setValue(42L))
            .assertEventWasFired()
            .assertEvent(event -> {
                assertThat(event.getBefore()).isEmpty();
                assertThat(event.getAfter()).contains(42L);
            });
    }

    /* clear */

    @Test
    public void valueOfNumberFieldCanBeCleared() {
        NumberField numberField = page.withValue().clear();
        assertThat(numberField.getValue()).isEmpty();
    }

    @Test
    public void clearingValueOfNumberFieldsFiresEvent() {
        EventCaptor.capture(eventSystem(), ClearedEvent.class)
            .execute(() -> page.withValue().clear())
            .assertEventWasFired();
    }

    /* get value */

    @Test
    public void gettingValueOfNumberFieldReturnsValueAsOptional() {
        assertThat(page.withValue().getValue()).contains(42L);
    }

    @Test
    public void gettingValueOfEmptyNumberFieldReturnsEmptyOptional() {
        assertThat(page.empty().getValue()).isEmpty();
    }

    /* get visible text */

    @Test
    public void gettingVisibleTextOfEmptyNumberFieldReturnsEmptyString() {
        assertThat(page.empty().getVisibleText()).isEqualTo("");
    }

    @Test
    public void gettingVisibleTextOfNumberFieldReturnsValueAsString() {
        assertThat(page.withValue().getVisibleText()).isEqualTo("42");
    }

    /* mapping */

    @Test
    public final void inputNumberFieldTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.empty());
    }

    @Test(expected = MappingException.class)
    public void nonNumberFieldTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noNumberField());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#empty")
        NumberField empty();
        @IdentifyUsing("#withValue")
        NumberField withValue();

        @IdentifyUsing("#noNumberField")
        NumberField noNumberField();

    }

}
