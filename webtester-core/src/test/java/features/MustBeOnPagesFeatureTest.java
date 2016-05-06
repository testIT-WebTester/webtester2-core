package features;

import org.junit.Before;
import org.junit.Test;

import integration.BaseIntegrationTest;

import info.novatec.testit.webtester.internal.must.MustConditionException;
import info.novatec.testit.webtester.pagefragments.PageFragment;
import info.novatec.testit.webtester.pagefragments.TextField;
import info.novatec.testit.webtester.pagefragments.annotations.IdentifyUsing;
import info.novatec.testit.webtester.pagefragments.annotations.Must;
import info.novatec.testit.webtester.pagefragments.annotations.Be;
import info.novatec.testit.webtester.pages.Page;


public class MustBeOnPagesFeatureTest extends BaseIntegrationTest {

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

        @Must(Be.VISIBLE)
        @IdentifyUsing("#username")
        TextField username();

    }

    public interface InheritedFailingFeaturePage extends FailingFeaturePage {
        // no own @Must condition - it should fail because of inheritance
    }

    public interface FailingFeaturePage extends Page {

        @Must(Be.PRESENT)
        @IdentifyUsing("#unknown")
        PageFragment unknown();

    }

}
