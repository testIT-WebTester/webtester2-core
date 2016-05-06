package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pagefragments.TableField;
import info.novatec.testit.webtester.pagefragments.TableRow;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class TableRowIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/tableRow.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* fields */

    @Test
    public void fieldsCanBeRead() {
        List<TableField> fields = page.tableRow().fields();
        assertThat(fields).hasSize(2);
    }

    @Test
    public void fieldsCanBeStreamed() {
        Stream<TableField> fields = page.tableRow().streamFields();
        assertThat(fields).hasSize(2);
    }

    @Test
    public void fieldsAreReadCorrectly() {
        Stream<String> fields = page.tableRow().streamFields().map(TableField::getVisibleText);
        assertThat(fields).containsExactly("R1C1", "R1C2");
    }

    @Test
    public void fieldsOfEmptyRowCanBeRead() {
        List<TableField> fields = page.emptyRow().fields();
        assertThat(fields).isEmpty();
    }

    @Test
    public void totalNumberOfFieldsCanBeRead() {
        assertThat(page.tableRow().getNumberOfFields()).isEqualTo(2);
    }

    @Test
    public void fieldsOfRowsCanBeReadByTheirIndex() {
        TableField field = page.tableRow().getField(1);
        assertThat(field.getVisibleText()).isEqualTo("R1C2");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readingOutOfBoundsFieldsThrowsException() {
        page.tableRow().getField(42);
    }

    /* mapping */

    @Test
    public void tableRowTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.tableRow());
    }

    @Test(expected = MappingException.class)
    public void nonTableRowTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noTableRow());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#tableRow")
        TableRow tableRow();

        @IdentifyUsing("#emptyRow")
        TableRow emptyRow();

        @IdentifyUsing("#noTableRow")
        TableRow noTableRow();

    }

}
