package info.novatec.testit.webtester.pagefragments;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.utils.EnhancedSelect;


@Mapping(tag = "select")
public interface GenericSelect<T extends GenericSelect<T>> extends PageFragment {

    /**
     * Returns the texts of all options in order.
     * <p>
     * If there are no options, an empty list is returned.
     *
     * @return the texts of all the options
     * @see Select#getOptions()
     * @see WebElement#getText()
     * @see GenericSelect
     * @since 2.0
     */
    default List<String> getOptionTexts() {
        return streamOptionTexts().collect(Collectors.toList());
    }

    /**
     * Streams the texts of all options in order.
     * <p>
     * If there are no options, the stream will be empty.
     *
     * @return the texts of all the options
     * @see Select#getOptions()
     * @see WebElement#getAttribute(String)
     * @see GenericSelect
     * @since 2.0
     */
    default Stream<String> streamOptionTexts() {
        return new EnhancedSelect(webElement()).getOptions().stream().map(WebElement::getText);
    }

    /**
     * Returns the values of all options in order.
     * <p>
     * If there are no options, an empty list is returned.
     *
     * @return the values of all the options
     * @see Select#getOptions()
     * @see WebElement#getAttribute(String)
     * @see GenericSelect
     * @since 2.0
     */
    default List<String> getOptionValues() {
        return streamOptionValues().collect(Collectors.toList());
    }

    /**
     * Streams the values of all options in order.
     * <p>
     * If there are no options, the stream will be empty.
     *
     * @return the values of all the options
     * @see Select#getOptions()
     * @see WebElement#getAttribute(String)
     * @see GenericSelect
     * @since 2.0
     */
    default Stream<String> streamOptionValues() {
        return new EnhancedSelect(webElement()).getOptions().stream().map(option -> option.getAttribute("value"));
    }

    /**
     * Returns the number of selectable options.
     *
     * @return the number of options
     * @see Select#getOptions()
     * @see Collection#size()
     * @since 2.0
     */
    default Integer getOptionCount() {
        return new EnhancedSelect(webElement()).getOptions().size();
    }

}
