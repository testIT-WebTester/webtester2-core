package features;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.producers.IdStartsWith;
import info.novatec.testit.webtester.pages.Page;


public class IdentifyUsingFeatureTest extends BaseIntegrationTest {

    @Before
    public void openPage() {
        open("html/features/identify-using.html");
    }

    @Test
    public void demonstrateSinglePageFragments() {
        FeaturePage page = create(FeaturePage.class);
        assertThat(page.firstTextField().getText()).isEqualTo("#1");
        assertThat(page.secondTextField().getText()).isEqualTo("#2");
        assertThat(page.thirdTextField().getText()).isEqualTo("#3");
    }

    @Test
    public void demonstrateStreamsOfPageFragments() {
        Stream<TextField> stream = create(FeaturePage.class).textFieldStream();
        assertThat(extractTextValues(stream)).containsExactly("#1", "#2", "#3");
    }

    @Test
    public void demonstrateListOfPageFragments() {
        List<TextField> list = create(FeaturePage.class).textFieldList();
        assertThat(extractTextValues(list)).containsExactly("#1", "#2", "#3");
    }

    @Test
    public void demonstrateSetOfPageFragments() {
        Set<TextField> set = create(FeaturePage.class).textFieldSet();
        assertThat(extractTextValues(set)).containsOnly("#1", "#2", "#3");
    }

    private Stream<String> extractTextValues(Collection<TextField> collection) {
        return extractTextValues(collection.stream());
    }

    private Stream<String> extractTextValues(Stream<TextField> stream) {
        return stream.map(TextField::getText);
    }

    /* test pages */

    public interface FeaturePage extends Page {

        @IdentifyUsing("#textField1")
        TextField firstTextField();

        @IdentifyUsing("#textField2")
        TextField secondTextField();

        @IdentifyUsing("#textField3")
        TextField thirdTextField();

        @IdentifyUsing(how = IdStartsWith.class, value = "textField")
        Stream<TextField> textFieldStream();

        @IdentifyUsing(how = IdStartsWith.class, value = "textField")
        List<TextField> textFieldList();

        @IdentifyUsing(how = IdStartsWith.class, value = "textField")
        Set<TextField> textFieldSet();

    }

}
