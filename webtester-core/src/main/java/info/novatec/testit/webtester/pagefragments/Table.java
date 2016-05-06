package info.novatec.testit.webtester.pagefragments;

import java.util.List;
import java.util.stream.Stream;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.identification.producers.XPath;


@Mapping(tag = "table")
public interface Table extends PageFragment {

    /**
     * Returns a list of this {@link Table tables's} {@link TableRow rows}.
     * <p>
     * These are only the direct rows of this table. Nested tables will not be resolved!
     *
     * @return the rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    @IdentifyUsing(value = "./tr|./*/tr", how = XPath.class)
    List<TableRow> allRows();

    /**
     * Returns a stream of this {@link Table tables's} {@link TableRow rows}.
     * <p>
     * This is equivalent to calling <code>{@link #allRows()}.stream()</code>.
     *
     * @return the rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default Stream<TableRow> streamAllRows() {
        return allRows().stream();
    }

    /**
     * Returns a list of this {@link Table tables's} header {@link TableRow rows}.
     * <p>
     * These are only the direct header rows of this table. Nested tables will not be resolved!
     * <p>
     * <b>Note:</b> Calling this method will only return rows within a <code>&lt;thead&gt;</code> tag.
     *
     * @return the header rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    @IdentifyUsing(value = "./thead/tr", how = XPath.class)
    List<TableRow> headerRows();

    /**
     * Returns a stream of this {@link Table tables's} header {@link TableRow rows}.
     * <p>
     * This is equivalent to calling <code>{@link #headerRows()}.stream()</code>.
     *
     * @return the header rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default Stream<TableRow> streamHeaderRows() {
        return headerRows().stream();
    }

    /**
     * Returns a list of this {@link Table tables's} body {@link TableRow rows}.
     * <p>
     * These are only the direct body rows of this table. Nested tables will not be resolved!
     * <p>
     * <b>Note:</b> Calling this method will only return rows within a <code>&lt;tbody&gt;</code> tag.
     *
     * @return the body rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    @IdentifyUsing(value = "./tbody/tr", how = XPath.class)
    List<TableRow> bodyRows();

    /**
     * Returns a stream of this {@link Table tables's} body {@link TableRow rows}.
     * <p>
     * This is equivalent to calling <code>{@link #bodyRows()}.stream()</code>.
     *
     * @return the body rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default Stream<TableRow> streamBodyRows() {
        return bodyRows().stream();
    }

    /**
     * Returns a list of this {@link Table tables's} footer {@link TableRow rows}.
     * <p>
     * These are only the direct footer rows of this table. Nested tables will not be resolved!
     * <p>
     * <b>Note:</b> Calling this method will only return rows within a <code>&lt;tfoot&gt;</code> tag.
     *
     * @return the footer rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    @IdentifyUsing(value = "./tfoot/tr", how = XPath.class)
    List<TableRow> footerRows();

    /**
     * Returns a stream of this {@link Table tables's} footer {@link TableRow rows}.
     * <p>
     * This is equivalent to calling <code>{@link #footerRows()}.stream()</code>.
     *
     * @return the footer rows of this table
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default Stream<TableRow> streamFooterRows() {
        return footerRows().stream();
    }

    /**
     * Returns the total number of {@link TableRow rows} of this {@link Table tables}. Regardless if they are header, body
     * or
     * footer rows.
     *
     * @return the number of rows of this table
     * @see #allRows()
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default long getTotalNumberOfRows() {
        return allRows().size();
    }

    /**
     * Returns the total number of header {@link TableRow rows} of this {@link Table tables}.
     *
     * @return the number of header rows of this table
     * @see #headerRows()
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default long getNumberOfHeaderRows() {
        return headerRows().size();
    }

    /**
     * Returns the total number of body {@link TableRow rows} of this {@link Table tables}.
     *
     * @return the number of body rows of this table
     * @see #bodyRows()
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default long getNumberOfBodyRows() {
        return bodyRows().size();
    }

    /**
     * Returns the total number of footer {@link TableRow rows} of this {@link Table tables}.
     *
     * @return the number of footer rows of this table
     * @see #footerRows()
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default long getNumberOfFooterRows() {
        return footerRows().size();
    }

    /**
     * Returns the {@link TableRow row} for the given index. The index starts with <code>0</code> for the first row.
     *
     * @param index the zero-based index of the row to return
     * @return the row
     * @throws IndexOutOfBoundsException in case there is no row for the given index
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default TableRow getRow(int index) {
        return allRows().get(index);
    }

    /**
     * Returns the header {@link TableRow row} for the given index. The index starts with <code>0</code> for the first
     * header row.
     *
     * @param index the zero-based index of the header row to return
     * @return the header row
     * @throws IndexOutOfBoundsException in case there is no header row for the given index
     * @see #headerRows()
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default TableRow getHeaderRow(int index) {
        return headerRows().get(index);
    }

    /**
     * Returns the body {@link TableRow row} for the given index. The index starts with <code>0</code> for the first body
     * row.
     *
     * @param index the zero-based index of the body row to return
     * @return the body row
     * @throws IndexOutOfBoundsException in case there is no body row for the given index
     * @see #bodyRows()
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default TableRow getBodyRow(int index) {
        return bodyRows().get(index);
    }

    /**
     * Returns the footer {@link TableRow row} for the given index. The index starts with <code>0</code> for the first
     * footer row.
     *
     * @param index the zero-based index of the footer row to return
     * @return the footer row
     * @throws IndexOutOfBoundsException in case there is no footer row for the given index
     * @see #footerRows()
     * @see Table
     * @see TableRow
     * @since 2.0
     */
    default TableRow getFooterRow(int index) {
        return footerRows().get(index);
    }

}
