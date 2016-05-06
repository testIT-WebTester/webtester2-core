package info.novatec.testit.webtester.pagefragments;

import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;


@Mapping(tag = "textarea")
public interface TextArea extends GenericTextField<TextArea> {

    /**
     * Returns the <code>cols</code> attribute's value as an integer.
     *
     * @return the value of the <code>cols</code> attribute
     * @see Attribute
     * @since 2.0
     */
    @Attribute("cols")
    Integer getColumnCount();

    /**
     * Returns the <code>rows</code> attribute's value as an integer.
     *
     * @return the value of the <code>rows</code> attribute
     * @see Attribute
     * @since 2.0
     */
    @Attribute("rows")
    Integer getRowCount();

}
