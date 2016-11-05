package info.novatec.testit.webtester.support.assertj.assertions.pagefragments;

import static info.novatec.testit.webtester.support.assertj.WebTesterAssertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.expectThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import utils.MockFactory;

import info.novatec.testit.webtester.pagefragments.PageFragment;


public class PageFragmentAssertTest {

    @Nested
    @DisplayName("hasTag(..) can be asserted")
    class HasTag {

        @Test
        @DisplayName("having matching tag passes")
        void havingTagPasses() {
            assertThat(pageFragmentWithTag("div")).hasTag("div");
        }

        @Test
        @DisplayName("having a different tag fails")
        void notHavingTagFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithTag("span")).hasTag("div");
            })).hasMessage("Expected page fragment to have tag <div>, but was <span>.");
        }

    }

    @Nested
    @DisplayName("hasNotTag(..) can be asserted")
    class HasNotTag {

        @Test
        @DisplayName("having a different tag passes")
        void notHavingTagPasses() {
            assertThat(pageFragmentWithTag("span")).hasNotTag("div");
        }

        @Test
        @DisplayName("having matching tag fails")
        void havingTagFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithTag("div")).hasNotTag("div");
            })).hasMessage("Expected page fragment to not to have tag <div>, but it did.");
        }

    }

    @Nested
    @DisplayName("hasVisibleText(..) can be asserted")
    class HasVisibleText {

        @Test
        @DisplayName("having matching visible text passes")
        void havingVisibleTextPasses() {
            assertThat(pageFragmentWithVisibleText("foo")).hasVisibleText("foo");
        }

        @Test
        @DisplayName("having different visible text fails")
        void notHavingVisibleTextFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithVisibleText("bar")).hasVisibleText("foo");
            })).hasMessage("Expected page fragment's visible text to be <foo>, but was <bar>.");
        }

    }

    @Nested
    @DisplayName("hasNotVisibleText(..) can be asserted")
    class HasNotVisibleText {

        @Test
        @DisplayName("having different visible text passes")
        void notHavingVisibleTextPasses() {
            assertThat(pageFragmentWithVisibleText("bar")).hasNotVisibleText("foo");
        }

        @Test
        @DisplayName("having matching visible text fails")
        void havingVisibleTextFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithVisibleText("foo")).hasNotVisibleText("foo");
            })).hasMessage("Expected page fragment's visible text not to be <foo>, but it was.");
        }

    }

    @Nested
    @DisplayName("hasVisibleTextContaining(..) can be asserted")
    class HasVisibleTextContaining {

        @Test
        @DisplayName("having visible text containing the part passes")
        void havingVisibleTextContainingPasses() {
            assertThat(pageFragmentWithVisibleText("foo bar foo")).hasVisibleTextContaining("foo");
        }

        @Test
        @DisplayName("having visible text that does not contain the part fails")
        void notHavingVisibleTextContainingFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithVisibleText("bar bar")).hasVisibleTextContaining("foo");
            })).hasMessage("Expected page fragment's visible text to contain <foo>, but it didn't.");
        }

    }

    @Nested
    @DisplayName("hasAttribute(..) can be asserted")
    class HasAttribute {

        @Test
        @DisplayName("having the attribute passes")
        void havingAttributePasses() {
            assertThat(pageFragmentWithAttribute("foo")).hasAttribute("foo");
        }

        @Test
        @DisplayName("note having the attribute fails")
        void notHavingAttributeFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithoutAttributes()).hasAttribute("foo");
            })).hasMessage("Expected page fragment to have attribute <foo>, but it didn't.");
        }

    }

    @Nested
    @DisplayName("hasNotAttribute(..) can be asserted")
    class HasNotAttribute {

        @Test
        @DisplayName("note having the attribute passes")
        void notHavingAttributePasses() {
            assertThat(pageFragmentWithoutAttributes()).hasNotAttribute("bar");
        }

        @Test
        @DisplayName("having the attribute fails")
        void havingAttributeFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithAttribute("bar")).hasNotAttribute("bar");
            })).hasMessage("Expected page fragment not to have attribute <bar>, but it did.");
        }

    }

    @Nested
    @DisplayName("hasAttributeValue(..) can be asserted")
    class HasAttributeValue {

        @Test
        @DisplayName("having the attribute with matching value passes")
        void havingAttributeWithValuePasses() {
            assertThat(pageFragmentWithAttributeAndValue("foo", "val")).hasAttributeValue("foo", "val");
        }

        @Test
        @DisplayName("having the attribute with different value fails")
        void havingAttributeWithDifferentValueFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithAttributeAndValue("foo", "other")).hasAttributeValue("foo", "val");
            })).hasMessage("Expected page fragment's <foo> attribute value to be <val>, but it was <other>.");
        }

        @Test
        @DisplayName("not having the attribute fails")
        void notHavingAttributeFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(pageFragmentWithoutAttributes()).hasAttributeValue("foo", "val");
            })).hasMessage("Expected page fragment to have attribute <foo>, but it didn't.");
        }

    }

    @Nested
    @DisplayName("isVisible() can be asserted")
    class IsVisible {

        @Test
        @DisplayName("being visible passes")
        void beingVisiblePasses() {
            assertThat(visiblePageFragment()).isVisible();
        }

        @Test
        @DisplayName("being invisible fails")
        void notBeingVisibleFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(invisiblePageFragment()).isVisible();
            })).hasMessage("Expected page fragment to be visible, but it wasn't.");
        }

    }

    @Nested
    @DisplayName("isInvisible() can be asserted")
    class IsInvisible {

        @Test
        @DisplayName("being invisible passes")
        void beingInvisiblePasses() {
            assertThat(invisiblePageFragment()).isInvisible();
        }

        @Test
        @DisplayName("being visible fails")
        void notBeingInvisibleFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(visiblePageFragment()).isInvisible();
            })).hasMessage("Expected page fragment to be invisible, but it wasn't.");
        }

    }

    @Nested
    @DisplayName("isPresent() can be asserted")
    class IsPresent {

        @Test
        @DisplayName("being present passes")
        void beingPresentPasses() {
            assertThat(presentPageFragment()).isPresent();
        }

        @Test
        @DisplayName("not being present fails")
        void notBeingPresentFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(nonPresentFragment()).isPresent();
            })).hasMessage("Expected page fragment to be present, but it wasn't.");
        }

    }

    @Nested
    @DisplayName("isNotPresent() can be asserted")
    class IsNotPresent {

        @Test
        @DisplayName("not being present passes")
        void notBeingPresentPasses() {
            assertThat(nonPresentFragment()).isNotPresent();
        }

        @Test
        @DisplayName("being present fails")
        void beingPresentFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(presentPageFragment()).isNotPresent();
            })).hasMessage("Expected page fragment not to be present, but it was.");
        }

    }

    @Nested
    @DisplayName("isEnabled() can be asserted")
    class IsEnabled {

        @Test
        @DisplayName("being enabled passes")
        void beingEnabledPasses() {
            assertThat(enabledPageFragment()).isEnabled();
        }

        @Test
        @DisplayName("being disabled fails")
        void notBeingEnabledFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(disabledPageFragment()).isEnabled();
            })).hasMessage("Expected page fragment to be enabled, but it wasn't.");
        }

    }

    @Nested
    @DisplayName("isDisabled() can be asserted")
    class IsDisabled {

        @Test
        @DisplayName("being disabled passes")
        void beingDisabledPasses() {
            assertThat(disabledPageFragment()).isDisabled();
        }

        @Test
        @DisplayName("being enabled fails")
        void notBeingDisabledFails() {
            assertThat(expectThrows(AssertionError.class, () -> {
                assertThat(enabledPageFragment()).isDisabled();
            })).hasMessage("Expected page fragment to be disabled, but it wasn't.");
        }

    }

    @Nested
    @DisplayName("assertions provide fluent API")
    class AssertionProvidesFluentApi {

        @Test
        @DisplayName("hasTag(..)")
        void hasTag() {
            PageFragmentAssert original = assertThat(pageFragmentWithTag("div"));
            PageFragmentAssert returned = original.hasTag("div");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasNotTag(..)")
        void hasNotTag() {
            PageFragmentAssert original = assertThat(pageFragmentWithTag("span"));
            PageFragmentAssert returned = original.hasNotTag("div");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasVisibleText(..)")
        void hasVisibleText() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("foo"));
            PageFragmentAssert returned = original.hasVisibleText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasNotVisibleText(..)")
        void hasNotVisibleText() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("bar"));
            PageFragmentAssert returned = original.hasNotVisibleText("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasVisibleTextContaining(..)")
        void hasVisibleTextContaining() {
            PageFragmentAssert original = assertThat(pageFragmentWithVisibleText("foo bar foo"));
            PageFragmentAssert returned = original.hasVisibleTextContaining("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasAttribute(..)")
        void hasAttribute() {
            PageFragmentAssert original = assertThat(pageFragmentWithAttribute("foo"));
            PageFragmentAssert returned = original.hasAttribute("foo");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasNotAttribute(..)")
        void hasNotAttribute() {
            PageFragmentAssert original = assertThat(pageFragmentWithoutAttributes());
            PageFragmentAssert returned = original.hasNotAttribute("bar");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("hasAttributeValue(..)")
        void hasAttributeValue() {
            PageFragmentAssert original = assertThat(pageFragmentWithAttributeAndValue("foo", "val"));
            PageFragmentAssert returned = original.hasAttributeValue("foo", "val");
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("isVisible()")
        void isVisible() {
            PageFragmentAssert original = assertThat(visiblePageFragment());
            PageFragmentAssert returned = original.isVisible();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("isInvisible()")
        void isInvisible() {
            PageFragmentAssert original = assertThat(invisiblePageFragment());
            PageFragmentAssert returned = original.isInvisible();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("isPresent()")
        void isPresent() {
            PageFragmentAssert original = assertThat(presentPageFragment());
            PageFragmentAssert returned = original.isPresent();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("isNotPresent()")
        void isNotPresent() {
            PageFragmentAssert original = assertThat(nonPresentFragment());
            PageFragmentAssert returned = original.isNotPresent();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("isEnabled()")
        void isEnabled() {
            PageFragmentAssert original = assertThat(enabledPageFragment());
            PageFragmentAssert returned = original.isEnabled();
            assertThat(returned).isSameAs(original);
        }

        @Test
        @DisplayName("isDisabled()")
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
