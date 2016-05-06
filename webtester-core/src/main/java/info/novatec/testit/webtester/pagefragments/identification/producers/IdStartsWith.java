package info.novatec.testit.webtester.pagefragments.identification.producers;

import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.identification.ByProducer;


/**
 * This {@link ByProducer} produces a {@link By} using {@link By#cssSelector(String)} to partially match an ID
 * (starts-with).
 *
 * @see ByProducer
 * @since 2.0
 */
public class IdStartsWith implements ByProducer {

    private static final String ID_STARTS_WITH_PATTERN = "[id^='%s']";

    @Override
    public By createBy(String value) {
        return By.cssSelector(String.format(ID_STARTS_WITH_PATTERN, value));
    }

    @Override
    public String toString() {
        return "ID Starts With";
    }

}
