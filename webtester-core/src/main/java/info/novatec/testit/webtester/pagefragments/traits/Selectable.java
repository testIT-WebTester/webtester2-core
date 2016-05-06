package info.novatec.testit.webtester.pagefragments.traits;

import org.openqa.selenium.WebElement;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.Mark;
import info.novatec.testit.webtester.pagefragments.annotations.As;


/**
 * {@link PageFragment Page fragments} extending this trait are "selectable" i.e. radio buttons, checkboxes etc.
 *
 * @param <T> the type of the page fragment for this trait, used as return type for fluent API methods
 * @see PageFragment
 * @see WebElement#isSelected()
 * @since 2.0
 */
public interface Selectable<T extends PageFragment> extends PageFragment {

    /**
     * Returns whether or not the {@link PageFragment page fragment} is currently selected.
     *
     * @return true if selected, false otherwise
     * @see PageFragment
     * @see WebElement#isSelected()
     * @since 2.0
     */
    @Mark(As.READ)
    default boolean isSelected() {
        return webElement().isSelected();
    }

    /**
     * Returns whether or not the {@link PageFragment page fragment} is currently selected.
     *
     * @return true if selected, false otherwise
     * @see PageFragment
     * @see WebElement#isSelected()
     * @since 2.0
     */
    @Mark(As.READ)
    default boolean isNotSelected() {
        return !webElement().isSelected();
    }

}
