package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.unit.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTextContainsTest {

    VisibleTextContains cut = new VisibleTextContains("foo");

    @Test
    public void matchingATermEvaluatesToTrue() {
        PageFragment fragment = fragment().withVisibleText("foo bar").build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void matchingWithinATermEvaluatesToTrue() {
        PageFragment fragment = fragment().withVisibleText("barfoobar").build();
        assertThat(cut.test(fragment)).isTrue();
    }

    @Test
    public void testThatNotMatchingVisibleTextEvaluatesToFalse() {
        PageFragment fragment = fragment().withVisibleText("bar boo").build();
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void toStringIsGeneratedCorrectly() {
        assertThat(cut).hasToString("visible text contains: foo");
    }

}
