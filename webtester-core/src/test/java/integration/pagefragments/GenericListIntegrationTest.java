package integration.pagefragments;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pagefragments.GenericList;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


/**
 * This test only tests the mapping of generic lists.
 * <p>
 * The functionality offered by the {@link GenericList} is tests in {@link OrderedListIntegrationTest} and {@link
 * UnorderedListIntegrationTest}.
 */
public class GenericListIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/genericList.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* mapping */

    @Test
    public void orderedListTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.orderedList());
    }

    @Test
    public void unorderedListTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.unorderedList());
    }

    @Test(expected = MappingException.class)
    public void nonListTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noList());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#orderedList")
        GenericList orderedList();

        @IdentifyUsing("#unorderedList")
        GenericList unorderedList();

        @IdentifyUsing("#noList")
        GenericList noList();

    }

}
