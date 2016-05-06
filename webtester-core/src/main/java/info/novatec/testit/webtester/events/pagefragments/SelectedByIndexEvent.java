package info.novatec.testit.webtester.events.pagefragments;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.SingleSelect;


/**
 * This {@link Event event} occurs whenever a selection is made by index.
 * <p>
 * It contains the selected index as a property.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see SingleSelect
 * @since 2.0
 */
@Getter
public class SelectedByIndexEvent extends AbstractPageFragmentEvent {

    private final int index;

    public SelectedByIndexEvent(PageFragment fragment, int index) {
        super(fragment);
        this.index = index;
    }

    @Override
    public String describe() {
        return "selected index: " + index + " of " + getPageFragmentName();
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<SelectedByIndexEvent> {

        private Integer index;

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<SelectedByIndexEvent> setAfterData(WebElement webElement) {
            Select select = new Select(webElement);
            this.index = select.getOptions().indexOf(select.getFirstSelectedOption());
            return this;
        }

        @Override
        protected SelectedByIndexEvent buildWith(PageFragment fragment) {
            return new SelectedByIndexEvent(fragment, index);
        }

    }

}
