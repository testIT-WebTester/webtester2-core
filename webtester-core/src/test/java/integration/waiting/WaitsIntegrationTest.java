package integration.waiting;

import static info.novatec.testit.webtester.conditions.Conditions.invisible;
import static info.novatec.testit.webtester.conditions.Conditions.present;
import static info.novatec.testit.webtester.conditions.Conditions.visible;
import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.waiting.TimeoutException;
import info.novatec.testit.webtester.waiting.Wait;


public class WaitsIntegrationTest extends BaseIntegrationTest {

    WaitsTestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/utils/waits.html";
    }

    @Before
    public void page() {
        page = browser().create(WaitsTestPage.class);
    }

    /* testWaitUntilPresent */

    @Test
    public final void testWaitUntilPresent_ElementIsPresent_ElementFound() {
        Wait.until(page.spanIsPresent()).is(present());
    }

    @Test(expected = TimeoutException.class)
    public final void testWaitUntilPresent_ElementIsNotPresent_Exception() {
        Wait.until(page.spanIsNotPresent()).is(present());
    }

    /* testWaitUntilVisible */

    @Test
    public final void testWaitUntilVisible_ElementIsVisible_ElementFound() {
        Wait.until(page.spanIsVisible()).is(visible());
    }

    @Test(expected = TimeoutException.class)
    public final void testWaitUntilVisible_ElementIsNotVisible_Exception() {
        Wait.until(page.spanIsNotVisible()).is(visible());
    }

    /* testWaitUntilInvisible */

    @Test
    public final void testWaitUntilInvisible_ElementIsInvisible_ElementFound() {
        Wait.until(page.spanIsNotVisible()).is(invisible());
    }

    @Test(expected = TimeoutException.class)
    public final void testWaitUntilInvisible_ElementIsNotInvisible_Exception() {
        Wait.until(page.spanIsVisible()).is(invisible());
    }

    /* UTILITIES */

    public interface WaitsTestPage extends Page {

        @IdentifyUsing("#spanIsPresent")
        PageFragment spanIsPresent();

        @IdentifyUsing("#spanIsNotPresent")
        PageFragment spanIsNotPresent();

        @IdentifyUsing("#spanIsVisible")
        PageFragment spanIsVisible();

        @IdentifyUsing("#spanIsNotVisible")
        PageFragment spanIsNotVisible();

        @PostConstruct
        default void checkThatButtonsHaveCorrectStateForTest() {
            assertThat(spanIsVisible().isVisible()).isTrue();
            assertThat(spanIsNotVisible().isVisible()).isFalse();
        }

    }

}
