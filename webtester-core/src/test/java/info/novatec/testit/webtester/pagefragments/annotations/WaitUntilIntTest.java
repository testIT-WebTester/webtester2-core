package info.novatec.testit.webtester.pagefragments.annotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.conditions.collections.NotEmpty;
import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.internal.exceptions.IllegalSignatureException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pages.Page;
import info.novatec.testit.webtester.waiting.TimeoutException;


class WaitUntilIntTest extends BaseIntTest {

    @BeforeEach
    void openPage() {
        open("html/features/wait-annotation.html");
    }

    @Test
    void waitIsExecutedUntilConditionIsMet_PageFragment() {
        FeaturePage page = browser().create(FeaturePage.class);
        assertThat(page.becomesVisibleLater().isVisible());
    }

    @Test
    void waitIsExecutedUntilConditionIsMet_Collection() {
        CollectionsPage page = browser().create(CollectionsPage.class);
        assertThat(!page.becomeVisibleLater().isEmpty());
    }

    @Test
    void timeoutExceptionIsThrownIfElementNeverMatches_PageFragment() {
        assertThrows(TimeoutException.class, () -> {
            create(FeaturePage.class).neverPresent();
        });
    }

    @Test
    void timeoutExceptionIsThrownIfElementNeverMatches_Collection() {
        assertThrows(TimeoutException.class, () -> {
            create(CollectionsPage.class).neverPresent();
        });
    }

    @Test
    void exceptionInCaseTheConditionDoesNotSupportTheMethodsReturnType() {
        assertThrows(IllegalSignatureException.class, () -> {
            create(CollectionsPage.class).wrongConditionType();
        });
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

    public interface CollectionsPage extends Page {

        @WaitUntil(NotEmpty.class)
        @IdentifyUsing("#becomesVisibleLater")
        List<Button> becomeVisibleLater();

        @WaitUntil(Visible.class)
        @IdentifyUsing("#becomesVisibleLater")
        List<Button> wrongConditionType();

        @WaitUntil(value = NotEmpty.class, timeout = 50, unit = TimeUnit.MILLISECONDS)
        @IdentifyUsing("#unknown")
        List<Button> neverPresent();

    }

}
