package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class OrderedListIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/orderedList.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* list items */

    @Test
    public final void listItemsCanBeRetrievedAsList() {
        assertThat(page.listWithItems().items().size()).isEqualTo(3);
    }

    @Test
    public final void listItemsCanBeRetrievedAsStream() {
        assertThat(page.listWithItems().streamItems().count()).isEqualTo(3L);
    }

    /* get item */

    @Test
    public final void itemsCanBeRetrievedByIndex() {
        OrderedList listWithItems = page.listWithItems();
        assertThat(listWithItems.getItem(0).getVisibleText()).isEqualTo("ordered item 1");
        assertThat(listWithItems.getItem(1).getVisibleText()).isEqualTo("ordered item 2");
        assertThat(listWithItems.getItem(2).getVisibleText()).isEqualTo("ordered item 3");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void negativeIndicesWhenGettingItemThrowException() {
        page.listWithItems().getItem(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public final void wrongIndicesWhenGettingItemThrowException() {
        page.listWithItems().getItem(3);
    }

    /* size */

    @Test
    public final void emptyListsReturnsSizeAsZero() {
        assertThat(page.emptyList().size()).isEqualTo(0);
    }

    @Test
    public final void fullListsReturnsSize() {
        assertThat(page.listWithItems().size()).isEqualTo(3);
    }

    /* empty */

    @Test
    public final void emptyListsReturnsTrueWhenAskedIfEmpty() {
        assertThat(page.emptyList().isEmpty()).isTrue();
    }

    @Test
    public final void fullListsReturnsFalseWhenAskedIfEmpty() {
        assertThat(page.listWithItems().isEmpty()).isFalse();
    }

    /* other */

    @Test
    public final void nestedListsAreHandledCorrectly() {
        OrderedList nestedLists = page.nestedLists();
        assertThat(nestedLists.size()).isEqualTo(3);
        assertThat(nestedLists.getItem(0).getVisibleText()).isEqualTo("One\nTwo\nThree");
        assertThat(nestedLists.getItem(1).getVisibleText()).isEqualTo("Four");
        assertThat(nestedLists.getItem(2).getVisibleText()).isEqualTo("Five");
    }

    /* mapping */

    @Test
    public void orderedListTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.emptyList());
    }

    @Test(expected = MappingException.class)
    public void unorderedListTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.unorderedList());
    }

    @Test(expected = MappingException.class)
    public void nonListTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noList());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#emptyOrderedList")
        OrderedList emptyList();
        @IdentifyUsing("#fullOrderedList")
        OrderedList listWithItems();

        @IdentifyUsing("#nestedLists")
        OrderedList nestedLists();

        @IdentifyUsing("#unorderedList")
        OrderedList unorderedList();
        @IdentifyUsing("#noList")
        OrderedList noList();

    }

}
