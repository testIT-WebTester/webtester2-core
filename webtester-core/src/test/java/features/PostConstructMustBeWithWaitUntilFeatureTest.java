package features;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.conditions.pagefragments.PresentAndVisible;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.internal.must.MustConditionException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.PostConstructMustBe;
import info.novatec.testit.webtester.pagefragments.annotations.WaitUntil;
import info.novatec.testit.webtester.pages.Page;


public class PostConstructMustBeWithWaitUntilFeatureTest extends BaseIntegrationTest {

    @Before
    public void openPage() {
        open("html/features/must-be-with-wait.html");
    }

    /*
     * The #button is displayed with a slight delay.
     * Only when the @Wait is used will the must condition be executed positively.
     */

    @Test
    public void demonstratePassingMustBeBehaviour() {
        browser().create(PassingFeaturePage.class);
    }

    @Test(expected = MustConditionException.class)
    public void demonstrateFailingMustBeBehaviour() {
        browser().create(FailingFeaturePage.class);
    }

    /* test pages */

    public interface PassingFeaturePage extends Page {

        @PostConstructMustBe(Visible.class)
        @WaitUntil(PresentAndVisible.class)
        @IdentifyUsing("#button")
        Button button();

    }

    public interface FailingFeaturePage extends Page {

        @PostConstructMustBe(Visible.class)
        @IdentifyUsing("#button")
        Button button();

    }

}
