package info.novatec.testit.webtester.conditions.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;
import static utils.MockFactory.fragment;

import org.junit.Test;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class AttributeTest {

    Attribute cut = new Attribute("attribute");

    @Test
    public void havingTheAttributeEvaluatesToTrue() {
        PageFragment fragment = fragment().withAttribute("attribute").build();
        assertThat(hasAttribute(fragment)).isTrue();
    }

    @Test
    public void notHavingTheAttributeEvaluatesToFalse() {
        PageFragment fragment = fragment().withoutAttribute("attribute").build();
        assertThat(hasAttribute(fragment)).isFalse();
    }

    boolean hasAttribute(PageFragment fragment) {
        return cut.test(fragment);
    }

}
