package info.novatec.testit.webtester.pagefragments.identification.producers;

import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.identification.ByProducer;


/**
 * This {@link ByProducer} produces a {@link By} using {@link By#partialLinkText(String)}.
 *
 * @see ByProducer
 * @since 2.0
 */
public class PartialLinkText implements ByProducer {

    @Override
    public By createBy(String value) {
        return By.partialLinkText(value);
    }

    @Override
    public String toString() {
        return "Partial Link Text";
    }

}
