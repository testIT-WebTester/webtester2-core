package info.novatec.testit.webtester.events.pagefragments;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.events.PageFragmentEventBuilder;
import info.novatec.testit.webtester.events.Produces;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * Base implementation of all {@link PageFragment page fragment} {@link AbstractPageFragmentEvent event} builders.
 * <p>
 * These builders are used in conjunction with the {@link Produces @Produces} annotation to build events when annotated
 * methods are executed.
 *
 * @param <T> the event type this builder is producing
 * @since 2.0
 */
public abstract class AbstractPageFragmentEventBuilder<T extends AbstractPageFragmentEvent>
    implements PageFragmentEventBuilder<T> {

    private PageFragment fragment;

    @Override
    public PageFragmentEventBuilder<T> setPageFragment(PageFragment fragment) {
        this.fragment = fragment;
        return this;
    }

    @Override
    public boolean needsBeforeData() {
        return false;
    }

    @Override
    public PageFragmentEventBuilder<T> setBeforeData(WebElement webElement) {
        return this;
    }

    @Override
    public boolean needsAfterData() {
        return false;
    }

    @Override
    public PageFragmentEventBuilder<T> setAfterData(WebElement webElement) {
        return this;
    }

    @Override
    public final T build() {
        if (fragment == null) {
            throw new IllegalStateException("no page fragment set for this builder: " + this);
        }
        return buildWith(fragment);
    }

    protected abstract T buildWith(PageFragment fragment);

}
