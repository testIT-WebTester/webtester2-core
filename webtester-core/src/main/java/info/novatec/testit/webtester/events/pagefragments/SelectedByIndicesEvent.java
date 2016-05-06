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
 * This {@link Event event} occurs whenever a selection is made by multiple indices.
 * <p>
 * It contains the selected indices as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see MultiSelect
 * @since 2.0
 */
@Getter
public class SelectedByIndicesEvent extends AbstractPageFragmentEvent {

    private final List<Integer> indices;

    public SelectedByIndicesEvent(PageFragment fragment, Collection<Integer> indices) {
        super(fragment);
        this.indices = Collections.unmodifiableList(new LinkedList<>(indices));
    }

    @Override
    public String describe() {
        return "selected indices: " + indices + " of " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<SelectedByIndicesEvent> {

        private List<Integer> before;
        private List<Integer> after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectedByIndicesEvent> setBeforeData(WebElement webElement) {
            before = new Select(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getAttribute("index")))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectedByIndicesEvent> setAfterData(WebElement webElement) {
            after = new Select(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getAttribute("index")))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
            return this;
        }

        @Override
        protected SelectedByIndicesEvent buildWith(PageFragment fragment) {
            List<Integer> values = new LinkedList<>(after);
            values.removeAll(before);
            return new SelectedByIndicesEvent(fragment, values);
        }

    }

}
