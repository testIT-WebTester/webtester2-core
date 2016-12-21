package info.novatec.testit.webtester.pagefragments;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.ClearedEvent;
import info.novatec.testit.webtester.events.pagefragments.NumberSetEvent;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.As;
import info.novatec.testit.webtester.pagefragments.annotations.Attribute;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;


@Mapping(tag = "input", attribute = "type", values = "number")
public interface NumberField extends PageFragment {

    @Action
    @Mark(As.USED)
    @Produces(NumberSetEvent.class)
    default NumberField setValue(long value) {
        WebElement webElement = webElement();
        webElement.clear();
        webElement.sendKeys(String.valueOf(value));
        return this;
    }

    @Action
    @Mark(As.USED)
    @Produces(ClearedEvent.class)
    default NumberField clear() {
        webElement().clear();
        return this;
    }

    @Mark(As.READ)
    @Attribute("value")
    Optional<Long> getValue();

    @Override
    @Mark(As.READ)
    default String getVisibleText() {
        return getValue().map(String::valueOf).orElse(StringUtils.EMPTY);
    }

}
