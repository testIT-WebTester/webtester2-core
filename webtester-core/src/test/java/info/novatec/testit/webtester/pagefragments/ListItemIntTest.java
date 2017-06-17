package info.novatec.testit.webtester.pagefragments;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class ListItemIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/listItem.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* mapping */

    @Test
    public void listItemTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.listItem());
    }

    @Test(expected = MappingException.class)
    public void nonListItemTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noListItem());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#listItem")
        ListItem listItem();
        @IdentifyUsing("#noListItem")
        ListItem noListItem();

    }

}
