package info.novatec.testit.webtester.events;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import info.novatec.testit.webtester.events.pagefragments.AbstractPageFragmentEvent;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Methods of {@link PageFragment page fragments} annotated with this annotation will trigger the firing of an event of the
 * specified class upon invocation.
 * <p>
 * For this to work there needs to be a corresponding registered {@link PageFragmentEventBuilder} implementation.
 *
 * @see Event
 * @see EventSystem
 * @see PageFragmentEventBuilder
 * @since 2.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Produces {

    /**
     * The class of the event to fire when the annotated method is invoked.
     * <p>
     * Must be a subclass of {@link AbstractPageFragmentEvent}!
     *
     * @return the event class
     * @see Event
     * @see AbstractPageFragmentEvent
     * @see Produces
     * @since 2.0
     */
    Class<? extends AbstractPageFragmentEvent> value();

}
