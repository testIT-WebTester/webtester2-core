package features;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.WaitUntil;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.waiting.TimeoutException;


public class WaitUntilAnnotationFeatureTest extends BaseIntegrationTest {

    @Before
    public void openPage() {
        open("html/features/wait-annotation.html");
    }

    @Test
    public void demonstrateWaitUntilVisible() {

        FeaturePage page = browser().create(FeaturePage.class);

        // waits until button is visible
        long initialWait = measure(page::becomesVisible);
        assertThat(initialWait).isGreaterThan(500);

        // button is already visible
        long afterDisplayed = measure(page::becomesVisible);
        assertThat(afterDisplayed).isLessThan(500);

    }

    @Test(expected = TimeoutException.class)
    public void demonstrateWaitException() {
        create(FeaturePage.class).neverPresent();
    }

    private long measure(Runnable runnable) {
        long start = System.currentTimeMillis();
        runnable.run();
        return System.currentTimeMillis() - start;
    }

    /* test pages */

    public interface FeaturePage extends Page {

        @WaitUntil(Visible.class)
        @IdentifyUsing("#becomesVisible")
        Button becomesVisible();

        @WaitUntil(Present.class)
        @IdentifyUsing("#unknown")
        Button neverPresent();

    }

}
