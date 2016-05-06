package integration.adhoc;

import static info.novatec.testit.webtester.conditions.Conditions.attributeWithValue;
import static info.novatec.testit.webtester.conditions.Conditions.has;
import static info.novatec.testit.webtester.conditions.Conditions.visibleText;
import static info.novatec.testit.webtester.pagefragments.identification.ByProducers.css;
import static info.novatec.testit.webtester.pagefragments.identification.ByProducers.id;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.openqa.selenium.NoSuchElementException;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pages.Page;


@RunWith(Enclosed.class)
public class AdHocFinderIntegrationTest {

    public static class FromBrowser extends AbstractAdHocFindTest {

        /* CSS Selector */

        @Test
        public void usingCssSelectorFindSingleGeneric() {
            GenericElement textField = browser()
                .find("#textField");
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingCssSelectorFindManyGenerics() {
            List<GenericElement> textFields = browser()
                .findMany(".textField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingCssSelectorFindManyGenericsAndFilterThem() {
            List<GenericElement> filteredTextFields = browser()
                .findMany(".textField")
                .filter(attributeWithValue("value", "foo"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        /* Identification First */

        @Test
        public void usingIdentificationFindSingleGeneric() {
            GenericElement textField = browser()
                .findBy(id("textField"))
                .asGeneric();
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingIdentificationFindSingleTextField() {
            TextField textField = browser()
                .findBy(id("textField"))
                .as(TextField.class);
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingIdentificationFindManyGenerics() {
            List<GenericElement> textFields = browser()
                .findBy(css(".textField"))
                .asManyGenerics()
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingIdentificationFindManyTextFields() {
            List<TextField> textFields = browser()
                .findBy(css(".textField"))
                .asMany(TextField.class)
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingIdentificationFindManyGenericsAndFilterThem() {
            List<GenericElement> filteredTextFields = browser()
                .findBy(css(".textField"))
                .asManyGenerics()
                .filter(attributeWithValue("value", "foo"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        @Test
        public void usingIdentificationFindManyTextFieldsAndFilterThem() {
            List<TextField> filteredTextFields = browser()
                .findBy(css(".textField"))
                .asMany(TextField.class)
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        /* Class First */

        @Test
        public void usingClassFirstToFindSingleTextFieldWithCssSelector() {
            TextField textField = browser()
                .find(TextField.class)
                .by("#textField");
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingClassFirstToFindSingleTextFieldWithIdentification() {
            TextField textField = browser()
                .find(TextField.class)
                .by(id("textField"));
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithCssSelector() {
            List<TextField> textFields = browser()
                .find(TextField.class)
                .manyBy(".textField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithIdentification() {
            List<TextField> textFields = browser()
                .find(TextField.class)
                .manyBy(css(".textField"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithCssSelectorAndFilterThem() {
            List<TextField> filteredTextFields = browser()
                .find(TextField.class)
                .manyBy(".textField")
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithIdentificationAndFilterThem() {
            List<TextField> filteredTextFields = browser()
                .find(TextField.class)
                .manyBy(css(".textField"))
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

    }

    public static class FromPage extends AbstractAdHocFindTest {

        /* CSS Selector */

        @Test
        public void usingCssSelectorFindSingleGeneric() {
            GenericElement textField = page()
                .find("#textField");
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingCssSelectorFindManyGenerics() {
            List<GenericElement> textFields = page()
                .findMany(".textField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingCssSelectorFindManyGenericsAndFilterThem() {
            List<GenericElement> filteredTextFields = page()
                .findMany(".textField")
                .filter(attributeWithValue("value", "foo"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        /* Identification First */

        @Test
        public void usingIdentificationFindSingleGeneric() {
            GenericElement textField = page()
                .findBy(id("textField"))
                .asGeneric();
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingIdentificationFindSingleTextField() {
            TextField textField = page()
                .findBy(id("textField"))
                .as(TextField.class);
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingIdentificationFindManyGenerics() {
            List<GenericElement> textFields = page()
                .findBy(css(".textField"))
                .asManyGenerics()
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingIdentificationFindManyTextFields() {
            List<TextField> textFields = page()
                .findBy(css(".textField"))
                .asMany(TextField.class)
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingIdentificationFindManyGenericsAndFilterThem() {
            List<GenericElement> filteredTextFields = page()
                .findBy(css(".textField"))
                .asManyGenerics()
                .filter(attributeWithValue("value", "foo"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        @Test
        public void usingIdentificationFindManyTextFieldsAndFilterThem() {
            List<TextField> filteredTextFields = page()
                .findBy(css(".textField"))
                .asMany(TextField.class)
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        /* Class First */

        @Test
        public void usingClassFirstToFindSingleTextFieldWithCssSelector() {
            TextField textField = page()
                .find(TextField.class)
                .by("#textField");
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingClassFirstToFindSingleTextFieldWithIdentification() {
            TextField textField = page()
                .find(TextField.class)
                .by(id("textField"));
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithCssSelector() {
            List<TextField> textFields = page()
                .find(TextField.class)
                .manyBy(".textField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithIdentification() {
            List<TextField> textFields = page()
                .find(TextField.class)
                .manyBy(css(".textField"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithCssSelectorAndFilterThem() {
            List<TextField> filteredTextFields = page()
                .find(TextField.class)
                .manyBy(".textField")
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithIdentificationAndFilterThem() {
            List<TextField> filteredTextFields = page()
                .find(TextField.class)
                .manyBy(css(".textField"))
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

    }

    public static class FromPageFragment extends AbstractAdHocFindTest {

        @Test
        public void findingPageFragmentsWithinPageFragments() {

            List<GenericElement> textFieldsInAllGroups = browser()
                .find("#groups")
                .findMany(".groupTextField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFieldsInAllGroups, 7);

            List<GenericElement> textFieldsInGroup1 = browser()
                .find("#group1")
                .findMany(".groupTextField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFieldsInGroup1, 5);

            List<GenericElement> textFieldsInGroup2 = browser()
                .find("#group2")
                .findMany(".groupTextField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFieldsInGroup2, 2);

        }

        /* CSS Selector */

        @Test
        public void usingCssSelectorFindSingleGeneric() {
            GenericElement textField = browser()
                .find("#group1")
                .find("#group1\\:txt1");
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingCssSelectorFindManyGenerics() {
            List<GenericElement> textFields = browser()
                .find("#group1")
                .findMany(".groupTextField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingCssSelectorFindManyGenericsAndFilterThem() {
            List<GenericElement> filteredTextFields = browser()
                .find("#group1")
                .findMany(".groupTextField")
                .filter(attributeWithValue("value", "foo"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        /* Identification First */

        @Test
        public void usingIdentificationFindSingleGeneric() {
            GenericElement textField = browser()
                .find("#group1")
                .findBy(id("group1:txt1"))
                .asGeneric();
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingIdentificationFindSingleTextField() {
            TextField textField = browser()
                .find("#group1")
                .findBy(id("group1:txt1"))
                .as(TextField.class);
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingIdentificationFindManyGenerics() {
            List<GenericElement> textFields = browser()
                .find("#group1")
                .findBy(css(".groupTextField"))
                .asManyGenerics()
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingIdentificationFindManyTextFields() {
            List<TextField> textFields = browser()
                .find("#group1")
                .findBy(css(".groupTextField"))
                .asMany(TextField.class)
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingIdentificationFindManyGenericsAndFilterThem() {
            List<GenericElement> filteredTextFields = browser()
                .find("#group1")
                .findBy(css(".groupTextField"))
                .asManyGenerics()
                .filter(attributeWithValue("value", "foo"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        @Test
        public void usingIdentificationFindManyTextFieldsAndFilterThem() {
            List<TextField> filteredTextFields = browser()
                .find("#group1")
                .findBy(css(".groupTextField"))
                .asMany(TextField.class)
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        /* Class First */

        @Test
        public void usingClassFirstToFindSingleTextFieldWithCssSelector() {
            TextField textField = browser()
                .find("#group1")
                .find(TextField.class)
                .by("#group1\\:txt1");
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingClassFirstToFindSingleTextFieldWithIdentification() {
            TextField textField = browser()
                .find("#group1")
                .find(TextField.class)
                .by(id("group1:txt1"));
            verifyPageFragmentWasFound(textField);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithCssSelector() {
            List<TextField> textFields = browser()
                .find("#group1")
                .find(TextField.class)
                .manyBy(".groupTextField")
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithIdentification() {
            List<TextField> textFields = browser()
                .find("#group1")
                .find(TextField.class)
                .manyBy(css(".groupTextField"))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(textFields, 5);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithCssSelectorAndFilterThem() {
            List<TextField> filteredTextFields = browser()
                .find("#group1")
                .find(TextField.class)
                .manyBy(".groupTextField")
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

        @Test
        public void usingClassFirstToFindManyTextFieldsWithIdentificationAndFilterThem() {
            List<TextField> filteredTextFields = browser()
                .find("#group1")
                .find(TextField.class)
                .manyBy(css(".groupTextField"))
                .filter(has(visibleText("foo")))
                .collect(Collectors.toList());
            verifyPageFragmentsWereFound(filteredTextFields, 2);
        }

    }

    public static abstract class AbstractAdHocFindTest extends BaseIntegrationTest {

        @Override
        protected final String getHTMLFilePath() {
            return "html/utils/ad-hoc-finding.html";
        }

        final void verifyPageFragmentWasFound(PageFragment fragment) throws NoSuchElementException {
            // getting the web element will throw an exception if not found!
            fragment.webElement();
        }

        final void verifyPageFragmentsWereFound(List<? extends PageFragment> fragments, int expectedSize)
            throws NoSuchElementException {
            assertThat(fragments).hasSize(expectedSize);
            // getting the web element will throw an exception if not found!
            fragments.forEach(PageFragment::webElement);
        }

        final Page page() {
            return create(Page.class);
        }

    }

}
