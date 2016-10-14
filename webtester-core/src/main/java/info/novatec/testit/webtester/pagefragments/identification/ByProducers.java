package info.novatec.testit.webtester.pagefragments.identification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.By;

import lombok.experimental.UtilityClass;

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
@UtilityClass
public class ByProducers {

    private static final Map<Class<? extends ByProducer>, ByProducer> BY_PRODUCER_CACHE = new ConcurrentHashMap<>();

    /**
     * Creates a new {@link By} using {@link ClassName}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By className(String value) {
        return createBy(ClassName.class, value);
    }

    /**
     * Creates a new {@link By} using {@link CssSelector}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By css(String value) {
        return createBy(CssSelector.class, value);
    }

    /**
     * Creates a new {@link By} using {@link Id}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By id(String value) {
        return createBy(Id.class, value);
    }

    /**
     * Creates a new {@link By} using {@link IdStartsWith}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By idStartsWith(String value) {
        return createBy(IdStartsWith.class, value);
    }

    /**
     * Creates a new {@link By} using {@link IdEndsWith}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By idEndsWith(String value) {
        return createBy(IdEndsWith.class, value);
    }

    /**
     * Creates a new {@link By} using {@link Name}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By name(String value) {
        return createBy(Name.class, value);
    }

    /**
     * Creates a new {@link By} using {@link LinkText}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By linkText(String value) {
        return createBy(LinkText.class, value);
    }

    /**
     * Creates a new {@link By} using {@link PartialLinkText}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By partialLinkText(String value) {
        return createBy(PartialLinkText.class, value);
    }

    /**
     * Creates a new {@link By} using {@link TagName}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By tagName(String value) {
        return createBy(TagName.class, value);
    }

    /**
     * Creates a new {@link By} using {@link XPath}.
     *
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
    public static By xPath(String value) {
        return createBy(XPath.class, value);
    }

    /**
     * Creates a new {@link By} from evaluating the given {@link IdentifyUsing} annotation.
     *
     * @param identifyUsing the annotation to evaluate
     * @return the created {@link By}
     * @since 2.0
     */
    public static By createBy(IdentifyUsing identifyUsing) {
        return createBy(identifyUsing.how(), identifyUsing.value());
    }

    /**
     * Creates a new {@link By} using the given {@link ByProducers} class and {@code value}.
     *
     * @param producerClass the class to use
     * @param value the value to use
     * @return the created {@link By}
     * @since 2.0
     */
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
            String message = "unable to create instance of " + producerClass;
            throw new InvalidByProducerException(message, e);
        }
    }

}
