package integration.pagefragments;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.Paragraph;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class ParagraphIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/paragraph.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* mapping */

    @Test
    public void paragraphTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.paragraph());
    }

    @Test(expected = MappingException.class)
    public void nonParagraphTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noParagraph());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#paragraph")
        Paragraph paragraph();
        @IdentifyUsing("#noParagraph")
        Paragraph noParagraph();

    }

}
