package info.novatec.testit.webtester.pagefragments;

import java.util.List;
import java.util.stream.Stream;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.identification.producers.XPath;


@Mapping(tag = "tr")
public interface TableRow extends PageFragment {

    /**
     * Returns a list of this {@link TableRow row's} {@link TableField fields}.
     * <p>
     * These are only the direct children of this row. Nested tables / rows will not be resolved!
     *
     * @return the fields of this row
     * @see TableField
     * @see TableRow
     * @see Table
     * @since 2.0
     */
    @IdentifyUsing(value = "./th|./td", how = XPath.class)
    List<TableField> fields();

    /**
     * Returns a stream of this {@link TableRow row's} {@link TableField fields}.
     * <p>
     * This is equivalent to calling <code>{@link #fields()}.stream()</code>.
     *
     * @return the fields of this row
     * @see TableField
     * @see TableRow
     * @see Table
     * @since 2.0
     */
    default Stream<TableField> streamFields() {
        return fields().stream();
    }

    /**
     * Returns the {@link TableField fields} for the given index. The index starts with <code>0</code> for the first field.
     *
     * @param index the zero-based index of the field to return
     * @return the field
     * @throws IndexOutOfBoundsException in case there is no field for the given index
     * @see TableField
     * @see TableRow
     * @see Table
     * @since 2.0
     */
    default TableField getField(int index) {
        return fields().get(index);
    }

    /**
     * Returns the number of {@link TableField fields} of this {@link TableRow row}.
     *
     * @return the number of fields of this row
     * @see TableField
     * @see TableRow
     * @see Table
     * @since 2.0
     */
    default long getNumberOfFields() {
        return fields().size();
    }

}
