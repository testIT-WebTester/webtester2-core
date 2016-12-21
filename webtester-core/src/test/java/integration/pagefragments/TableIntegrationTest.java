package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.Table;
import info.novatec.testit.webtester.pagefragments.TableRow;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class TableIntegrationTest extends BaseIntegrationTest {

    private static final String HEADER_ROW = "Header 1 Header 2 Header 3";
    private static final String BODY_ROW1 = "R1C1 R1C2 R1C3";
    private static final String BODY_ROW2 = "R2C1 R2C2 R2C3";
    private static final String BODY_ROW3 = "R3C1 R3C2 R3C3";
    private static final String FOOTER_ROW = "Footer 1 Footer 2 Footer 3";

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/table.html";
    }

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /* all rows */

    @Test
    public void allRowsOfClassicTablesCanBeRead() {
        List<TableRow> rows = page.classicTable().allRows();
        assertThat(rows).hasSize(5);
    }

    @Test
    public void allRowsOfClassicTablesCanBeStreamed() {
        Stream<TableRow> rows = page.classicTable().streamAllRows();
        assertThat(rows).hasSize(5);
    }

    @Test
    public void allRowsOfClassicTablesAreReadCorrectly() {
        Stream<String> rows = page.classicTable().streamAllRows().map(TableRow::getVisibleText);
        assertThat(rows).containsExactly(HEADER_ROW, BODY_ROW1, BODY_ROW2, BODY_ROW3, FOOTER_ROW);
    }

    @Test
    public void totalNumberOfRowsForClassicTablesCanBeRead() {
        assertThat(page.classicTable().getTotalNumberOfRows()).isEqualTo(5);
    }

    @Test
    public void rowsOfClassicTablesCanBeReadByTheirIndex() {
        TableRow row = page.classicTable().getRow(2);
        assertThat(row.getVisibleText()).isEqualTo(BODY_ROW2);
    }

    @Test
    public void allRowsOfModernTablesCanBeRead() {
        List<TableRow> rows = page.modernTable().allRows();
        assertThat(rows).hasSize(5);
    }

    @Test
    public void allRowsOfModernTablesCanBeStreamed() {
        Stream<TableRow> rows = page.modernTable().streamAllRows();
        assertThat(rows).hasSize(5);
    }

    @Test
    public void allRowsOfModernTablesAreReadCorrectly() {
        Stream<String> rows = page.modernTable().streamAllRows().map(TableRow::getVisibleText);
        assertThat(rows).containsExactly(HEADER_ROW, BODY_ROW1, BODY_ROW2, BODY_ROW3, FOOTER_ROW);
    }

    @Test
    public void allRowsOfEmptyTablesCanBeRead() {
        List<TableRow> rows = page.emptyTable().allRows();
        assertThat(rows).isEmpty();
    }

    @Test
    public void totalNumberOfRowsForModernTablesCanBeRead() {
        assertThat(page.modernTable().getTotalNumberOfRows()).isEqualTo(5);
    }

    @Test
    public void rowsOfModernTablesCanBeReadByTheirIndex() {
        TableRow row = page.modernTable().getRow(2);
        assertThat(row.getVisibleText()).isEqualTo(BODY_ROW2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readingOutOfBoundsRowThrowsException() {
        page.modernTable().getRow(42);
    }

    /* header rows */

    @Test
    public void headerRowsOfModernTablesCanBeRead() {
        List<TableRow> rows = page.modernTable().headerRows();
        assertThat(rows).hasSize(1);
    }

    @Test
    public void headerRowsOfModernTablesCanBeStreamed() {
        Stream<TableRow> rows = page.modernTable().streamHeaderRows();
        assertThat(rows).hasSize(1);
    }

    @Test
    public void headerRowsOfModernTablesAreReadCorrectly() {
        Stream<String> rows = page.modernTable().streamHeaderRows().map(TableRow::getVisibleText);
        assertThat(rows).containsExactly(HEADER_ROW);
    }

    @Test
    public void headerRowsOnlyWorkWithModernTables() {
        assertThat(page.classicTable().headerRows()).isEmpty();
        assertThat(page.classicTable().streamHeaderRows()).isEmpty();
    }

    @Test
    public void numberOfHeaderRowsCanBeRead() {
        assertThat(page.modernTable().getNumberOfHeaderRows()).isEqualTo(1);
    }

    @Test
    public void headerRowsCanBeReadByTheirIndex() {
        TableRow row = page.modernTable().getHeaderRow(0);
        assertThat(row.getVisibleText()).isEqualTo(HEADER_ROW);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readingOutOfBoundsHeaderRowThrowsException() {
        page.modernTable().getHeaderRow(42);
    }

    /* body rows */

    @Test
    public void bodyRowsOfModernTablesCanBeRead() {
        List<TableRow> rows = page.modernTable().bodyRows();
        assertThat(rows).hasSize(3);
    }

    @Test
    public void bodyRowsOfModernTablesCanBeStreamed() {
        Stream<TableRow> rows = page.modernTable().streamBodyRows();
        assertThat(rows).hasSize(3);
    }

    @Test
    public void bodyRowsOfModernTablesAreReadCorrectly() {
        Stream<String> rows = page.modernTable().streamBodyRows().map(TableRow::getVisibleText);
        assertThat(rows).containsExactly(BODY_ROW1, BODY_ROW2, BODY_ROW3);
    }

    @Test
    public void numberOfBodyRowsCanBeRead() {
        assertThat(page.modernTable().getNumberOfBodyRows()).isEqualTo(3);
    }

    @Test
    public void bodyRowsCanBeReadByTheirIndex() {
        TableRow row = page.modernTable().getBodyRow(1);
        assertThat(row.getVisibleText()).isEqualTo(BODY_ROW2);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readingOutOfBoundsBodyRowThrowsException() {
        page.modernTable().getBodyRow(42);
    }

    /* footer rows */

    @Test
    public void footerRowsOfModernTablesCanBeRead() {
        List<TableRow> rows = page.modernTable().footerRows();
        assertThat(rows).hasSize(1);
    }

    @Test
    public void footerRowsOfModernTablesCanBeStreamed() {
        Stream<TableRow> rows = page.modernTable().streamFooterRows();
        assertThat(rows).hasSize(1);
    }

    @Test
    public void footerRowsOfModernTablesAreReadCorrectly() {
        Stream<String> rows = page.modernTable().streamFooterRows().map(TableRow::getVisibleText);
        assertThat(rows).containsExactly(FOOTER_ROW);
    }

    @Test
    public void footerRowsOnlyWorkWithModernTables() {
        assertThat(page.classicTable().footerRows()).isEmpty();
        assertThat(page.classicTable().streamFooterRows()).isEmpty();
    }

    @Test
    public void numberOfFooterRowsCanBeRead() {
        assertThat(page.modernTable().getNumberOfFooterRows()).isEqualTo(1);
    }

    @Test
    public void footerRowsCanBeReadByTheirIndex() {
        TableRow row = page.modernTable().getFooterRow(0);
        assertThat(row.getVisibleText()).isEqualTo(FOOTER_ROW);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void readingOutOfBoundsFooterRowThrowsException() {
        page.modernTable().getFooterRow(42);
    }

    /* mapping */

    @Test
    public void tableTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.modernTable());
        assertPageFragmentCanBeInitialized(page.classicTable());
    }

    @Test(expected = MappingException.class)
    public void nonTableTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noTable());
    }

    public interface TestPage extends Page {

        @IdentifyUsing("#modernTable")
        Table modernTable();

        @IdentifyUsing("#classicTable")
        Table classicTable();

        @IdentifyUsing("#emptyTable")
        Table emptyTable();

        @IdentifyUsing("#emptyRowTable")
        Table emptyRowTable();

        @IdentifyUsing("#noTable")
        Table noTable();

    }

}
