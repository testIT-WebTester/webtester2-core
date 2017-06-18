package info.novatec.testit.webtester.pagefragments.annotations;

import org.junit.Before;
import org.junit.Test;

import utils.integration.BaseIntTest;

import info.novatec.testit.webtester.conditions.pagefragments.PresentAndVisible;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.internal.postconstruct.PostConstructMustBeConditionException;
import info.novatec.testit.webtester.pagefragments.Button;
import info.novatec.testit.webtester.pages.Page;


public class PostConstructMustBeWithWaitUntilIntTest extends BaseIntTest {

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

    @Test(expected = PostConstructMustBeConditionException.class)
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
