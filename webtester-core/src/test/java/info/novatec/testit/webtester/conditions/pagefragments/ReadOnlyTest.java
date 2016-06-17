package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class ReadOnlyTest {

    ReadOnly cut = new ReadOnly();

    @Test
    public void htmlReadOnlyEvaluatesToTrue() {
        PageFragment htmlFragment = fragment().withAttribute("readonly", "true").build();
        assertThat(cut.test(htmlFragment)).isTrue();
    }

    @Test
    public void xhtmlReadOnlyEvaluatesToTrue() {
        PageFragment xhtmlFragment = fragment().withAttribute("readonly", "readonly").build();
        assertThat(cut.test(xhtmlFragment)).isTrue();
    }

    @Test
    public void notHavingAttributeEvaluatesToFalse() {
        PageFragment fragment = fragment().withoutAttribute("readonly").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void havingWrongAttributeValueEvaluatesToFalse() {
        PageFragment fragment = fragment().withAttribute("readonly", "wrong").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("read only");
    }

}
