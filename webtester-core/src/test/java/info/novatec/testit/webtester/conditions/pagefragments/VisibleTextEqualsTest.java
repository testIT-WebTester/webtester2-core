package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTextEqualsTest {

    VisibleTextEquals cut = new VisibleTextEquals("foo");

    @Test
    public void matchingWholeTextEvaluatesToTrue() {
        PageFragment fragment = fragment().withVisibleText("foo").build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void partialMatchesEvaluateToFalse() {
        PageFragment fragment = fragment().withVisibleText("foobar").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void notMatchingAnyPartOfTheTextEvaluatesToFalse() {
        PageFragment fragment = fragment().withVisibleText("bar").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("visible text equals: foo");
    }

}
