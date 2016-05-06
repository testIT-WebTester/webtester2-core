package info.novatec.testit.webtester.pagefragments.annotations;

import java.util.function.Predicate;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This enumeration contains possible wait operations which can be used with {@link info.novatec.testit.webtester.pagefragments.annotations.Wait @Wait}.
 *
 * @see info.novatec.testit.webtester.pagefragments.annotations.Wait
 * @since 2.0
 */
public enum Until {

    /**
     * @see Conditions#visible()
     */
    VISIBLE(Conditions.visible()),

    /**
     * @see Conditions#present()
     */
    PRESENT(Conditions.present()),

    /**
     * @see Conditions#presentAndVisible() ()
     */
    PRESENT_AND_VISIBLE(Conditions.presentAndVisible()),

    /**
     * @see Conditions#enabled()
     */
    ENABLED(Conditions.enabled()),

    /**
     * @see Conditions#editable()
     */
    EDITABLE(Conditions.editable()),

    /**
     * @see Conditions#interactable()
     */
    INTERACTABLE(Conditions.interactable());

    private final Predicate<PageFragment> predicate;

    Until(Predicate<PageFragment> predicate) {
        this.predicate = predicate;
    }

    public void waitWith(PageFragment pageFragment) {
        info.novatec.testit.webtester.waiting.Wait.until(pageFragment).is(predicate);
    }

}
