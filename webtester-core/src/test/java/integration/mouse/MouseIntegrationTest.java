package integration.mouse;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;
import utils.events.EventCaptor;

import info.novatec.testit.webtester.events.pagefragments.ClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.ContextClickedEvent;
import info.novatec.testit.webtester.events.pagefragments.DoubleClickedEvent;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.Div;
import info.novatec.testit.webtester.pagefragments.Image;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.mouse.Mouse;


public class MouseIntegrationTest extends BaseIntegrationTest {

    TestPage page;

    @Override
    protected String getHTMLFilePath() {
        return "html/utils/mouse.html";
    }

    @Before
    public void init() {
        page = create(TestPage.class);
    }

    /* clicks */

    @Test
    public void mouseClicksCanBeExecuted() {

        /* Click the single click button. */
        Mouse.click(page.clickSingleClickButton());

        /* The target button should now be displayed after. */
        assertThat(page.clickTargetButton().isVisible()).isTrue();

    }

    @Test
    public void clicksFireEvents() {
        EventCaptor.capture(eventSystem(), ClickedEvent.class)
            .execute(() -> Mouse.click(page.clickSingleClickButton()))
            .assertEventWasFired();
    }

    @Test
    public void mouseDoubleClicksCanBeExecuted() {

        /* Double click the double click button. */
        Mouse.doubleClick(page.clickDoubleClickButton());

        /* The target button should now be displayed after. */
        assertThat(page.clickTargetButton().isVisible()).isTrue();

    }

    @Test
    public void doubleClicksFireEvents() {
        EventCaptor.capture(eventSystem(), DoubleClickedEvent.class)
            .execute(() -> Mouse.doubleClick(page.clickDoubleClickButton()))
            .assertEventWasFired();
    }

    @Test
    public void mouseContextClicksCanBeExecuted() {

        /* Context click the context click button. */
        Mouse.contextClick(page.clickContextClickButton());

        /* The target button should now be displayed after. */
        assertThat(page.clickTargetButton().isVisible()).isTrue();

    }

    @Test
    public void contextClicksFireEvents() {
        EventCaptor.capture(eventSystem(), ContextClickedEvent.class)
            .execute(() -> Mouse.contextClick(page.clickContextClickButton()))
            .assertEventWasFired();
    }

    /* moves */

    @Test
    public void mouseCanBeMovedToSingleFragment() {

        /* Move to button. This will trigger the display of the next button. */
        Mouse.moveTo(page.moveToStartButton());

        /* Every button should be displayed after all moves are done. */
        assertThat(page.moveToStartButton().isVisible()).isTrue();
        assertThat(page.moveToInstantButton().isVisible()).isTrue();

    }

    @Test
    public void mouseCanBeMovedToDifferentFragmentsInSequence() {

        /* Move to each button. Every move will trigger the display of the next button. */
        Mouse.moveToEach(page.moveToStartButton(), page.moveToInstantButton(), page.moveToDelayedButton());

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

        @IdentifyUsing("#dragSource")
        Image dragSource();

        @IdentifyUsing("#dropTarget")
        Div dropTarget();

    }

}
