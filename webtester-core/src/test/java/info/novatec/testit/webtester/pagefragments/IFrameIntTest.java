package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class IFrameIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/iframe.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* source path */

    @Test
    public void sourcePathCanBeRead() {
        String expected = getUrlFor("html/pagefragments/_frameContent.html");
        assertThat(page.iframe().getSourcePath()).contains(expected);
    }

    /* mapping */

    @Test
    public void iframeTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.iframe());
    }

    @Test(expected = MappingException.class)
    public void nonIFrameTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noIFrame());
    }

    /* utilities */

    private interface TestPage extends Page {

        @IdentifyUsing("#iframe")
        IFrame iframe();
        @IdentifyUsing("#noIFrame")
        IFrame noIFrame();

    }

}
