package integration.conditions;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.conditions.Conditions;
import info.novatec.testit.webtester.pagefragments.Checkbox;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class ReadOnlyIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Before
    public void initPage() {
        page = create(TestPage.class);
    }

    /**
     * This test verifies that we are using the correct trigger values when
     * working with HTML4/5. In HTML the readonly attribute is a boolean.
     */
    @Test
    public void testThatReadOnlyIsRecognizedForHtmlElement() {
        open("html/conditions/conditions_readonly.html"); // HTML 4/5
        assertThat(Conditions.readOnly().test(page.switchable())).isTrue();
        page.switchCheckbox().select();
        assertThat(Conditions.readOnly().test(page.switchable())).isFalse();
    }

    /**
     * This test verifies that we are using the correct trigger values when
     * working with XHTML. In XHTML the readonly attribute is set if it has the
     * value 'readonly'.
     */
    @Test
    public void testThatReadOnlyIsRecognizedForXhtmlElement() {
        open("html/conditions/conditions_readonly.xhtml"); // XHTML
        assertThat(Conditions.readOnly().test(page.switchable())).isTrue();
        page.switchCheckbox().select();
        assertThat(Conditions.readOnly().test(page.switchable())).isFalse();
    }

    interface TestPage extends Page {

        @IdentifyUsing("#switchable")
        TextField switchable();
        @IdentifyUsing("#switch")
        Checkbox switchCheckbox();

    }

}
