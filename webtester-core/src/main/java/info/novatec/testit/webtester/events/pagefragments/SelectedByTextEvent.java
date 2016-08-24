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
 * This {@link Event event} occurs whenever a selection is made by text.
 * <p>
 * It contains the selected text as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see SingleSelect
 * @since 2.0
 */
@Getter
public class SelectedByTextEvent extends AbstractPageFragmentEvent {

    private final String text;

    public SelectedByTextEvent(PageFragment fragment, String text) {
        super(fragment);
        this.text = text;
    }

    @Override
    public String describe() {
        return "selected text: " + text + " of " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<SelectedByTextEvent> {

        private String text;

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectedByTextEvent> setAfterData(WebElement webElement) {
            this.text = new EnhancedSelect(webElement).getFirstSelectedOption().getText();
            return this;
        }

        @Override
        protected SelectedByTextEvent buildWith(PageFragment fragment) {
            return new SelectedByTextEvent(fragment, text);
        }

    }

}
