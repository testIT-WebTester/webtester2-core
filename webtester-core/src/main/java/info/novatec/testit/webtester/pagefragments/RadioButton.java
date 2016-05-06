package info.novatec.testit.webtester.pagefragments;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;


@Mapping(tag = "input", attribute = "type", values = "radio")
public interface RadioButton extends PageFragment, Selectable<RadioButton> {

    /**
     * Selects the {@link RadioButton radio button} by clicking on it and fires a {@link ClickedEvent}.
     *
     * @return the same radio button instance for fluent API use
     * @see RadioButton
     * @see ClickedEvent
     * @see WebElement#click()
     * @since 2.0
     */
    @Action
    @Mark(As.USED)
    @Produces(ClickedEvent.class)
    default RadioButton select() {
        webElement().click();
        return this;
    }

}
