package info.novatec.testit.webtester.pagefragments.annotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.waiting.TimeoutException;


public class WaitUntilIntTest extends BaseIntTest {

    @Before
    public void openPage() {
        open("html/features/wait-annotation.html");
    }

    @Test
    public void demonstrateWaitUntilVisible() {
        FeaturePage page = browser().create(FeaturePage.class);
        assertThat(page.becomesVisibleLater().isVisible());
    }

    @Test(expected = TimeoutException.class)
    public void demonstrateWaitException() {
        create(FeaturePage.class).neverPresent();
    }

    /* test pages */

    public interface FeaturePage extends Page {

        @WaitUntil(Visible.class)
        @IdentifyUsing("#becomesVisibleLater")
        Button becomesVisibleLater();

        @WaitUntil(value = Present.class, timeout = 50, unit = TimeUnit.MILLISECONDS)
        @IdentifyUsing("#unknown")
        Button neverPresent();

    }

}
