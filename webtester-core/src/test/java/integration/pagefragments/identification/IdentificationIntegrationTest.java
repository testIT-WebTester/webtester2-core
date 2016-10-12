package integration.pagefragments.identification;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.Link;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.producers.ClassName;
import info.novatec.testit.webtester.pagefragments.identification.producers.CssSelector;
import info.novatec.testit.webtester.pagefragments.identification.producers.Id;
import info.novatec.testit.webtester.pagefragments.identification.producers.IdEndsWith;
import info.novatec.testit.webtester.pagefragments.identification.producers.IdStartsWith;
import info.novatec.testit.webtester.pagefragments.identification.producers.LinkText;
import info.novatec.testit.webtester.pagefragments.identification.producers.Name;
import info.novatec.testit.webtester.pagefragments.identification.producers.PartialLinkText;
import info.novatec.testit.webtester.pagefragments.identification.producers.TagName;
import info.novatec.testit.webtester.pagefragments.identification.producers.XPath;
import info.novatec.testit.webtester.pages.Page;


public class IdentificationIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/identification/by-producers.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    @Test
    public void byId() {
        assertPageFragmentCanBeInitialized(page.byId());
    }

    @Test
    public void byIdStartsWith() {
        assertPageFragmentCanBeInitialized(page.byIdStartsWith());
    }

    @Test
    public void byIdEndsWith() {
        assertPageFragmentCanBeInitialized(page.byIdEndsWith());
    }

    @Test
    public void byXpath() {
        assertPageFragmentCanBeInitialized(page.byXpath());
    }

    @Test
    public void byCss() {
        assertPageFragmentCanBeInitialized(page.byCss());
    }

    @Test
    public void byClass() {
        assertPageFragmentCanBeInitialized(page.byClass());
    }

    @Test
    public void byName() {
        assertPageFragmentCanBeInitialized(page.byName());
    }

    @Test
    public void byLinkText() {
        assertPageFragmentCanBeInitialized(page.byLinkText());
    }

    @Test
    public void byPartialLinkText() {
        assertPageFragmentCanBeInitialized(page.byPartialLinkText());
    }

    @Test
    public void byTagName() {
        assertPageFragmentCanBeInitialized(page.byTagName());
    }

    public interface TestPage extends Page {

        @IdentifyUsing(value = "id", how = Id.class)
        TextField byId();
        @IdentifyUsing(value = "prefix:", how = IdStartsWith.class)
        TextField byIdStartsWith();
        @IdentifyUsing(value = ":suffix", how = IdEndsWith.class)
        TextField byIdEndsWith();
        @IdentifyUsing(value = "//div[@id='xpath']/input", how = XPath.class)
        TextField byXpath();
        @IdentifyUsing(value = "div#css input", how = CssSelector.class)
        TextField byCss();
        @IdentifyUsing(value = "class", how = ClassName.class)
        TextField byClass();
        @IdentifyUsing(value = "name", how = Name.class)
        TextField byName();
        @IdentifyUsing(value = "link text", how = LinkText.class)
        Link byLinkText();
        @IdentifyUsing(value = "partial link", how = PartialLinkText.class)
        Link byPartialLinkText();
        @IdentifyUsing(value = "tagname", how = TagName.class)
        PageFragment byTagName();

    }

}
