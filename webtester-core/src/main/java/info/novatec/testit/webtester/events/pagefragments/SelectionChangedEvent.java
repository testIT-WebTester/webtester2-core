package info.novatec.testit.webtester.events.pagefragments;

import org.openqa.selenium.WebElement;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.Checkbox;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.RadioButton;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


/**
 * This {@link Event event} occurs whenever a selection is changed.
 * <p>
 * It contains the before and after selection values as properties.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see Selectable
 * @see Checkbox
 * @see RadioButton
 * @since 2.0
 */
@Getter
public class SelectionChangedEvent extends AbstractPageFragmentEvent {

    private final boolean before;
    private final boolean after;

    public SelectionChangedEvent(PageFragment fragment, boolean before, boolean after) {
        super(fragment);
        this.before = before;
        this.after = after;
    }

    @Override
    public String describe() {
        return "changed selection of '" + getPageFragmentName() + "' from '" + before + "' to '" + after + "')";
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<SelectionChangedEvent> {

        private Boolean before;
        private Boolean after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectionChangedEvent> setBeforeData(WebElement webElement) {
            this.before = webElement.isSelected();
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectionChangedEvent> setAfterData(WebElement webElement) {
            this.after = webElement.isSelected();
            return this;
        }

        @Override
        protected SelectionChangedEvent buildWith(PageFragment fragment) {
            return new SelectionChangedEvent(fragment, before, after);
        }

    }

}
