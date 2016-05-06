package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriverException;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClearedEvent;
import info.novatec.testit.webtester.events.pagefragments.FormSubmittedEvent;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class GenericElementIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/genericElement.html";
    }

    /* submit */

    @Test
    public void genericElementsCanBeSubmitted() {
        page.form().submit();
        assertThat(currentPageTitle()).isEqualTo("Target Page");
    }

    @Test
    public void submittingFiresEvent() {
        EventCaptor.capture(eventSystem(), FormSubmittedEvent.class)
            .execute(() -> page.form().submit())
            .assertEventWasFired();
    }

    @Test(expected = WebDriverException.class)
    public void submittingANonFormGenericElementThrowsWebDriverException() {
        page.button().submit();
    }

    /* sending keys */

    @Test
    public void keysCanBeSend() {
        GenericElement field = page.textField();
        field.sendKeys(" xur");
        assertThat(field.getAttribute("value")).contains("foo bar xur");
    }

    /* clear */

    @Test
    public void genericElementsCanBeCleared() {
        GenericElement field = page.textField();
        field.clear();
        assertThat(field.getAttribute("value")).contains("");
    }

    @Test
    public void clearingFiresEvent() {
        EventCaptor.capture(eventSystem(), ClearedEvent.class)
            .execute(() -> page.textField().clear())
            .assertEventWasFired();
    }

    /* casting to other page object class */

    @Test
    public final void genericElementsCanBeCastAsOtherPageFragments() {
        Button asButton = page.button().as(Button.class);
        assertPageFragmentCanBeInitialized(asButton);
    }

    @Test(expected = MappingException.class)
    public final void castingToOtherPageFragmentClassRequiresMappingCompliance() {
        TextField asTextField = page.button().as(TextField.class);
        assertPageFragmentCanBeInitialized(asTextField);
    }

    /* utilities */

    public interface TestPage extends Page {

        @IdentifyUsing("#button")
        GenericElement button();

        @IdentifyUsing("#textField")
        GenericElement textField();

        @IdentifyUsing("#form")
        GenericElement form();

    }

}
