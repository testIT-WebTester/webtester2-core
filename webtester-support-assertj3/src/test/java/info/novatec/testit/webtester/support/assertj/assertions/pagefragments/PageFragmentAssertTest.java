package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PageFragmentAssertTest {

    @Nested
    class HasTagAssertion {

        @Test
        void passesForMatchingTag() {
            assertThat(pageFragmentWithTag("div")).hasTag("div");
        }

        @Test
        void failsForDifferentTag() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithTag("span")).hasTag("div");
            });
            assertThat(exception).hasMessage("Expected page fragment to have tag <div>, but was <span>.");
        }

    }

    @Nested
    class HasNotTagAssertion {

        @Test
        void passesForDifferentTag() {
            assertThat(pageFragmentWithTag("span")).hasNotTag("div");
        }

        @Test
        void failsForMatchingTag() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithTag("div")).hasNotTag("div");
            });
            assertThat(exception).hasMessage("Expected page fragment to not to have tag <div>, but it did.");
        }

    }

    @Nested
    class HasVisibleTextAssertion {

        @Test
        void passesForMatchingVisibleText() {
            assertThat(pageFragmentWithVisibleText("foo")).hasVisibleText("foo");
        }

        @Test
        void failsForDifferentVisibleText() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithVisibleText("bar")).hasVisibleText("foo");
            });
            assertThat(exception).hasMessage("Expected page fragment's visible text to be <foo>, but was <bar>.");
        }

    }

    @Nested
    class HasNotVisibleTextAssertion {

        @Test
        void passesForDifferentVisibleText() {
            assertThat(pageFragmentWithVisibleText("bar")).hasNotVisibleText("foo");
        }

        @Test
        void failsForMatchingVisibleText() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithVisibleText("foo")).hasNotVisibleText("foo");
            });
            assertThat(exception).hasMessage("Expected page fragment's visible text not to be <foo>, but it was.");
        }

    }

    @Nested
    class HasVisibleTextContainingAssertion {

        @Test
        void passesIfTextIsContained() {
            assertThat(pageFragmentWithVisibleText("foo bar foo")).hasVisibleTextContaining("foo");
        }

        @Test
        void failsIfTextIsNotContained() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithVisibleText("bar bar")).hasVisibleTextContaining("foo");
            });
            assertThat(exception).hasMessage("Expected page fragment's visible text to contain <foo>, but it didn't.");
        }

    }

    @Nested
    class HasAttributeAssertion {

        @Test
        void passesIfAttributeExists() {
            assertThat(pageFragmentWithAttribute("foo")).hasAttribute("foo");
        }

        @Test
        void failsIfAttributeDoesNotExist() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithoutAttributes()).hasAttribute("foo");
            });
            assertThat(exception).hasMessage("Expected page fragment to have attribute <foo>, but it didn't.");
        }

    }

    @Nested
    class HasNotAttributeAssertion {

        @Test
        void passesIfAttributeDoesNotExist() {
            assertThat(pageFragmentWithoutAttributes()).hasNotAttribute("bar");
        }

        @Test
        void failsIfAttributeExists() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithAttribute("bar")).hasNotAttribute("bar");
            });
            assertThat(exception).hasMessage("Expected page fragment not to have attribute <bar>, but it did.");
        }

    }

    @Nested
    class HasAttributeValueAssertion {

        @Test
        void passesIfAttributeHasMatchingValue() {
            assertThat(pageFragmentWithAttributeAndValue("foo", "val")).hasAttributeValue("foo", "val");
        }

        @Test
        void failsIfAttributeHasDifferentValue() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithAttributeAndValue("foo", "other")).hasAttributeValue("foo", "val");
            });
            assertThat(exception).hasMessage("Expected page fragment's <foo> attribute value to be <val>, but it was <other>.");
        }

        @Test
        void failsIfAttributeDoesNotExist() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithoutAttributes()).hasAttributeValue("foo", "val");
            });
            assertThat(exception).hasMessage("Expected page fragment to have attribute <foo>, but it didn't.");
        }

    }

    @Nested
    class IsVisibleAssertion {

        @Test
        void passesForVisibleFragment() {
            assertThat(visiblePageFragment()).isVisible();
        }

        @Test
        void failsForInvisibleFragment() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(invisiblePageFragment()).isVisible();
            });
            assertThat(exception).hasMessage("Expected page fragment to be visible, but it wasn't.");
        }

    }

    @Nested
    class IsInvisibleAssertion {

        @Test
        void passesForInvisibleFragment() {
            assertThat(invisiblePageFragment()).isInvisible();
        }

        @Test
        void failsForVisibleFragment() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(visiblePageFragment()).isInvisible();
            });
            assertThat(exception).hasMessage("Expected page fragment to be invisible, but it wasn't.");
        }

    }

    @Nested
    class IsPresentAssertion {

        @Test
        void passesForExistingFragment() {
            assertThat(presentPageFragment()).isPresent();
        }

        @Test
        void failsForNonExistingFragment() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(nonPresentFragment()).isPresent();
            });
            assertThat(exception).hasMessage("Expected page fragment to be present, but it wasn't.");
        }

    }

    @Nested
    class IsNotPresentAssertion {

        @Test
        void passesForNonExistingFragment() {
            assertThat(nonPresentFragment()).isNotPresent();
        }

        @Test
        void failsForExistingFragment() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(presentPageFragment()).isNotPresent();
            });
            assertThat(exception).hasMessage("Expected page fragment not to be present, but it was.");
        }

    }

    @Nested
    class IsEnabledAssertion {

        @Test
        void passesForEnabledFragment() {
            assertThat(enabledPageFragment()).isEnabled();
        }

        @Test
        void failsForDisabledFragment() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(disabledPageFragment()).isEnabled();
            });
            assertThat(exception).hasMessage("Expected page fragment to be enabled, but it wasn't.");
        }

    }

    @Nested
    class IsDisabledAssertion {

        @Test
        void passesForDisabledFragment() {
            assertThat(disabledPageFragment()).isDisabled();
        }

        @Test
        void failsForEnabledFragment() {
            AssertionError exception = assertThrows(AssertionError.class, () -> {
                assertThat(enabledPageFragment()).isDisabled();
            });
            assertThat(exception).hasMessage("Expected page fragment to be disabled, but it wasn't.");
        }

    }

    @Nested
    class AssertionsProvideFluentApi {

        @Test
        void hasTag() {
            PageFragmentAssert original = assertThat(pageFragmentWithTag("div"));
            PageFragmentAssert returned = original.hasTag("div");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNotTag() {
            PageFragmentAssert original = assertThat(pageFragmentWithTag("span"));
            PageFragmentAssert returned = original.hasNotTag("div");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasVisibleText() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("foo"));
            PageFragmentAssert returned = original.hasVisibleText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNotVisibleText() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("bar"));
            PageFragmentAssert returned = original.hasNotVisibleText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasVisibleTextContaining() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("foo bar foo"));
            PageFragmentAssert returned = original.hasVisibleTextContaining("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasAttribute() {
            PageFragmentAssert original = assertThat(pageFragmentWithAttribute("foo"));
            PageFragmentAssert returned = original.hasAttribute("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasNotAttribute() {
            PageFragmentAssert original = assertThat(pageFragmentWithoutAttributes());
            PageFragmentAssert returned = original.hasNotAttribute("bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void hasAttributeValue() {
            PageFragmentAssert original = assertThat(pageFragmentWithAttributeAndValue("foo", "val"));
            PageFragmentAssert returned = original.hasAttributeValue("foo", "val");
            assertThat(returned).isSameAs(original);
        }

        @Test
        void isVisible() {
            PageFragmentAssert original = assertThat(visiblePageFragment());
            PageFragmentAssert returned = original.isVisible();
            assertThat(returned).isSameAs(original);
        }

        @Test
        void isInvisible() {
            PageFragmentAssert original = assertThat(invisiblePageFragment());
            PageFragmentAssert returned = original.isInvisible();
            assertThat(returned).isSameAs(original);
        }

        @Test
        void isPresent() {
            PageFragmentAssert original = assertThat(presentPageFragment());
            PageFragmentAssert returned = original.isPresent();
            assertThat(returned).isSameAs(original);
        }

        @Test
        void isNotPresent() {
            PageFragmentAssert original = assertThat(nonPresentFragment());
            PageFragmentAssert returned = original.isNotPresent();
            assertThat(returned).isSameAs(original);
        }

        @Test
        void isEnabled() {
            PageFragmentAssert original = assertThat(enabledPageFragment());
            PageFragmentAssert returned = original.isEnabled();
            assertThat(returned).isSameAs(original);
        }

        @Test
        void isDisabled() {
            PageFragmentAssert original = assertThat(disabledPageFragment());
            PageFragmentAssert returned = original.isDisabled();
            assertThat(returned).isSameAs(original);
        }

    }

    PageFragment pageFragmentWithTag(String tagName) {
        return MockFactory.fragment().withTagName(tagName).build();
    }

    PageFragment pageFragmentWithVisibleText(String text) {
        return MockFactory.fragment().withVisibleText(text).build();
    }

    PageFragment pageFragmentWithAttribute(String attributeName) {
        return MockFactory.fragment().withAttribute(attributeName).build();
    }

    PageFragment pageFragmentWithAttributeAndValue(String attributeName, String value) {
        return MockFactory.fragment().withAttribute(attributeName, value).build();
    }

    PageFragment pageFragmentWithoutAttributes() {
        return MockFactory.fragment().build();
    }

    PageFragment visiblePageFragment() {
        return MockFactory.fragment().visible().build();
    }

    PageFragment invisiblePageFragment() {
        return MockFactory.fragment().invisible().build();
    }

    PageFragment nonPresentFragment() {
        return MockFactory.fragment().notPresent().build();
    }

    PageFragment presentPageFragment() {
        return MockFactory.fragment().present().build();
    }

    PageFragment enabledPageFragment() {
        return MockFactory.fragment().enabled().build();
    }

    PageFragment disabledPageFragment() {
        return MockFactory.fragment().disabled().build();
    }

}
