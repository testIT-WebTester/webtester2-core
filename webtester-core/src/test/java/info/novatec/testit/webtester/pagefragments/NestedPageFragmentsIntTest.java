package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.identification.producers.XPath;
import info.novatec.testit.webtester.pages.Page;


public class NestedPageFragmentsIntTest extends BaseIntTest {

    @Override
    protected String getHTMLFilePath() {
        return "html/nested-page-fragments.html";
    }

    @Test
    public final void nestedPageFragmentsAreHandledCorrectly() {

        NestedPageObjectsPage page = create(NestedPageObjectsPage.class);

        String operand = "divOne";
        String operand2 = "divTwo";
        String operand3 = "divThree1";
        String operand4 = "divThree2";

        Stream<TextField> allTextFieldsOfPage = page.textFields();
        assertThat(allTextFieldsOfPage.map(TextField::getText)).containsExactly(operand, operand2, operand3, operand4);

        NestedPageObjectOne nestedOne = page.nestedPageObjectOne();
        assertThat(nestedOne.textfield().getText()).isEqualTo(operand);
        assertThat(nestedOne.textFields().map(TextField::getText)).containsExactly(operand);

        NestedPageObjectTwo nestedTwo = page.nestedPageObjectTwo();
        assertThat(nestedTwo.textfield().getText()).isEqualTo(operand2);
        assertThat(nestedTwo.textFields().map(TextField::getText)).containsExactly(operand2, operand3, operand4);

        NestedPageObjectThree nestedThree = nestedTwo.nestedPageObjectThree();
        assertThat(nestedThree.textField1().getText()).isEqualTo(operand3);
        assertThat(nestedThree.textField2().getText()).isEqualTo(operand4);
        assertThat(nestedThree.textFields().map(TextField::getText)).containsExactly(operand3, operand4);

    }

    public interface NestedPageObjectOne extends PageFragment {

        @IdentifyUsing("#textfield")
        TextField textfield();

        @IdentifyUsing(value = ".//input", how = XPath.class)
        Stream<TextField> textFields();

    }

    public interface NestedPageObjectsPage extends Page {

        @IdentifyUsing("#title")
        Headline title();

        @IdentifyUsing(value = ".//input", how = XPath.class)
        Stream<TextField> textFields();

        @IdentifyUsing("#divOne")
        NestedPageObjectOne nestedPageObjectOne();

        @IdentifyUsing("#divTwo")
        NestedPageObjectTwo nestedPageObjectTwo();

    }

    public interface NestedPageObjectThree extends PageFragment {

        @IdentifyUsing("#otherTextfield1")
        TextField textField1();

        @IdentifyUsing("#otherTextfield2")
        TextField textField2();

        @IdentifyUsing(value = ".//input", how = XPath.class)
        Stream<TextField> textFields();

    }

    public interface NestedPageObjectTwo extends PageFragment {

        @IdentifyUsing("#textfield")
        TextField textfield();

        @IdentifyUsing(value = ".//input", how = XPath.class)
        Stream<TextField> textFields();

        @IdentifyUsing("#divThree")
        NestedPageObjectThree nestedPageObjectThree();

    }

}
