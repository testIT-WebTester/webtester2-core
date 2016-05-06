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
 * This {@link Event event} occurs whenever a text is set.
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
public class TextSetEvent extends AbstractPageFragmentEvent {

    private final String before;
    private final String after;

    public TextSetEvent(PageFragment fragment, String before, String after) {
        super(fragment);
        this.before = before;
        this.after = after;
    }

    @Override
    public String describe() {
        return "text of '" + getPageFragmentName() + "' was set to '" + after + "' (was '" + before + "')";
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<TextSetEvent> {

        private String before;
        private String after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<TextSetEvent> setBeforeData(WebElement webElement) {
            this.before = webElement.getAttribute("value");
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<TextSetEvent> setAfterData(WebElement webElement) {
            this.after = webElement.getAttribute("value");
            return this;
        }

        @Override
        protected TextSetEvent buildWith(PageFragment fragment) {
            return new TextSetEvent(fragment, before, after);
        }

    }

}
