package info.novatec.testit.webtester.events.pagefragments;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;

import lombok.Getter;

import info.novatec.testit.webtester.events.Event;
import info.novatec.testit.webtester.events.EventListener;
import info.novatec.testit.webtester.events.EventSystem;
import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.pagefragments.NumberField;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This {@link Event event} occurs whenever a number field's value is set.
 * <p>
 * It contains the before and after values as properties.
 *
 * @see Event
 * @see EventListener
 * @see EventSystem
 * @see NumberField
 * @since 2.0
 */
@Getter
public class NumberSetEvent extends AbstractPageFragmentEvent {

    private final Optional<Long> before;
    private final Optional<Long> after;

    public NumberSetEvent(PageFragment fragment, Long before, Long after) {
        super(fragment);
        this.before = Optional.ofNullable(before);
        this.after = Optional.ofNullable(after);
    }

    @Override
    public String describe() {
        return "number of '" + getPageFragmentName() + "' was set to '" + after + "' (was '" + before + "')";
    }

    public static class Builder extends AbstractPageFragmentEventBuilder<NumberSetEvent> {

        private Long before;
        private Long after;

        @Override
        public boolean needsBeforeData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<NumberSetEvent> setBeforeData(WebElement webElement) {
            this.before = getNumberValue(webElement);
            return this;
        }

        @Override
        public boolean needsAfterData() {
            return true;
        }

        @Override
        public PageFragmentEventBuilder<NumberSetEvent> setAfterData(WebElement webElement) {
            this.after = getNumberValue(webElement);
            return this;
        }

        private Long getNumberValue(WebElement webElement) {
            String value = webElement.getAttribute("value");
            if (!StringUtils.isBlank(value)) {
                return Long.valueOf(value);
            }
            return null;
        }

        @Override
        protected NumberSetEvent buildWith(PageFragment fragment) {
            return new NumberSetEvent(fragment, before, after);
        }

    }

}
