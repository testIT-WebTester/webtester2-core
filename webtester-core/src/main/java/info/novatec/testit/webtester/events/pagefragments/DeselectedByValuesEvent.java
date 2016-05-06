package info.novatec.testit.webtester.events.pagefragments;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever a deselection is made by multiple values.
 * <p>
 * It contains the deselected values as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see MultiSelect
 * @since 2.0
 */
@Getter
public class DeselectedByValuesEvent extends AbstractPageFragmentEvent {

    private final List<String> values;

    public DeselectedByValuesEvent(PageFragment fragment, Collection<String> values) {
        super(fragment);
        this.values = Collections.unmodifiableList(new LinkedList<>(values));
    }

    @Override
    public String describe() {
        return "deselected values: " + values + " of " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<DeselectedByValuesEvent> {

        private List<String> before;
        private List<String> after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<DeselectedByValuesEvent> setBeforeData(WebElement webElement) {
            before = new Select(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getAttribute("value")))
                .collect(Collectors.toList());
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<DeselectedByValuesEvent> setAfterData(WebElement webElement) {
            after = new Select(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getAttribute("value")))
                .collect(Collectors.toList());
            return this;
        }

        @Override
        protected DeselectedByValuesEvent buildWith(PageFragment fragment) {
            List<String> values = new LinkedList<>(before);
            values.removeAll(after);
            return new DeselectedByValuesEvent(fragment, values);
        }

    }

}
