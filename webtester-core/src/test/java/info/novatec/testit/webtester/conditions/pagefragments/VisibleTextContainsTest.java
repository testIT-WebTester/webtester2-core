package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class VisibleTextContainsTest {

    VisibleTextContains cut = new VisibleTextContains("foo");

    @Test
    public void matchingATermEvaluatesToTrue() {
        PageFragment fragment = fragment().withVisibleText("foo bar").build();
        assertThat(containsText(fragment)).isTrue();
    }

    @Test
    public void matchingWithinATermEvaluatesToTrue() {
        PageFragment fragment = fragment().withVisibleText("barfoobar").build();
        assertThat(containsText(fragment)).isTrue();
    }

    @Test
    public void testThatNotMatchingVisibleTextEvaluatesToFalse() {
        PageFragment fragment = fragment().withVisibleText("bar boo").build();
        assertThat(containsText(fragment)).isFalse();
    }

    boolean containsText(PageFragment fragment) {
        return cut.test(fragment);
    }

}
