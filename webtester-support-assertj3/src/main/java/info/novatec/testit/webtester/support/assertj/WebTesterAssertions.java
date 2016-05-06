package info.novatec.testit.webtester.support.assertj;

import org.assertj.core.api.Assertions;

import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.GenericTextField;
import info.novatec.testit.webtester.pagefragments.MultiSelect;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.SingleSelect;
import info.novatec.testit.webtester.pagefragments.traits.Selectable;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.ButtonAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.GenericTextFieldAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.MultiSelectAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.PageFragmentAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.SelectableAssert;
import info.novatec.testit.webtester.support.assertj.assertions.pagefragments.SingleSelectAssert;


public final class WebTesterAssertions extends Assertions {

    public static PageFragmentAssert assertThat(PageFragment actual) {
        return new PageFragmentAssert(actual);
    }

    public static SelectableAssert assertThat(Selectable actual) {
        return new SelectableAssert(actual);
    }

    public static ButtonAssert assertThat(Button actual) {
        return new ButtonAssert(actual);
    }

    public static SingleSelectAssert assertThat(SingleSelect actual) {
        return new SingleSelectAssert(actual);
    }

    public static MultiSelectAssert assertThat(MultiSelect actual) {
        return new MultiSelectAssert(actual);
    }

    public static GenericTextFieldAssert assertThat(GenericTextField actual) {
        return new GenericTextFieldAssert(actual);
    }

    private WebTesterAssertions() {
        // utility constructor
    }

}
