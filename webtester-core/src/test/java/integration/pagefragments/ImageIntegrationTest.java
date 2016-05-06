package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.pagefragments.Image;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class ImageIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/image.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* attributes */

    @Test
    public void fileNameCanBeRead() {
        assertThat(page.image().getFileName()).contains("_image.png");
    }

    @Test
    public void sourcePathCanBeRead() {
        String expected = getUrlFor("html/pagefragments/_image.png");
        assertThat(page.image().getSourcePath()).contains(expected);
    }

    @Test
    public void altTextCanBeRead() {
        assertThat(page.image().getAlternateText()).contains("some image");
    }

    @Test
    public void widthCanBeRead() {
        assertThat(page.image().getWidth()).isEqualTo(42);
    }

    @Test
    public void heightCanBeRead() {
        assertThat(page.image().getHeight()).isEqualTo(43);
    }

    /* click */

    @Test
    public void imagesCanBeClicked() {
        page.image().click();
        assertThat(currentPageTitle()).isEqualTo("Target Page");
    }

    @Test
    public void clickingImagesFiresEvents() {
        EventCaptor.capture(eventSystem(), ClickedEvent.class)
            .execute(()-> page.image().click())
            .assertEventWasFired();
    }

    /* mapping */

    @Test
    public void imageTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.image());
    }

    @Test(expected = MappingException.class)
    public void nonImageTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noImage());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#image")
        Image image();

        @IdentifyUsing("#noImage")
        Image noImage();

    }

}
