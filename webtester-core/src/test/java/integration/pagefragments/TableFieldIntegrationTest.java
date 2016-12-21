package integration.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.TableField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.mapping.MappingException;
import info.novatec.testit.webtester.pages.Page;


public class TableFieldIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/tableField.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* is header field */

    @Test
    public void tableHeaderFieldsAreRecognizedAsSuch() {
        TableField field = page.tableHeaderField();
        assertThat(field.isHeaderField()).isTrue();
    }

    @Test
    public void nonTableHeaderFieldsAreRecognizedAsSuch() {
        TableField field = page.tableField();
        assertThat(field.isHeaderField()).isFalse();
    }

    /* mapping */

    @Test
    public void tableFieldTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.tableHeaderField());
    }

    @Test
    public void tableHeaderFieldTypeIsValidMapping() {
        assertPageFragmentCanBeInitialized(page.tableField());
    }

    @Test(expected = MappingException.class)
    public void nonTableFieldTypeIsInvalidMapping() {
        assertPageFragmentCanBeInitialized(page.noTableField());
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#tableHeaderField")
        TableField tableHeaderField();

        @IdentifyUsing("#tableField")
        TableField tableField();

        @IdentifyUsing("#noTableField")
        TableField noTableField();

    }

}
