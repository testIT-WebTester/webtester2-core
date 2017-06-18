package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.FormSubmittedEvent;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class FormIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/form.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* submit */

    @Test
    public void formsCanBeSubmitted() {
        page.form().submit();
        assertThat(currentPageTitle()).isEqualTo("Target Page");
    }

    @Test
    public void submittingFormsFiresEvent() {
        EventCaptor.capture(eventSystem(), FormSubmittedEvent.class)
            .execute(() -> page.form().submit())
            .assertEventWasFired();
    }

    /* mapping */

    @Test
    public void formTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.form());
    }

    @Test(expected = MappingException.class)
    public void nonFormTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noForm());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#form")
        Form form();

        @IdentifyUsing("#noForm")
        Form noForm();

    }

}
