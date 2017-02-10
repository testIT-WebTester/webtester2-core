package info.novatec.testit.webtester.pagefragments;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class ParagraphIntTest extends BaseIntTest {

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
