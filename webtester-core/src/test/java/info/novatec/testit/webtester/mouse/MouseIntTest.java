package info.novatec.testit.webtester.mouse;

import static info.novatec.testit.webtester.conditions.Conditions.equalTo;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.TestUtils.executeWithRetryOf;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import utils.integration.BaseIntTest;
import utils.integration.TestBrowserFactory;

import info.novatec.testit.webtester.browser.Browser;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.Div;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.waiting.Wait;

/** NOTE: This test will break other tests because of the context menu. It has to be executed in its own Browser! */
@Disabled("breaks current CI build")
public class MouseIntTest extends BaseIntTest {

    static Browser browser;

    FeaturePage page;

    @BeforeAll
    public static void setupBrowser() {
        // same method name as base class method for logical override in JUnit
        browser = new TestBrowserFactory().createBrowser();
    }

    @AfterAll
    static void closeIsolatedBrowser() {
        browser.close();
    }

    @Test
    void demonstrateOnPageFragment() {
        executeWithRetryOf(10, this::openPage, () -> {
            Mouse.on(page.firstButton())//
                .click()//
                .doubleClick()//
                .contextClick();

            Wait.untilSupplied(() -> page.getMessage(1)).is(equalTo("Context Clicked"));
            assertThat(page.getMessage(1)).isEqualTo("Context Clicked");
        });
    }

    @Test
    void demonstrateSequence() {
        executeWithRetryOf(10, this::openPage, () -> {
            Mouse.sequence()//
                .click(page.secondButton())//
                .moveTo(page.thirdButton())//
                .moveTo(page.firstButton())//
                .contextClick();

            assertThat(page.getMessage(1)).isEqualTo("Context Clicked");
            assertThat(page.getMessage(2)).isEqualTo("Clicked");
            assertThat(page.getMessage(3)).isEqualTo("Moved To");
        });
    }

    @Test
    void demonstrateMoveToEach() {
        executeWithRetryOf(10, this::openPage, () -> {
            // move mouse to each button in the given order
            Mouse.moveToEach(page.firstButton(), page.secondButton(), page.thirdButton());

            // assert mouse was moved
            assertThat(page.getMessage(1)).isEqualTo("Moved To");
            assertThat(page.getMessage(2)).isEqualTo("Moved To");
            assertThat(page.getMessage(3)).isEqualTo("Moved To");
        });
    }

    void openPage() {
        browser.open(getUrlFor("html/features/mouse-utils.html"));
        page = browser.create(FeaturePage.class);
    }

    public interface FeaturePage extends Page {

        @IdentifyUsing("#firstButton")
        Button firstButton();

        @IdentifyUsing("#secondButton")
        Button secondButton();

        @IdentifyUsing("#thirdButton")
        Button thirdButton();

        @IdentifyUsing("#div1")
        Div message1();

        @IdentifyUsing("#div2")
        Div message2();

        @IdentifyUsing("#div3")
        Div message3();

        default String getMessage(int number) {
            switch (number) {
                case 1:
                    return message1().getVisibleText();
                case 2:
                    return message2().getVisibleText();
                case 3:
                    return message3().getVisibleText();
                default:
                    throw new IllegalArgumentException("unknown message field: #" + number);
            }
        }

    }

}
