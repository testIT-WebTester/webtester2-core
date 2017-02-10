package info.novatec.testit.webtester.pagefragments;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class HeadlineIntTest extends BaseIntTest {

    TestPage page;

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/headline.html";
    }

    @Test
    public void h1TypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.headline1());
    }

    @Test
    public void h2TypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.headline2());
    }

    @Test
    public void h3TypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.headline3());
    }

    @Test
    public void h4TypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.headline4());
    }

    @Test
    public void h5TypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.headline5());
    }

    @Test
    public void h6TypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.headline6());
    }

    @Test(expected = MappingException.class)
    public void nonHeadlineTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noHeadline());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#headline1")
        Headline headline1();
        @IdentifyUsing("#headline2")
        Headline headline2();
        @IdentifyUsing("#headline3")
        Headline headline3();
        @IdentifyUsing("#headline4")
        Headline headline4();
        @IdentifyUsing("#headline5")
        Headline headline5();
        @IdentifyUsing("#headline6")
        Headline headline6();

        @IdentifyUsing("#noHeadline")
        Headline noHeadline();

    }

}
