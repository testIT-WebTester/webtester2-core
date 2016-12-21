package info.novatec.testit.webtester.events.pagefragments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.utils.EnhancedSelect;


/**
 * This {@link Event event} occurs whenever a deselection is made by multiple texts.
 * <p>
 * It contains the deselected texts as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see MultiSelect
 * @since 2.0
 */
@Getter
public class DeselectedByTextsEvent extends AbstractPageFragmentEvent {

    private final List<String> texts;

    public DeselectedByTextsEvent(PageFragment fragment, Collection<String> texts) {
        super(fragment);
        this.texts = Collections.unmodifiableList(new ArrayList<>(texts));
    }

    @Override
    public String describe() {
        return "deselected texts: " + texts + " of " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<DeselectedByTextsEvent> {

        private List<String> before;
        private List<String> after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<DeselectedByTextsEvent> setBeforeData(WebElement webElement) {
            before = new EnhancedSelect(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getText()))
                .collect(Collectors.toList());
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<DeselectedByTextsEvent> setAfterData(WebElement webElement) {
            after = new EnhancedSelect(webElement).getAllSelectedOptions()
                .stream()
                .map(element -> StringUtils.defaultString(element.getText()))
                .collect(Collectors.toList());
            return this;
        }

        @Override
        protected DeselectedByTextsEvent buildWith(PageFragment fragment) {
            List<String> values = new ArrayList<>(before);
            values.removeAll(after);
            return new DeselectedByTextsEvent(fragment, values);
        }

    }

}
