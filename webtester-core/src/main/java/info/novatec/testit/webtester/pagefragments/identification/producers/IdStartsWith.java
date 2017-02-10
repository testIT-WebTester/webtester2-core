package info.novatec.testit.webtester.pagefragments.identification.producers;

import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.identification.ByProducer;


/**
 * This {@link ByProducer} produces a {@link By} using {@link By#cssSelector(String)} to partially match an ID
 * (starts-with). The given value will be escaped using {@link CssSelectorUtils#escape(String)} in order to prevent special
 * characters from interfering with the selection.
 * <p>
 * <b>Example:</b> {@code form:table:selection[5]} will be escaped to {@code form\:table\:selection\[5\]}
 *
 * @see ByProducer
 * @since 2.0
 */
public class IdStartsWith implements ByProducer {

    private static final String ID_STARTS_WITH_PATTERN = "[id^='%s']";

    @Override
    public By createBy(String value) {
        String escapedValue = CssSelectorUtils.escape(value);
        return By.cssSelector(String.format(ID_STARTS_WITH_PATTERN, escapedValue));
    }

    @Override
    public String toString() {
        return "ID Starts With";
    }

}
