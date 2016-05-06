package info.novatec.testit.webtester.pagefragments.annotations;

import java.util.function.Predicate;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.PageFragment;


/**
 * This enumeration contains possible wait operations which can be used with {@link Must @Must}.
 *
 * @see Must
 * @since 2.0
 */
public enum Be {

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

    private final Predicate<? super PageFragment> predicate;

    Be(Predicate<? super PageFragment> predicate) {
        this.predicate = predicate;
    }

    public boolean checkFor(PageFragment pageFragment) {
        return predicate.test(pageFragment);
    }

}
