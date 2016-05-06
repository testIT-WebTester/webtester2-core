package info.novatec.testit.webtester.conditions.syntax;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import info.novatec.testit.webtester.pagefragments.PageFragment;


@RunWith(MockitoJUnitRunner.class)
public class NotTest {

    @Mock
    PageFragment fragment;

    @Test
    public void trueIsInvertedToFalse() {
        Not<PageFragment> cut = new Not<>(fragment -> true);
        assertThat(cut.test(fragment)).isFalse();
    }

    @Test
    public void falseIsInvertedToTrue() {
        Not<PageFragment> cut = new Not<>(fragment -> false);
        assertThat(cut.test(fragment)).isTrue();
    }

}
