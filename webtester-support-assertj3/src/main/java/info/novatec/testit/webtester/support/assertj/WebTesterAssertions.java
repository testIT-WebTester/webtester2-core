package info.novatec.testit.webtester.support.assertj;

import org.assertj.core.api.Assertions;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.GenericSelect;
import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.ButtonAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.GenericSelectAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.GenericTextFieldAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.MultiSelectAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.PageFragmentAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.SelectableAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.SingleSelectAssert;


/**
 * This class contains a number of entry points for WebTester related {@link Assertions}.
 *
 * @see PageFragmentAssert
 * @see SelectableAssert
 * @see ButtonAssert
 * @see SingleSelectAssert
 * @see MultiSelectAssert
 * @see GenericTextFieldAssert
 * @since 2.0
 */
public final class WebTesterAssertions extends Assertions {

    /**
     * Creates a new {@link PageFragmentAssert} for the given {@link PageFragment}.
     *
     * @param actual the page fragment to assert
     * @return the new assert instance
     * @see PageFragment
     * @see PageFragmentAssert
     * @since 2.0
     */
    public static PageFragmentAssert assertThat(PageFragment actual) {
        return new PageFragmentAssert(actual);
    }

    /**
     * Creates a new {@link SelectableAssert} for the given {@link Selectable}.
     *
     * @param actual the selectable to assert
     * @return the new assert instance
     * @see Selectable
     * @see SelectableAssert
     * @since 2.0
     */
    public static SelectableAssert assertThat(Selectable actual) {
        return new SelectableAssert(actual);
    }

    /**
     * Creates a new {@link ButtonAssert} for the given {@link Button}.
     *
     * @param actual the button to assert
     * @return the new assert instance
     * @see Button
     * @see ButtonAssert
     * @since 2.0
     */
    public static ButtonAssert assertThat(Button actual) {
        return new ButtonAssert(actual);
    }

    /**
     * Creates a new {@link GenericSelectAssert} for the given {@link GenericSelect}.
     *
     * @param actual the select to assert
     * @return the new assert instance
     * @see GenericSelect
     * @see GenericSelectAssert
     * @since 2.0
     */
    public static GenericSelectAssert assertThat(GenericSelect actual) {
        return new GenericSelectAssert(actual, GenericSelectAssert.class);
    }

    /**
     * Creates a new {@link SingleSelectAssert} for the given {@link SingleSelect}.
     *
     * @param actual the single select to assert
     * @return the new assert instance
     * @see SingleSelect
     * @see SingleSelectAssert
     * @since 2.0
     */
    public static SingleSelectAssert assertThat(SingleSelect actual) {
        return new SingleSelectAssert(actual);
    }

    /**
     * Creates a new {@link MultiSelectAssert} for the given {@link MultiSelect}.
     *
     * @param actual the multi select to assert
     * @return the new assert instance
     * @see MultiSelect
     * @see MultiSelectAssert
     * @since 2.0
     */
    public static MultiSelectAssert assertThat(MultiSelect actual) {
        return new MultiSelectAssert(actual);
    }

    /**
     * Creates a new {@link GenericTextFieldAssert} for the given {@link GenericTextField}.
     *
     * @param actual the text field to assert
     * @return the new assert instance
     * @see GenericTextField
     * @see GenericTextFieldAssert
     * @since 2.0
     */
    public static GenericTextFieldAssert assertThat(GenericTextField actual) {
        return new GenericTextFieldAssert(actual);
    }

    private WebTesterAssertions() {
        // utility constructor
    }

}
