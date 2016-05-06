package info.novatec.testit.webtester.pagefragments;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.events.pagefragments.FormSubmittedEvent;
import info.novatec.testit.webtester.pagefragments.annotations.Action;
import info.novatec.testit.webtester.pagefragments.annotations.Mapping;


@Mapping(tag = "form")
public interface Form extends PageFragment {

    /**
     * Submits this {@link Form} for processing by the application and fires a {@link FormSubmittedEvent}.
     *
     * @return the same form for fluent API use
     * @see Form
     * @see WebElement#submit()
     * @see FormSubmittedEvent
     * @since 2.0
     */
    @Action
    @Produces(FormSubmittedEvent.class)
    default Form submit() {
        webElement().submit();
        return this;
    }

}
