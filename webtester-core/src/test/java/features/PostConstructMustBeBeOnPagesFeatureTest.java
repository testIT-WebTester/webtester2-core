package features;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.conditions.pagefragments.Present;
import info.novatec.testit.webtester.conditions.pagefragments.Visible;
import info.novatec.testit.webtester.internal.must.MustConditionException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.PostConstructMustBe;
import info.novatec.testit.webtester.pages.Page;


public class PostConstructMustBeBeOnPagesFeatureTest extends BaseIntegrationTest {

    @Before
    public void openPage() {
        open("html/features/must-be.html");
    }

    @Test
    public void demonstratePassingMustBeBehaviour() {
        browser().create(PassingFeaturePage.class);
    }

    @Test(expected = MustConditionException.class)
    public void demonstrateFailingMustBeBehaviour() {
        browser().create(FailingFeaturePage.class);
    }

    @Test(expected = MustConditionException.class)
    public void demonstrateInheritanceFailingMustBeBehaviour() {
        browser().create(InheritedFailingFeaturePage.class);
    }

    /* test pages */

    public interface PassingFeaturePage extends Page {

        @PostConstructMustBe(Visible.class)
        @IdentifyUsing("#username")
        TextField username();

    }

    public interface InheritedFailingFeaturePage extends FailingFeaturePage {
        // no own @PostConstructMustBe condition - it should fail because of inheritance
    }

    public interface FailingFeaturePage extends Page {

        @PostConstructMustBe(Present.class)
        @IdentifyUsing("#unknown")
        PageFragment unknown();

    }

}
