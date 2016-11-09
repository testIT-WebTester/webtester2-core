package info.novatec.testit.webtester.mouse;

import static info.novatec.testit.webtester.conditions.Conditions.visible;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.BiFunction;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.ContextClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.DoubleClickedEvent;
import info.novatec.testit.webtester.internal.ActionTemplate;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.waiting.Wait;


/**
 * The default implementation of a {@link MouseDriver}.
 * <p>
 * <b>Note:</b> Methods provided by this class depend on Selenium's {@link Actions} class.
 * There are some {@link WebDriver} implementation (e.g. v3.0.1 of the Marionette-based FirefoxDriver)
 * which do not support the commands issued by {@link Actions}!
 *
 * @since 2.0
 */
public class DefaultMouseDriver implements MouseDriver {

    @Override
    public void click(PageFragment fragment) {
        ActionTemplate.pageFragment(fragment)
            .execute((f) -> perform(f, Actions::click))
            .fireEvent(ClickedEvent::new)
            .markAsUsed();
    }

    @Override
    public void doubleClick(PageFragment fragment) {
        ActionTemplate.pageFragment(fragment)
            .execute((f) -> perform(f, Actions::doubleClick))
            .fireEvent(DoubleClickedEvent::new)
            .markAsUsed();
    }

    @Override
    public void contextClick(PageFragment fragment) {
        ActionTemplate.pageFragment(fragment)
            .execute((f) -> perform(f, Actions::contextClick))
            .fireEvent(ContextClickedEvent::new)
            .markAsUsed();
    }

    @Override
    public void moveToEach(PageFragment fragment, PageFragment... fragments) throws TimeoutException {
        moveTo(fragment);
        moveToEach(Arrays.asList(fragments));
    }

    @Override
    public void moveToEach(Collection<PageFragment> fragments) throws TimeoutException {
        fragments.forEach(this::moveTo);
    }

    @Override
    public void moveTo(PageFragment fragment) throws TimeoutException {
        ActionTemplate.pageFragment(fragment).execute((f) -> {
            Wait.until(f).is(visible());
            perform(f, Actions::moveToElement);
        });
    }

    private void perform(PageFragment fragment, BiFunction<Actions, WebElement, Actions> biConsumer) {
        biConsumer.apply(sequenceFor(fragment), fragment.webElement()).perform();
    }

    private Actions sequenceFor(PageFragment fragment) {
        return new Actions(fragment.browser().webDriver());
    }

}
