package info.novatec.testit.webtester.mouse;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.ContextClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.DoubleClickedEvent;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;


public class DefaultMouseDriverIntTest extends BaseIntTest {

    TestPage page;
    DefaultMouseDriver cut;

    @Override
    protected String getHTMLFilePath() {
        return "html/utils/mouse.html";
    }

    @Before
    public void init() {
        page = create(TestPage.class);
        cut = new DefaultMouseDriver();
    }

    /* clicks */

    @Test
    public void mouseClicksCanBeExecuted() {

        /* Click the single click button. */
        cut.click(page.clickSingleClickButton());

        /* The target button should now be displayed after. */
        assertThat(page.clickTargetButton().isVisible()).isTrue();

    }

    @Test
    public void clicksFireEvents() {
        EventCaptor.capture(eventSystem(), ClickedEvent.class)
            .execute(() -> cut.click(page.clickSingleClickButton()))
            .assertEventWasFired();
    }

    @Test
    public void mouseDoubleClicksCanBeExecuted() {

        /* Double click the double click button. */
        cut.doubleClick(page.clickDoubleClickButton());

        /* The target button should now be displayed after. */
        assertThat(page.clickTargetButton().isVisible()).isTrue();

    }

    @Test
    public void doubleClicksFireEvents() {
        EventCaptor.capture(eventSystem(), DoubleClickedEvent.class)
            .execute(() -> cut.doubleClick(page.clickDoubleClickButton()))
            .assertEventWasFired();
    }

    @Test
    public void mouseContextClicksCanBeExecuted() {

        /* Context click the context click button. */
        cut.contextClick(page.clickContextClickButton());

        /* The target button should now be displayed after. */
        assertThat(page.clickTargetButton().isVisible()).isTrue();

    }

    @Test
    public void contextClicksFireEvents() {
        EventCaptor.capture(eventSystem(), ContextClickedEvent.class)
            .execute(() -> cut.contextClick(page.clickContextClickButton()))
            .assertEventWasFired();
    }

    /* moves */

    @Test
    public void mouseCanBeMovedToSingleFragment() {

        /* Move to button. This will trigger the display of the next button. */
        cut.moveTo(page.moveToStartButton());

        /* Every button should be displayed after all moves are done. */
        assertThat(page.moveToStartButton().isVisible()).isTrue();
        assertThat(page.moveToInstantButton().isVisible()).isTrue();

    }

    @Test
    public void mouseCanBeMovedToDifferentFragmentsInSequence() {

        /* Move to each button. Every move will trigger the display of the next button. */
        cut.moveToEach(page.moveToStartButton(), page.moveToInstantButton(), page.moveToDelayedButton());

        /* Every button should be displayed after all moves are done. */
        assertThat(page.moveToStartButton().isVisible()).isTrue();
        assertThat(page.moveToInstantButton().isVisible()).isTrue();
        assertThat(page.moveToDelayedButton().isVisible()).isTrue();

    }

    public interface TestPage extends Page {

        @IdentifyUsing("#moveTo_startButton")
        Button moveToStartButton();

        @IdentifyUsing("#moveTo_instantButton")
        Button moveToInstantButton();

        @IdentifyUsing("#moveTo_delayedButton")
        Button moveToDelayedButton();

        @IdentifyUsing("#click_singleClickButton")
        Button clickSingleClickButton();

        @IdentifyUsing("#click_doubleClickButton")
        Button clickDoubleClickButton();

        @IdentifyUsing("#click_contextClickButton")
        Button clickContextClickButton();

        @IdentifyUsing("#click_targetButton")
        Button clickTargetButton();

    }

}
