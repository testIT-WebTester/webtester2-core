package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeWithValueTest {

    AttributeWithValue cut = new AttributeWithValue("attribute", "value");

    @Test
    public void havingTheAttributeValueMatchEvaluatesToTrue() {
        PageFragment fragment = fragment().withAttribute("attribute", "value").build();
        assertThat(hasAttributeWithValue(fragment)).isTrue();
    }

    @Test
    public void havingADifferentValueForTheAttributeEvaluatesToFalse() {
        PageFragment fragment = fragment().withAttribute("attribute", "otherValue").build();
        assertThat(hasAttributeWithValue(fragment)).isFalse();
    }

    @Test
    public void notHavingTheAttributeEvaluatesToFalse() {
        PageFragment fragment = fragment().withoutAttribute("attribute").build();
        assertThat(hasAttributeWithValue(fragment)).isFalse();
    }

    boolean hasAttributeWithValue(PageFragment fragment) {
        return cut.test(fragment);
    }

}
