package info.novatec.testit.webtester.pagefragments.identification;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.producers.ClassName;
import info.novatec.testit.webtester.pagefragments.identification.producers.CssSelector;
import info.novatec.testit.webtester.pagefragments.identification.producers.Id;
import info.novatec.testit.webtester.pagefragments.identification.producers.IdEndsWith;
import info.novatec.testit.webtester.pagefragments.identification.producers.IdStartsWith;
import info.novatec.testit.webtester.pagefragments.identification.producers.LinkText;
import info.novatec.testit.webtester.pagefragments.identification.producers.Name;
import info.novatec.testit.webtester.pagefragments.identification.producers.PartialLinkText;
import info.novatec.testit.webtester.pagefragments.identification.producers.TagName;
import info.novatec.testit.webtester.pagefragments.identification.producers.XPath;


/**
 * This utility class offers factory methods for Selenium {@link By} instances.
 *
 * @see By
 * @see ByProducer
 * @see IdentifyUsing
 * @since 2.0
 */
public final class ByProducers {

    private static final Map<Class<? extends ByProducer>, ByProducer> BY_PRODUCER_CACHE = new ConcurrentHashMap<>();

    public static By className(String value) {
        return createBy(ClassName.class, value);
    }

    public static By css(String value) {
        return createBy(CssSelector.class, value);
    }

    public static By id(String value) {
        return createBy(Id.class, value);
    }

    public static By idStartsWith(String value) {
        return createBy(IdStartsWith.class, value);
    }

    public static By idEndsWith(String value) {
        return createBy(IdEndsWith.class, value);
    }

    public static By name(String value) {
        return createBy(Name.class, value);
    }

    public static By linkText(String value) {
        return createBy(LinkText.class, value);
    }

    public static By partialLinkText(String value) {
        return createBy(PartialLinkText.class, value);
    }

    public static By tagName(String value) {
        return createBy(TagName.class, value);
    }

    public static By xPath(String value) {
        return createBy(XPath.class, value);
    }

    public static By createBy(IdentifyUsing identifyUsing) {
        return createBy(identifyUsing.how(), identifyUsing.value());
    }

    public static By createBy(Class<? extends ByProducer> producerClass, String value) {
        ByProducer producer = BY_PRODUCER_CACHE.get(producerClass);
        if (producer == null) {
            producer = createNewInstanceOf(producerClass);
            BY_PRODUCER_CACHE.put(producerClass, producer);
        }
        return producer.createBy(value);
    }

    private static ByProducer createNewInstanceOf(Class<? extends ByProducer> producerClass) {
        try {
            return producerClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            String message = "unable to create " + ByProducer.class.getSimpleName() + " for: " + producerClass;
            throw new UndeclaredThrowableException(e, message);
        }
    }

    private ByProducers() {
        // utility class
    }

}
