package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import info.novatec.testit.webtester.pagefragments.Checkbox;
import info.novatec.testit.webtester.pagefragments.RadioButton;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;
import info.novatec.testit.webtester.support.assertj.WebTesterAssertions;


/**
 * Contains assertions for {@link Selectable selectables}.
 *
 * @see WebTesterAssertions
 * @see Selectable
 * @see Checkbox
 * @see RadioButton
 * @since 2.0
 */
public class SelectableAssert extends AbstractPageFragmentAssert<SelectableAssert, Selectable> {

    public SelectableAssert(Selectable actual) {
        super(actual, SelectableAssert.class);
    }

    /**
     * Asserts that the {@link Selectable selectable} is selected.
     *
     * @return same assertion instance for fluent API
     * @see Selectable#isSelected()
     * @since 2.0
     */
    public SelectableAssert isSelected() {
        String errorMessage = "Expected selectable fragment to be selected, but it wasn't.";
        assertThat(actual.isSelected()).overridingErrorMessage(errorMessage).isTrue();
        return this;
    }

    /**
     * Asserts that the {@link Selectable selectable} is not selected.
     *
     * @return same assertion instance for fluent API
     * @see Selectable#isNotSelected()
     * @since 2.0
     */
    public SelectableAssert isNotSelected() {
        String errorMessage = "Expected selectable fragment not to be selected, but it was.";
        assertThat(actual.isNotSelected()).overridingErrorMessage(errorMessage).isTrue();
        return this;
    }

}
