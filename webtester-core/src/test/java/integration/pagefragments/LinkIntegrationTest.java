package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pagefragments.Link;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class LinkIntegrationTest extends BaseIntegrationTest {

    LinkTestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/link.html";
    }

    @Before
    public void initPage() {
        page = create(LinkTestPage.class);
    }

    /* attributes */

    @Test
    public void destinationCanBeRead() {
        String expected = getUrlFor("html/pagefragments/_targetPage.html");
        assertThat(page.link().getDestination()).contains(expected);
    }

    @Test
    public void targetCanBeRead() {
        assertThat(page.link().getTarget()).contains("_self");
    }

    /* click */

    @Test
    public void linksCanBeClicked() {
        page.link().click();
        assertThat(currentPageTitle()).isEqualTo("Target Page");
    }

    @Test
    public void clickingLinksFiresEvents() {
        EventCaptor.capture(eventSystem(), ClickedEvent.class)
            .execute(()-> page.link().click())
            .assertEventWasFired();
    }

    /* mapping */

    @Test
    public void linkTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.link());
    }

    @Test(expected = MappingException.class)
    public void nonLinkTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noLink());
    }

    public interface LinkTestPage extends Page {

        @IdentifyUsing("#link")
        Link link();

        @IdentifyUsing("#noLink")
        Link noLink();

    }

}
