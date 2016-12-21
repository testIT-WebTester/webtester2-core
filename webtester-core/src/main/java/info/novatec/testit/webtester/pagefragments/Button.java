package info.novatec.testit.webtester.pagefragments;

import java.util.Optional;

import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.traits.Clickable;


@Mapping(tag = "button")
@Mapping(tag = "input", attribute = "type", values = { "submit", "reset", "button" })
public interface Button extends PageFragment, Clickable<Button> {

    /**
     * Returns the value attribute of this button as an optional string.
     * This returns an empty optional in case the button has no 'value' attribute (i.e. it is a
     * <code>&lt;button&gt;</code>).
     *
     * @return the optional value
     * @see Attribute
     * @see Optional
     * @since 2.0
     */
    @Attribute("value")
    Optional<String> getValue();

    /**
     * Returns the label of the button.
     * <p>
     * In case the button is a <code>&lt;button&gt;</code> it's visible text is used. In case it's a
     * <code>&lt;input&gt;</code> the attribute <code>value</code> is used.
     * <p>
     * This method never returns <code>null</code>. In case no label could be computed, an empty string is returned.
     *
     * @return the label
     * @see #getValue()
     * @see PageFragment#getVisibleText()
     * @see Optional
     * @since 2.0
     */
    @Mark(As.READ)
    default String getLabel() {
        if ("button".equalsIgnoreCase(getTagName())) {
            return getVisibleText();
        }
        return getValue().orElse("");
    }

}
