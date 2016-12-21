package integration.pagefragments;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.Span;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class SpanIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/span.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* mapping */

    @Test
    public void spanTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.span());
    }

    @Test(expected = MappingException.class)
    public void nonSpanTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noSpan());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#span")
        Span span();
        @IdentifyUsing("#noSpan")
        Span noSpan();

    }

}
