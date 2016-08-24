package info.novatec.testit.webtester.events.pagefragments;

import org.openqa.selenium.WebElement;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.pagefragments.utils.EnhancedSelect;


/**
 * This {@link Event event} occurs whenever a selection is made by value.
 * <p>
 * It contains the selected value as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see SingleSelect
 * @since 2.0
 */
@Getter
public class SelectedByValueEvent extends AbstractPageFragmentEvent {

    private final String value;

    public SelectedByValueEvent(PageFragment fragment, String value) {
        super(fragment);
        this.value = value;
    }

    @Override
    public String describe() {
        return "selected value: " + value + " of " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<SelectedByValueEvent> {

        private String value;

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectedByValueEvent> setAfterData(WebElement webElement) {
            this.value = new EnhancedSelect(webElement).getFirstSelectedOption().getAttribute("value");
            return this;
        }

        @Override
        protected SelectedByValueEvent buildWith(PageFragment fragment) {
            return new SelectedByValueEvent(fragment, value);
        }

    }

}
