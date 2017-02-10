package info.novatec.testit.webtester.adhoc;

import static info.novatec.testit.webtester.conditions.Conditions.attributeWithValue;
import static info.novatec.testit.webtester.conditions.Conditions.has;
import static info.novatec.testit.webtester.conditions.Conditions.visibleText;
import static info.novatec.testit.webtester.pagefragments.identification.ByProducers.css;
import static info.novatec.testit.webtester.pagefragments.identification.ByProducers.id;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.NoSuchElementException;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.GenericElement;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pages.Page;


class AdHocFinderIntTest extends BaseIntTest {

    @Override
    protected String getHTMLFilePath() {
        return "html/utils/ad-hoc-finding.html";
    }

    @Nested
    @DisplayName("Page Fragments can be searched within a Browser")
    class FromBrowser {

        @Nested
        @DisplayName("find(...) takes a CSS selector")
        class CssSelector {

            @Test
            void findingSingleGenericElement() {
                GenericElement textField = browser().find("#textField");
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyGenericElements() {
                List<GenericElement> textFields = browser().findMany(".textField")//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyGenericElementsAndFilteringThem() {
                List<GenericElement> filteredTextFields = browser().findMany(".textField")//
                    .filter(attributeWithValue("value", "foo"))//
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

        @Nested
        @DisplayName("findBy(...) takes any By selector")
        class IdentificationFirst {

            @Test
            void findingSingleGenericElement() {
                GenericElement textField = browser().findBy(id("textField")).asGeneric();
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingSingleTextField() {
                TextField textField = browser().findBy(id("textField")).as(TextField.class);
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyGenericElements() {
                List<GenericElement> textFields = browser().findBy(css(".textField"))//
                    .asManyGenerics()//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFields() {
                List<TextField> textFields = browser().findBy(css(".textField"))//
                    .asMany(TextField.class)//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyGenericElementsAndFilteringThem() {
                List<GenericElement> filteredTextFields = browser().findBy(css(".textField"))
                    .asManyGenerics()
                    .filter(attributeWithValue("value", "foo"))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

            @Test
            void findingManyTextFieldsAndFilteringThem() {
                List<TextField> filteredTextFields = browser().findBy(css(".textField"))
                    .asMany(TextField.class)
                    .filter(has(visibleText("foo")))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

        @Nested
        @DisplayName("find(...) takes a Page Fragment class")
        class ClassFirst {

            @Test
            void findingSingleTextFieldWithDefaultCssSelector() {
                TextField textField = browser().find(TextField.class).by("#textField");
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingSingleTextFieldWithBySelector() {
                TextField textField = browser().find(TextField.class).by(id("textField"));
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyTextFieldsWithDefaultCssSelector() {
                List<TextField> textFields = browser().find(TextField.class)//
                    .manyBy(".textField")//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFieldsWithBySelector() {
                List<TextField> textFields = browser().find(TextField.class)//
                    .manyBy(css(".textField"))//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFieldsWithDefaultCssSelectorAndFilterThem() {
                List<TextField> filteredTextFields = browser().find(TextField.class)//
                    .manyBy(".textField")//
                    .filter(has(visibleText("foo")))//
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

            @Test
            void findingManyTextFieldsWithBySelectorAndFilterThem() {
                List<TextField> filteredTextFields = browser().find(TextField.class)
                    .manyBy(css(".textField"))
                    .filter(has(visibleText("foo")))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

    }

    @Nested
    @DisplayName("Page Fragments can be searched with a Page")
    class FromPage {

        @Nested
        @DisplayName("find(...) takes a CSS selector")
        class CssSelector {

            @Test
            void findingSingleGenericElement() {
                GenericElement textField = page().find("#textField");
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyGenericElements() {
                List<GenericElement> textFields = page().findMany(".textField").collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyGenericElementsAndFilteringThem() {
                List<GenericElement> filteredTextFields = page().findMany(".textField")//
                    .filter(attributeWithValue("value", "foo"))//
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

        @Nested
        @DisplayName("findBy(...) takes any By selector")
        class IdentificationFirst {

            @Test
            void findingSingleGenericElement() {
                GenericElement textField = page().findBy(id("textField")).asGeneric();
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingSingleTextField() {
                TextField textField = page().findBy(id("textField")).as(TextField.class);
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyGenericElements() {
                List<GenericElement> textFields = page().findBy(css(".textField"))//
                    .asManyGenerics()//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFields() {
                List<TextField> textFields = page().findBy(css(".textField"))//
                    .asMany(TextField.class)//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyGenericElementsAndFilteringThem() {
                List<GenericElement> filteredTextFields = page().findBy(css(".textField"))
                    .asManyGenerics()
                    .filter(attributeWithValue("value", "foo"))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

            @Test
            void findingManyTextFieldsAndFilteringThem() {
                List<TextField> filteredTextFields = page().findBy(css(".textField"))//
                    .asMany(TextField.class)//
                    .filter(has(visibleText("foo")))//
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

        @Nested
        @DisplayName("find(...) takes a Page Fragment class")
        class ClassFirst {

            @Test
            void findingSingleTextFieldWithDefaultCssSelector() {
                TextField textField = page().find(TextField.class).by("#textField");
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingSingleTextFieldWithBySelector() {
                TextField textField = page().find(TextField.class).by(id("textField"));
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyTextFieldsWithDefaultCssSelector() {
                List<TextField> textFields = page().find(TextField.class)//
                    .manyBy(".textField")//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFieldsWithBySelector() {
                List<TextField> textFields = page().find(TextField.class)//
                    .manyBy(css(".textField"))//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFieldsWithDefaultCssSelectorAndFilterThem() {
                List<TextField> filteredTextFields = page().find(TextField.class)//
                    .manyBy(".textField")//
                    .filter(has(visibleText("foo")))//
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

            @Test
            void findingManyTextFieldsWithBySelectorAndFilterThem() {
                List<TextField> filteredTextFields = page().find(TextField.class)//
                    .manyBy(css(".textField"))//
                    .filter(has(visibleText("foo")))//
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

    }

    @Nested
    @DisplayName("Page Fragments can be searched within another Page Fragment")
    class FromPageFragment {

        @Test
        void findingPageFragmentsWithinPageFragments() {
            List<GenericElement> textFieldsInAllGroups = browser().find("#groups")//
                .findMany(".groupTextField")//
                .collect(toList());
            verifyPageFragmentsWereFound(textFieldsInAllGroups, 7);

            List<GenericElement> textFieldsInGroup1 = browser().find("#group1")//
                .findMany(".groupTextField")//
                .collect(toList());
            verifyPageFragmentsWereFound(textFieldsInGroup1, 5);

            List<GenericElement> textFieldsInGroup2 = browser().find("#group2")//
                .findMany(".groupTextField")//
                .collect(toList());
            verifyPageFragmentsWereFound(textFieldsInGroup2, 2);
        }

        @Nested
        @DisplayName("find(...) takes a CSS selector")
        class CssSelector {

            @Test
            void findingSingleGenericElement() {
                GenericElement textField = browser().find("#group1")//
                    .find("#group1\\:txt1");
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyGenericElements() {
                List<GenericElement> textFields = browser().find("#group1")//
                    .findMany(".groupTextField")//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyGenericElementsAndFilteringThem() {
                List<GenericElement> filteredTextFields = browser().find("#group1")
                    .findMany(".groupTextField")
                    .filter(attributeWithValue("value", "foo"))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

        @Nested
        @DisplayName("findBy(...) takes any By selector")
        class IdentificationFirst {

            @Test
            void findingSingleGenericElement() {
                GenericElement textField = browser().find("#group1")//
                    .findBy(id("group1:txt1"))//
                    .asGeneric();
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingSingleTextField() {
                TextField textField = browser().find("#group1")//
                    .findBy(id("group1:txt1"))//
                    .as(TextField.class);
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyGenericElements() {
                List<GenericElement> textFields = browser().find("#group1")//
                    .findBy(css(".groupTextField"))//
                    .asManyGenerics()//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFields() {
                List<TextField> textFields = browser().find("#group1")//
                    .findBy(css(".groupTextField"))//
                    .asMany(TextField.class)//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyGenericElementsAndFilteringThem() {
                List<GenericElement> filteredTextFields = browser().find("#group1")
                    .findBy(css(".groupTextField"))
                    .asManyGenerics()
                    .filter(attributeWithValue("value", "foo"))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

            @Test
            void findingManyTextFieldsAndFilteringThem() {
                List<TextField> filteredTextFields = browser().find("#group1")
                    .findBy(css(".groupTextField"))
                    .asMany(TextField.class)
                    .filter(has(visibleText("foo")))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

        @Nested
        @DisplayName("find(...) takes a Page Fragment class")
        class ClassFirst {

            @Test
            void findingSingleTextFieldWithDefaultCssSelector() {
                TextField textField = browser().find("#group1")//
                    .find(TextField.class)//
                    .by("#group1\\:txt1");
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingSingleTextFieldWithBySelector() {
                TextField textField = browser().find("#group1")//
                    .find(TextField.class)//
                    .by(id("group1:txt1"));
                verifyPageFragmentWasFound(textField);
            }

            @Test
            void findingManyTextFieldsWithDefaultCssSelector() {
                List<TextField> textFields = browser().find("#group1")//
                    .find(TextField.class)//
                    .manyBy(".groupTextField")//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFieldsWithBySelector() {
                List<TextField> textFields = browser().find("#group1")//
                    .find(TextField.class)//
                    .manyBy(css(".groupTextField"))//
                    .collect(toList());
                verifyPageFragmentsWereFound(textFields, 5);
            }

            @Test
            void findingManyTextFieldsWithDefaultCssSelectorAndFilterThem() {
                List<TextField> filteredTextFields = browser().find("#group1")
                    .find(TextField.class)
                    .manyBy(".groupTextField")
                    .filter(has(visibleText("foo")))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

            @Test
            void findingManyTextFieldsWithBySelectorAndFilterThem() {
                List<TextField> filteredTextFields = browser().find("#group1")
                    .find(TextField.class)
                    .manyBy(css(".groupTextField"))
                    .filter(has(visibleText("foo")))
                    .collect(toList());
                verifyPageFragmentsWereFound(filteredTextFields, 2);
            }

        }

    }

    Page page() {
        return create(Page.class);
    }

    void verifyPageFragmentWasFound(PageFragment fragment) throws NoSuchElementException {
        // getting the web element will throw an exception if not found!
        fragment.webElement();
    }

    void verifyPageFragmentsWereFound(List<? extends PageFragment> fragments, int expectedSize)
        throws NoSuchElementException {
        assertThat(fragments).hasSize(expectedSize);
        // getting the web element will throw an exception if not found!
        fragments.forEach(PageFragment::webElement);
    }

}
