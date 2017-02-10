package info.novatec.testit.webtester.pagefragments;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Named;
import info.novatec.testit.webtester.pages.Page;


public class PageFragmentIntTest extends BaseIntTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/pagefragments/pageFragment.html";
    }

    @Before
    public void setUp() {
        page = create(TestPage.class);
    }

    /* get web element */

    @Test
    public void webElementCanBeRetrieved() {
        WebElement webElement = page.pageFragment().webElement();
        assertThat(webElement).isNotNull();
    }

    @Test
    public void webElementIsLazyLoaded() {
        PageFragment unknown = page.unknown(); // no exception at creation
        try {
            unknown.webElement(); // exception when getting web element
            Assert.fail("NoSuchElementException should have been thrown!");
        } catch (NoSuchElementException e){
            assertThat(e).hasMessageContaining("Unable to locate element:");
        }
    }

    /* get optional name */

    @Test
    public void namedPageFragmentsReturnTheirName() {
        Optional<String> name = page.namedPageFragment().getName();
        assertThat(name).contains("Page Fragment");
    }

    @Test
    public void defaultNameIsGeneratedFromIdentification() {
        Optional<String> name = page.pageFragment().getName();
        assertThat(name).contains("PageFragment identified by CssSelector: #pageFragment");
    }

    @Test
    public void collectionsOfPageFragmentsDoNotHaveNames() {
        Optional<String> name = page.pageFragments().findFirst().get().getName();
        assertThat(name).isEmpty();
    }

    /* get tag name */

    @Test
    public void nameOfPageFragmentsTagCanBeRead() {
        String name = page.pageFragment().getTagName();
        assertThat(name).isEqualTo("div");
    }

    /* get visible text */

    @Test
    public void textOfVisiblePageFragmentsCanBeRead() {
        String text = page.pageFragment().getVisibleText();
        assertThat(text).isEqualTo("This is a page fragment (DIV)");
    }

    @Test
    public void textOfInvisiblePageFragmentsIsEmptyString() {
        String text = page.invisiblePageFragment().getVisibleText();
        assertThat(text).isEqualTo("");
    }

    /* get attributes */

    @Test
    public void attributesCanBeRead() {
        Optional<String> value = page.withAttributes().getAttribute("foo");
        assertThat(value).contains("hello world");
    }

    @Test
    public void emptyExistingAttributesAreReadAsEmptyString() {
        Optional<String> value = page.withAttributes().getAttribute("bar");
        assertThat(value).contains("");
    }

    @Test
    public void nonExistingAttributesAreReturnedAsEmptyOptionals() {
        Optional<String> value = page.withAttributes().getAttribute("xur");
        assertThat(value).isEmpty();
    }

    /* set attribute */

    @Test
    public void setAttribute() {
        TextField textField = page.textField();
        textField.setAttribute("value", "hello world!");
        assertThat(textField.getText()).isEqualTo("hello world!");
    }

    @Test
    public void setAttributeWithCharactersThatNeedEscaping() {
        TextField textField = page.textField();
        textField.setAttribute("value", "hello \"world!\"");
        assertThat(textField.getText()).isEqualTo("hello \"world!\"");
    }

    /* get CSS values */

    @Test
    public void cssPropertiesCanBeRead() {
        Optional<String> value = page.withStyle().getCssValue("text-decoration");
        assertThat(value).contains("underline");
    }

    @Test
    public void unknownCssPropertiesAreReturnedAsEmptyOptional() {
        Optional<String> value = page.withStyle().getCssValue("unknown-property");
        assertThat(value).isEmpty();
    }

    /* visibility */

    @Test
    public void visibilityOfVisibleFragmentsIsReturnedAsTrue() {
        boolean visible = page.pageFragment().isVisible();
        assertThat(visible).isTrue();
    }

    @Test
    public void visibilityOfInvisibleFragmentsIsReturnedAsFalse() {
        boolean visible = page.invisiblePageFragment().isVisible();
        assertThat(visible).isFalse();
    }

    /* invisibility */

    @Test
    public void invisibilityOfVisibleFragmentsIsReturnedAsFalse() {
        boolean visible = page.pageFragment().isInvisible();
        assertThat(visible).isFalse();
    }

    @Test
    public void invisibilityOfInvisibleExistingFragmentsIsReturnedAsTrue() {
        boolean visible = page.invisiblePageFragment().isInvisible();
        assertThat(visible).isTrue();
    }

    @Test(expected = NoSuchElementException.class)
    public void invisibilityOfNonExistingFragmentThrowsException() {
        boolean visible = page.unknown().isInvisible();
        assertThat(visible).isFalse();
    }

    /* presence */

    @Test
    public void presenceOfExistingFragmentsIsReturnedAsTrue() {
        boolean present = page.pageFragment().isPresent();
        assertThat(present).isTrue();
    }

    @Test
    public void presenceOfNonExistingFragmentsIsReturnedAsFalse() {
        boolean present = page.unknown().isPresent();
        assertThat(present).isFalse();
    }

    /* not presence */

    @Test
    public void nonPresenceOfExistingFragmentsIsReturnedAsFalse() {
        boolean present = page.pageFragment().isNotPresent();
        assertThat(present).isFalse();
    }

    @Test
    public void nonPresenceOfNonExistingFragmentsIsReturnedAsTrue() {
        boolean present = page.unknown().isNotPresent();
        assertThat(present).isTrue();
    }

    /* enabled */

    @Test
    public void enabledStateOfEnabledFragmentsIsReturnedAsTrue() {
        boolean enabled = page.enabled().isEnabled();
        assertThat(enabled).isTrue();
    }

    @Test
    public void enabledStateOfDisabledFragmentsIsReturnedAsFalse() {
        boolean enabled = page.disabled().isEnabled();
        assertThat(enabled).isFalse();
    }

    /* disabled */

    @Test
    public void disabledStateOfEnabledFragmentsIsReturnedAsFalse() {
        boolean enabled = page.enabled().isDisabled();
        assertThat(enabled).isFalse();
    }

    @Test
    public void disabledStateOfDisabledFragmentsIsReturnedAsTrue() {
        boolean enabled = page.disabled().isDisabled();
        assertThat(enabled).isTrue();
    }

    private interface TestPage extends Page {

        @IdentifyUsing("#unknown")
        PageFragment unknown();

        @IdentifyUsing("#pageFragment")
        PageFragment pageFragment();
        @Named("Page Fragment")
        @IdentifyUsing("#pageFragment")
        PageFragment namedPageFragment();
        @IdentifyUsing("#invisiblePageFragment")
        PageFragment invisiblePageFragment();
        @IdentifyUsing("#withAttributes")
        PageFragment withAttributes();
        @IdentifyUsing("#withStyle")
        PageFragment withStyle();

        @IdentifyUsing("#enabled")
        PageFragment enabled();
        @IdentifyUsing("#disabled")
        PageFragment disabled();

        @IdentifyUsing("#textField")
        TextField textField();

        @IdentifyUsing("#pageFragment")
        Stream<PageFragment> pageFragments();

    }

}
