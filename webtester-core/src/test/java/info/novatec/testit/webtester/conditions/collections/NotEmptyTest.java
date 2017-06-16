package info.novatec.testit.webtester.conditions.collections;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


class NotEmptyTest {

    NotEmpty cut = new NotEmpty();

    @Test
    void emptyCollectionEvaluatesToFalse() {
        boolean result = cut.test(emptyList());
        assertThat(result).isFalse();
    }

    @Test
    void collectionWithOneElementEvaluatesToTrue() {
        boolean result = cut.test(singletonList(mock(PageFragment.class)));
        assertThat(result).isTrue();
    }

}