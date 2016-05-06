package info.novatec.testit.webtester.events.pagefragments;

import org.openqa.selenium.WebElement;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.PasswordField;
import info.novatec.testit.webtester.pagefragments.TextArea;
import info.novatec.testit.webtester.pagefragments.TextField;


/**
 * This {@link Event event} occurs whenever a text is appended.
 * <p>
 * It contains the before and after texts as properties.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see TextField
 * @see TextArea
 * @see PasswordField
 * @since 2.0
 */
@Getter
public class TextAppendedEvent extends AbstractPageFragmentEvent {

    private final String before;
    private final String after;

    public TextAppendedEvent(PageFragment fragment, String before, String after) {
        super(fragment);
        this.before = before;
        this.after = after;
    }

    @Override
    public String describe() {
        return "text of '" + getPageFragmentName() + "' was appended to '" + after + "' (was '" + before + "')";
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<TextAppendedEvent> {

        private String before;
        private String after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<TextAppendedEvent> setBeforeData(WebElement webElement) {
            this.before = webElement.getAttribute("value");
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<TextAppendedEvent> setAfterData(WebElement webElement) {
            this.after = webElement.getAttribute("value");
            return this;
        }

        @Override
        protected TextAppendedEvent buildWith(PageFragment fragment) {
            return new TextAppendedEvent(fragment, before, after);
        }

    }

}
