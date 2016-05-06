package info.novatec.testit.webtester.pagefragments;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;


@Mapping(tag = "th")
@Mapping(tag = "td")
public interface TableField extends PageFragment {

    /**
     * Returns whether ot not this {@link TableField} is a header field.
     * Header fields are identified by their tag name (TH).
     *
     * @return true if this is a header filed
     * @see TableField
     * @since 2.0
     */
    default boolean isHeaderField() {
        return "th".equalsIgnoreCase(getTagName());
    }

}
