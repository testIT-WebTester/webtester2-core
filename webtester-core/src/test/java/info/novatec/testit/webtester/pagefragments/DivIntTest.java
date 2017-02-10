package info.novatec.testit.webtester.pagefragments;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class DivIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/div.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* mapping */

    @Test
    public void divTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.div());
    }

    @Test(expected = MappingException.class)
    public void nonDivTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noDiv());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#div")
        Div div();
        @IdentifyUsing("#noDiv")
        Div noDiv();

    }

}
